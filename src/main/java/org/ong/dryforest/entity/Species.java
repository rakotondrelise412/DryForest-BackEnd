package org.ong.dryforest.entity;

import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "species")
public class Species extends SyncEntity {
    @Id
    @Column(name = "id_species")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "mg_name")
    private String mg_name;

    @Column(name = "fr_name")
    private String fr_name;

    @Column(name = "en_name")
    private String en_name;

    @Column(name = "scientific_name")
    private String scientific_name;

    @Column(name = "density")
    private double density;

    @ManyToMany(mappedBy = "speciesList")
    private List<Site> sitesList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_species_type", referencedColumnName = "id_species_type")
    private SpeciesType type;
}
