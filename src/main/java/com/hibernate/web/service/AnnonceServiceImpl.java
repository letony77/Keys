package com.hibernate.web.service;

import com.hibernate.web.dao.AnnonceRepository;
import com.hibernate.web.dao.UserRepository;
import com.hibernate.web.entities.Annonce;
import com.hibernate.web.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Component
@Transactional
public class AnnonceServiceImpl implements Service{
@Autowired
    UserRepository userRepository;

@Autowired
    AnnonceRepository annonceRepository;

    @Override
    public Annonce addAnnounceToUser(Annonce annonce, Long idUser) {
      //  annonce.setDate(new Date());
        User user = userRepository.getOne(idUser);
        user.addAnnounceToUser(annonce);
        return annonceRepository.save(annonce);
    }

    @Override
    public List<Annonce> getAnnoncesByUser(Long idUser) {
        // TODO Auto-generated method stub
        User user = userRepository.getOne(idUser);
        List<Annonce> annonces = user.getAnnonces();
        return annonces;
    }



}
