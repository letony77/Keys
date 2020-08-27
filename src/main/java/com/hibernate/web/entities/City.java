package com.hibernate.web.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Table(name="city")
public class City implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id_city;
    private String name_city;

    @OneToMany(mappedBy = "city",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Collection<Annonce> annonces;

    public Collection<Annonce> getAnnonces() {
        return annonces;
    }

    public void setAnnonces(Collection<Annonce> annonces) {
        this.annonces = annonces;
    }

    public Long getId_city() {
        return id_city;
    }

    public void setId_city(Long id_city) {
        this.id_city = id_city;
    }

    public String getName_city() {
        return name_city;
    }

    public void setName_city(String name_city) {
        this.name_city = name_city;
    }

    public City() {
        super();
    }
    public City(String name_city) {
        super();
        this.name_city = name_city;
    }
}
