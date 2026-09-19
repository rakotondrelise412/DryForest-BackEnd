import React, { useEffect, useState } from 'react';
import { View, Button, Image, Alert, TextInput, Text } from 'react-native';
import * as ImagePicker from 'expo-image-picker';
import * as FileSystem from 'expo-file-system';
import * as ImageManipulator from 'expo-image-manipulator';
import * as SQLite from 'expo-sqlite';
import { v4 as uuidv4 } from 'uuid';

const db = SQLite.openDatabase('local.db');

// Configure these values with your Cloudinary account
const CLOUDINARY_CLOUD_NAME = 'your_cloud_name'; // ex: 'moncloud'
const CLOUDINARY_UPLOAD_PRESET = 'unsigned_preset_mobile'; // le preset unsigned que tu as créé

// Backend URL
const BACKEND_URL = 'https://ton-backend.example.com/api/plantations';

export default function PlantationCapture() {
  const [imageUri, setImageUri] = useState(null);
  const [plantNumber, setPlantNumber] = useState('');
  const [diameter, setDiameter] = useState('');
  const [height, setHeight] = useState('');
  const [density, setDensity] = useState('');

  useEffect(() => {
    db.transaction(tx => {
      tx.executeSql(`CREATE TABLE IF NOT EXISTS plantation_local (
        id INTEGER PRIMARY KEY AUTOINCREMENT,
        client_uuid TEXT,
        plant_number TEXT,
        date_plantation TEXT,
        diameter REAL,
        height REAL,
        density REAL,
        carbon_sequestered REAL,
        image_local_uri TEXT,
        image_public_id TEXT,
        is_synced INTEGER DEFAULT 0
      );`);
    });
  }, []);

  const takePhoto = async () => {
    const { status } = await ImagePicker.requestCameraPermissionsAsync();
    if (status !== 'granted') {
      Alert.alert('Permission requise', 'Permission caméra nécessaire');
      return;
    }
    const result = await ImagePicker.launchCameraAsync({ quality: 0.8, base64: false });
    if (result.cancelled) return;

    // 1) Option: compress/resize
    const manipResult = await ImageManipulator.manipulateAsync(
      result.uri,
      [{ resize: { width: 1200 } }], // redimensionner à 1200px de large si plus grand
      { compress: 0.7, format: ImageManipulator.SaveFormat.JPEG }
    );

    // 2) copy to app document directory for stable path
    const clientUuid = uuidv4();
    const ext = manipResult.uri.split('.').pop().split('?')[0] || 'jpg';
    const filename = `${clientUuid}.${ext}`;
    const imagesDir = `${FileSystem.documentDirectory}images/`;
    try {
      await FileSystem.makeDirectoryAsync(imagesDir, { intermediates: true });
    } catch (e) { /* ignore if exists */ }

    const destUri = imagesDir + filename;
    await FileSystem.copyAsync({ from: manipResult.uri, to: destUri });
    setImageUri(destUri);

    // 3) insert local DB immediately (offline-first)
    db.transaction(tx => {
      tx.executeSql(
        `INSERT INTO plantation_local (client_uuid, plant_number, date_plantation, diameter, height, density, image_local_uri, is_synced)
         VALUES (?, ?, ?, ?, ?, ?, ?, 0)`,
        [clientUuid, plantNumber || null, new Date().toISOString(), parseFloat(diameter || 0), parseFloat(height || 0), parseFloat(density || 0), destUri],
        (_, result) => {
          console.log('saved local id=', result.insertId);
          Alert.alert('Photo sauvegardée localement');
        },
        (_, error) => { console.error(error); return false; }
      );
    });
  };

  // Function to upload a single local record (attempt)
  const uploadAndSyncLocal = async (localRow) => {
    // localRow: { id, client_uuid, image_local_uri, plant_number, diameter, ...}
    const fileUri = localRow.image_local_uri;
    // build FormData for Cloudinary unsigned upload
    const formData = new FormData();
    const fileName = fileUri.split('/').pop();
    const fileType = 'image/jpeg';
    // In Expo, you can pass { uri, name, type } into FormData
    formData.append('file', { uri: fileUri, name: fileName, type: fileType });
    formData.append('upload_preset', CLOUDINARY_UPLOAD_PRESET);
    // optionally a folder:
    formData.append('folder', 'plantations');

    try {
      const cloudinaryUrl = `https://api.cloudinary.com/v1_1/${CLOUDINARY_CLOUD_NAME}/upload`;
      const uploadResp = await fetch(cloudinaryUrl, {
        method: 'POST',
        body: formData,
        headers: {
          'Accept': 'application/json',
        }
      });
      const uploadJson = await uploadResp.json();
      if (!uploadResp.ok) {
        console.error('Cloudinary upload failed', uploadJson);
        throw new Error('Cloudinary upload failed');
      }

      const publicId = uploadJson.public_id; // stocker ceci
      // 2) call backend to create Plantation central
      const backendPayload = {
        plant_number: localRow.plant_number || `PNLOCAL-${localRow.client_uuid}`,
        date_plantation: new Date().toISOString().slice(0,10), // adapt if you store date separately
        diameter: localRow.diameter || 0,
        height: localRow.height || 0,
        density: localRow.density || 0,
        carbon_sequestered: 0,
        image: publicId,
        status: false,
        id_plantation_block: null,
        id_placette: null,
        id_species: null
      };

      const backendResp = await fetch(BACKEND_URL, {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(backendPayload)
      });

      if (!backendResp.ok) {
        console.error('Backend create failed', await backendResp.text());
        throw new Error('Backend create failed');
      }

      // If everything OK: mark local record synced and store public_id
      db.transaction(tx => {
        tx.executeSql(
          `UPDATE plantation_local SET is_synced = 1, image_public_id = ? WHERE id = ?`,
          [publicId, localRow.id],
          () => console.log('marked synced'),
          (_, err) => { console.error(err); return false; }
        );
      });

      Alert.alert('Sync réussi');

    } catch (e) {
      console.error('uploadAndSyncLocal error', e);
      throw e;
    }
  };

  // Sync all unsynced local records
  const syncAll = async () => {
    db.transaction(tx => {
      tx.executeSql(`SELECT * FROM plantation_local WHERE is_synced = 0`, [], async (_, { rows }) => {
        for (let i = 0; i < rows.length; i++) {
          const row = rows.item(i);
          try {
            await uploadAndSyncLocal(row);
          } catch (e) {
            console.log('Sync failed for local id', row.id);
          }
        }
      });
    });
  };

  return (
    <View style={{ padding: 16 }}>
      <Text>Plant number</Text>
      <TextInput value={plantNumber} onChangeText={setPlantNumber} placeholder="PN-001" style={{ borderWidth:1, padding:8, marginBottom:8 }} />
      <Text>Diameter</Text>
      <TextInput value={diameter} onChangeText={setDiameter} keyboardType="numeric" style={{ borderWidth:1, padding:8, marginBottom:8 }} />
      <Text>Height</Text>
      <TextInput value={height} onChangeText={setHeight} keyboardType="numeric" style={{ borderWidth:1, padding:8, marginBottom:8 }} />
      <Text>Density</Text>
      <TextInput value={density} onChangeText={setDensity} keyboardType="numeric" style={{ borderWidth:1, padding:8, marginBottom:8 }} />

      <Button title="Prendre photo" onPress={takePhoto} />
      {imageUri && <Image source={{ uri: imageUri }} style={{ width: 200, height: 200, marginTop: 8 }} />}
      <View style={{ height: 12 }} />
      <Button title="Synchroniser maintenant" onPress={syncAll} />
    </View>
  );
}
