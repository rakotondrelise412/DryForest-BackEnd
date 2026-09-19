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
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "plantation")
public class Plantation extends SyncEntity{
    @Id
    @Column(name = "id_plantation")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;

    @Column(name = "uuid", columnDefinition = "uuid")
    private UUID uuid;

    @Column(name = "plant_number")
    public String plant_number;

    @Column(name = "date_plantation")
    public LocalDate date_plantation;

    @Column(name = "diameter")
    public double diameter;

    @Column(name = "height")
    public double height;

    @Column(name = "carbon_sequestered")
    public double carbon_sequestered;

    @Column(name = "image")
    public String image;

    @Column(name = "status")
    public boolean status;

    @Column(name = "is_synced")
    private boolean is_synced;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_reforestation", referencedColumnName = "id_reforestation")
    private Reforestation reforestation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_species", referencedColumnName = "id_species")
    private Species species;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_sub_plot", referencedColumnName = "id_sub_plot")
    private SubPlot subPlot;
    
}
