package org.ong.dryforest.entity;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.locationtech.jts.geom.Polygon;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.ong.dryforest.entity.PlantationBlock;
import org.ong.dryforest.entity.SyncEntity;
import org.ong.dryforest.entity.TypeZone;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name= "zone")
public class Zone extends SyncEntity {
    @Id
    @Column(name="id_zone")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "uuid", columnDefinition = "uuid")
    private UUID uuid;

    @Column(name="name")
    private String name;

    @Column(name="area")
    private double area;

    @Column(name="geom", columnDefinition = "GEOMETRY(polygon, 4326)")
    private Polygon geom;

    @Column(name = "is_synced")
    private boolean is_synced;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_type_zone", referencedColumnName = "id_type_zone")
    private TypeZone typeZone;

    @OneToMany(mappedBy = "zone", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PlantationBlock> plantationBlocks = new ArrayList<>();
}