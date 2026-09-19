package org.ong.dryforest.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import org.locationtech.jts.geom.Point;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "observation_pat")
public class ObservationPatrol extends SyncEntity {
    @Id
    @Column(name = "id_observation_pat")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "uuid", columnDefinition = "uuid")
    private UUID uuid;

    @Column(name = "datetime_observation")
    private LocalDateTime date_observation;

    @Column(name = "description")
    private String description;

    @Column(name="location", columnDefinition = "GEOMETRY(Point, 4326)")
    private Point location;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_observation_patrol_type", referencedColumnName = "id_observation_patrol_type")
    private TypeObservationPatrol typeObservationPatrol;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_patrol_group", referencedColumnName = "id_patrol_group")
    private PatrolGroup patrolGroup;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_user", referencedColumnName = "id_user")
    private Users users;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_zone", referencedColumnName = "id_zone")
    private Zone zone;

    @Column(name = "is_synced")
    private boolean is_synced;
}
