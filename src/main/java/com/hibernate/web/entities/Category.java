package com.hibernate.web.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Table(name="category")
public class Category implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id_category;
    private String name_category;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Collection<Annonce> annonces;

    public Long getId_category() {
        return id_category;
    }

    public void setId_category(Long id_category) {
        this.id_category = id_category;
    }

    public String getName_category() {
        return name_category;
    }

    public void setName_category(String name_category) {
        this.name_category = name_category;
    }

    public Collection<Annonce> getAnnonces() {
        return annonces;
    }

    public void setAnnonces(Collection<Annonce> annonces) {
        this.annonces = annonces;
    }

    public Category() {
        super();
    }
    public Category(String name_category) {
        super();
        this.name_category = name_category;

    }
}
