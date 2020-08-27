package com.hibernate.web.entities;

import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class User implements Serializable {
@Id @GeneratedValue(strategy = GenerationType.AUTO)
private Long id;
   @UpdateTimestamp @GeneratedValue
    private Date datetime;
    private String email;
    private String nom;
    private String prenom;
    private String num;
    private String userName;
    private String password;
    private String roles;
    private boolean isActive = true;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Annonce> annonces;

 /*   @ManyToOne
    @JoinColumn(name = "ROLE_ID")
    private Role role;*/

    public List<Annonce> getAnnonces() {
        return annonces;
    }

    public void setAnnonces(List<Annonce> annonces) {
        this.annonces = annonces;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

/*    public Role getRole() {
        return role;
    }*/

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

/*
    public void setRole(Role role) {
        this.role = role;
    }
*/

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public User(String email, String nom, String prenom, String num, String roles,/*Role role,*/ String password, String userName) {
        super();

        this.email = email;
        this.nom = nom;
        this.prenom = prenom;
        this.num = num;
    //    this.role = role;
        this.roles = roles;
        this.password = password;
        this.userName = userName;
    }

    public User(){
        super();
    }


    public void addAnnounceToUser(Annonce annonce) {
        if(getAnnonces() == null ) {
            this.annonces = new ArrayList<Annonce>();
        }
        getAnnonces().add(annonce);
        annonce.setUser(this);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", datetime=" + datetime +
                ", email='" + email + '\'' +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", num='" + num + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", roles='" + roles + '\'' +
                ", isActive=" + isActive +
                ", annonces=" + annonces +
                '}';
    }
}
