package com.hibernate.web.dao;

import com.hibernate.web.entities.Annonce;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AnnonceRepository extends JpaRepository<Annonce, Long> {


    @Query("select p from Annonce p where p.title like :x")
    public Page<Annonce> chercher(@Param("x") String mc, Pageable pageable);

    
    Optional<Annonce> findAnnonceByCategory(Long id);
}
