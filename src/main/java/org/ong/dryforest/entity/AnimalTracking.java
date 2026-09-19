package org.ong.dryforest.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.locationtech.jts.geom.Point;

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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "animal_tracking")
public class AnimalTracking extends SyncEntity{
    @Id
    @Column(name = "id_animal_tracking")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "uuid", columnDefinition = "uuid")
    private UUID uuid;

    @Column(name="location", columnDefinition = "GEOMETRY(Point, 4326)")
    private Point location;

    @Column(name = "date_tracking")
    private LocalDateTime date_tracking;

    @Column(name = "have_seen")
    private boolean have_seen;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_plantation_block", referencedColumnName = "id_plantation_block")
    private PlantationBlock plantationBlock;

    @OneToMany(mappedBy = "animalTracking", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AnimalTrackingDetail> animal_tracking_details = new ArrayList<>();

    @Column(name = "is_synced")
    private boolean is_synced;

}
