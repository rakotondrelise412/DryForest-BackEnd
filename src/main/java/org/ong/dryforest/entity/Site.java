package org.ong.dryforest.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.ArrayList;

import org.locationtech.jts.geom.Point;

import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.CascadeType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "site")
public class Site {
    @Id
    @Column(name = "id_site")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "location", columnDefinition = "GEOMETRY(Point, 4326)")
    private Point location;

    @OneToMany(mappedBy = "site", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Person> members = new ArrayList<>();

    @ManyToMany
    @JoinTable(
        name = "species_site",
        joinColumns = @JoinColumn(name = "id_site"),
        inverseJoinColumns = @JoinColumn(name = "id_species")
    )
    private List<Species> speciesList;
}
