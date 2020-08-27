package com.hibernate.web.controller;

import com.hibernate.web.dao.AnnonceRepository;
import com.hibernate.web.dao.CategoryRepository;
import com.hibernate.web.dao.CityRepository;
import com.hibernate.web.dao.UserRepository;
import com.hibernate.web.entities.*;
import com.hibernate.web.service.Service;
import org.hibernate.cache.spi.support.AbstractReadWriteAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import sun.java2d.pipe.SpanShapeRenderer;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;
import java.io.File;
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
@Autowired
    CategoryRepository categoryRepository;
@Autowired
    CityRepository cityRepository;
@Autowired
    JavaMailSender emailSender;

    City city = new City("Logne");
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

    @RequestMapping(value="/corp/formAnnonce", method= RequestMethod.GET)
    public String form(Model model){
        model.addAttribute("annonce",new Annonce());
        return "formAnnonce";
    }

    @RequestMapping(value="/corp/editA", method=RequestMethod.GET)
    public String edit(Model model, Long id){
        Annonce p = annonceRepository.getOne(id);
        model.addAttribute("annonce",p);
        return "editAnnonce";
    }

/*    @RequestMapping(value="/corp/saveAnnonce", method=RequestMethod.POST)
    public String save(Model model, Annonce annonce, @RequestParam(name = "annonce.category", required = false) String categoryname, @RequestParam(name = "annonce.city", required = false) String cityname, Category category, City city){
        category = new Category(categoryname);
        city = new City(cityname);

        annonce = new Annonce(annonce.getTitle(), annonce.getEntreprise(), annonce.getDescription(), category, city);
        annonceRepository.save(annonce);
        return "confirmationA";
    }*/

    @RequestMapping(value="/corp/saveAnnonceE", method=RequestMethod.POST)
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
    public String addAnnounceToUser(Annonce annonce, @RequestParam(name="annonce.city", required = false) String cityName, @RequestParam(name="annonce.category", required = false) String categoryName) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String getName = authentication.getName();
        Optional user = userRepository.findByUserName(getName);
        Optional<User> test = userRepository.findByUserName(getName);
        User test2 = test.get();
        Long idUser = test2.getId();
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

    @RequestMapping(value = "/sendEmail", method=RequestMethod.POST)
    public String sendSimpleEmail(@RequestParam(name="destinataire", required = false) String destinataire, @RequestParam(name="body", required = false) String body) {
        // Create a Simple MailMessage.
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(destinataire);
        message.setSubject("from Key'S");
        message.setText(body);

        // Send Message!
        this.emailSender.send(message);

        return "mailConfirm";
    }
    @RequestMapping(value = "/sendEmailPJ", method=RequestMethod.POST)
    public String sendSimpleEmailPJ(Model model, @RequestParam(name="destinataire", required = false) String destinataire, @RequestParam(name="body", required = false) String body, @RequestParam(name="file", required = false) String file) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();

        boolean multipart = true;

        MimeMessageHelper helper = new MimeMessageHelper(message, multipart);

        helper.setTo(destinataire);
        helper.setSubject("Contact Stagiaire");

        helper.setText(body);

        // Attachment 1
        FileSystemResource file1 = new FileSystemResource(new File(file));
        helper.addAttachment(file, file1);

        emailSender.send(message);

        return "mailConfirm";
    }
}

