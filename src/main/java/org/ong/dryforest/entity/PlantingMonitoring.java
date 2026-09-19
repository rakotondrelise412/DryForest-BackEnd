package org.ong.dryforest.entity;

import java.time.LocalDate;
import java.util.UUID;

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
@Table(name = "planting_monitoring")
public class PlantingMonitoring extends SyncEntity{
    @Id
    @Column(name = "id_planting_monitoring")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;

    @Column(name = "uuid", columnDefinition = "uuid")
    private UUID uuid;

    @Column(name = "date_planting_monitoring")
    public LocalDate date_planting_monitoring;

    @Column(name = "diameter")
    public double diameter;

    @Column(name = "height")
    public double height;

    // doit etre supprimer / unitil
    @Column(name = "density")
    public double density;

    @Column(name = "carbon_sequestered")
    public double carbon_sequestered;

    @Column(name = "image")
    public String image;

    @Column(name = "auto_generation")
    public int auto_generation;

    @Column(name = "is_synced")
    private boolean is_synced;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_plantation", referencedColumnName = "id_plantation")
    private Plantation plantation;
}
