package com.hibernate.web.service;

import com.hibernate.web.entities.Annonce;

import java.util.List;


public interface Service {

    public Annonce addAnnounceToUser(Annonce annonce, Long idUser);
    public List<Annonce> getAnnoncesByUser(Long idUser);

}
