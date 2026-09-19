package org.ong.dryforest.entity;

import java.util.UUID;

import org.locationtech.jts.geom.Point;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GenerationType;
import jakarta.persistence.GeneratedValue;
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
@Table(name= "sub_plot")
public class SubPlot extends SyncEntity{
    @Id
    @Column(name="id_sub_plot")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "uuid", columnDefinition = "uuid")
    private UUID uuid;

    @Column(name="name")
    private String name;

    @Column(name="width")
    private Double width;

    @Column(name="length")
    private Double length;

    @Column(name="location", columnDefinition = "GEOMETRY(Point, 4326)")
    private Point location;

    @Column(name = "is_synced")
    private boolean is_synced;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_plantation_block", referencedColumnName = "id_plantation_block")
    private PlantationBlock plantation_block;
}
