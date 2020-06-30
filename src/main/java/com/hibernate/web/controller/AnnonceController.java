package com.hibernate.web.controller;

import com.hibernate.web.dao.AnnonceRepository;
import com.hibernate.web.dao.UserRepository;
import com.hibernate.web.entities.Annonce;
import com.hibernate.web.entities.Item;
import com.hibernate.web.entities.User;
import com.hibernate.web.service.Service;
import org.hibernate.cache.spi.support.AbstractReadWriteAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class AnnonceController {

@Autowired
    AnnonceRepository annonceRepository;
@Autowired
    Service service;
@Autowired
    UserRepository userRepository;

    @RequestMapping(value = "/annonce")
    public String annonce(Model model
            , @RequestParam(name = "page", defaultValue = "0") int p
            , @RequestParam(name = "size", defaultValue = "6") int s
            , @RequestParam(name = "mc", defaultValue = "" ) String mc
    ){
        Page<Annonce> pagesAnnonce = annonceRepository.chercher("%"+mc+"%", PageRequest.of(p,s));
        model.addAttribute("listAnnonces", pagesAnnonce.getContent());

        int[] pages = new int[pagesAnnonce.getTotalPages()];
        model.addAttribute("pages", pages);
        model.addAttribute("size", s);
        model.addAttribute("pageCourante", p);
        model.addAttribute("mc", mc);
        return "annonce";
    }

    @RequestMapping(value="/admin/formAnnonce", method= RequestMethod.GET)
    public String form(Model model){
        model.addAttribute("annonce",new Annonce());
        return "formAnnonce";
    }

    @RequestMapping(value="/admin/editA", method=RequestMethod.GET)
    public String edit(Model model, Long id){
        Annonce p = annonceRepository.getOne(id);
        model.addAttribute("annonce",p);
        return "editAnnonce";
    }

    @RequestMapping(value="/admin/saveAnnonce", method=RequestMethod.POST)
    public String save(Model model, Annonce annonce){
        annonce = new Annonce(annonce.getTitle(), annonce.getEntreprise(), annonce.getDescription());
        annonceRepository.save(annonce);
        return "confirmationA";
    }

    @RequestMapping(value="/admin/saveAnnonceE", method=RequestMethod.POST)
    public String saveE(Annonce annonce){
        annonceRepository.save(annonce);
        return "confirmationA";
    }

    @RequestMapping(value = "/user/deleteA", method = RequestMethod.GET)
    public String delete(Long id, String motCle, int page, int size){
        annonceRepository.deleteById(id);
        return "redirect:/annonce?page="+page+"&size="+size+"&motCle="+motCle;
    }

    @RequestMapping(value ="/user/addfav/{id}", method = RequestMethod.GET)
    public String fav(Model model, @PathVariable("id") Long id, HttpSession session){
        System.out.println(annonceRepository.getOne(id));
        List<Item> cart;
        if(session.getAttribute("cart") == null){
          //  List<Annonce> fav = new ArrayList<Annonce>();
         //   fav.add(new Annonce(annonceRepository.find(id), annonce.get));
            cart = new ArrayList<Item>();
            cart.add(new Item(annonceRepository.getOne(id), 1));
            session.setAttribute("cart", cart);
            System.out.println("MAP LIST CART IF" + cart.size());
        } else {
            cart = (List<Item>) session.getAttribute("cart");
            cart.add(new Item(annonceRepository.getOne(id), 1));
            session.setAttribute("cart", cart);
            System.out.println("MAP LIST CART ELSE" + cart.size());

        }
        System.out.println(cart.size() + "MAX SIZE CART");
        return "redirect:/user/fav";
    }
    @RequestMapping (value="/user/fav")
    public String cart(){
        return "fav";
    }


    @RequestMapping(value = "/addAnnounceToUser", method=RequestMethod.POST)
    String addAnnounceToUser(Annonce annonce) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        //Long test = user.getId();
        String getName = authentication.getName();

        Optional user = userRepository.findByUserName(getName);
        Optional<User> test = userRepository.findByUserName(getName);
        User test2 = test.get();
        Long idUser = test2.getId();
        System.out.println(idUser);
        service.addAnnounceToUser(annonce, idUser);
        return "confirmationA";
    }

    @RequestMapping(value = "/profil", method=RequestMethod.GET)
  public String getAnnoncesByUser(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String getName = authentication.getName();
        Optional<User> test = userRepository.findByUserName(getName);
        User test2 = test.get();
        Long idUser = test2.getId();
        service.getAnnoncesByUser(idUser);
        List<Annonce> annonces = service.getAnnoncesByUser(idUser);
        model.addAttribute("annonce", annonces);
        return "profil";
    }
}

