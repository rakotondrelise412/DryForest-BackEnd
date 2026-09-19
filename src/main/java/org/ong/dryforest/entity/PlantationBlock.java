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

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name= "plantation_block")
public class PlantationBlock extends SyncEntity {
    @Id
    @Column(name="id_plantation_block")
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

    @Column(name = "nb_sub_plot")
    private int nb_sub_plot;
    
    @Column(name="geom", columnDefinition = "GEOMETRY(polygon, 4326)")
    private Polygon geom;

    @Column(name = "is_synced")
    private boolean is_synced;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_zone", referencedColumnName = "id_zone")
    private Zone zone;

    @OneToMany(mappedBy = "plantation_block", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SubPlot> subPlots = new ArrayList<>();

}
