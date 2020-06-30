package com.hibernate.web.dao;

import com.hibernate.web.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserName(String Username);

    // Methode uqi permet la recherche en dur sur la console
    @Query("select p from User p where p.nom like :x")
    public List<User> personnesParMC(@Param("x") String mc);

    // Methode qui permet la recherche dans les paginations
    @Query("select p from User p where p.nom like :x")
    public Page<User> chercher(@Param("x") String mc, Pageable pageable);



}
