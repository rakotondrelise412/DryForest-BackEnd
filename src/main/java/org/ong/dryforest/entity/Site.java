
package org.ong.dryforest.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "site")
public class Site extends SyncEntity {

    @Id
    @Column(name = "id_site")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "location", columnDefinition = "GEOMETRY(Point, 4326)")
    private Point location;

    @OneToMany(
            mappedBy = "site",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Person> members = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "species_site",
            joinColumns = @JoinColumn(name = "id_site"),
            inverseJoinColumns = @JoinColumn(name = "id_species")
    )
    private List<Species> speciesList = new ArrayList<>();

    @OneToMany(mappedBy = "site")
    private List<Zone> zones = new ArrayList<>();
}
