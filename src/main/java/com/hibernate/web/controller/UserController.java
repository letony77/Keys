package com.hibernate.web.controller;

import com.hibernate.web.dao.UserRepository;
import com.hibernate.web.entities.Role;
import com.hibernate.web.entities.User;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;
    //@Autowired
 //   private RoleRepository roleRepository;

/*    Role roleEmp = new Role ("Employeur");
    Role roleStagiaire = new Role ("Stagiaire");
    Role roleAdmin = new Role ("ADMIN");*/


    @RequestMapping(value ="/index")
    public String index(){
        return "index";
    }

    // Afficher les users
    @RequestMapping(value = "/admin/user")
    public String user(Model model
            , @RequestParam(name = "page", defaultValue = "0") int p
            , @RequestParam(name = "size", defaultValue = "6") int s
            , @RequestParam(name = "mc", defaultValue = "" ) String mc
    ){
        Page<User> pageUsers = userRepository.chercher("%"+mc+"%", PageRequest.of(p,s));
        model.addAttribute("listUsers", pageUsers.getContent());

        int[] pages = new int[pageUsers.getTotalPages()];
        model.addAttribute("pages", pages);
        model.addAttribute("size", s);
        model.addAttribute("pageCourante", p);
        model.addAttribute("mc", mc);
        return "users";
    }

    //Methode pour supprimer les users
    @RequestMapping(value = "/admin/delete", method = RequestMethod.GET)
    public String delete(Long id, String motCle, int page, int size){
        userRepository.deleteById(id);
        return "redirect:/admin/user?page="+page+"&size="+size+"&motCle="+motCle;
    }

    @RequestMapping(value="/form", method=RequestMethod.GET)
    public String formUser(Model model){
        model.addAttribute("user",new User());
        return "formUser";
    }

    @RequestMapping(value="/admin/edit", method=RequestMethod.GET)
    public String edit(Model model, Long id){
      //  Optional<User> p = userRepository.findById(id);
        User p = userRepository.getOne(id);
        model.addAttribute("user",p);
        return "editUser";
    }

    // Methode pour save un user
    @RequestMapping(value="/save", method=RequestMethod.POST)
    public String saveUser(Model model, User user, Role roleName, @RequestParam(name = "user.role", required = false) String role
                       ){
        roleName = new Role(role);

/*        roleRepository.save(roleStagiaire);
        roleRepository.save(roleEmp);
        roleRepository.save(roleAdmin);
        if (roleName.getName_role().equals(roleStagiaire.getName_role())){
            user = new User(user.getEmail(),user.getNom(),user.getPrenom(),user.getNum(), roleStagiaire, user.getPassword() );
        }
        if (roleName.getName_role().equals(roleEmp.getName_role())) {
            user = new User(user.getEmail(),user.getNom(),user.getPrenom(),user.getNum(), roleEmp, user.getPassword());
        }
        if (roleName.getName_role().equals(roleAdmin.getName_role())) {
            user = new User(user.getEmail(),user.getNom(),user.getPrenom(),user.getNum(), roleAdmin, user.getPassword());
        }*/
        user = new User(user.getEmail(), user.getNom(), user.getPrenom(), user.getNum(), user.getRoles(), user.getPassword(), user.getUserName());
        userRepository.save(user);
        return "confirmation";
    }

    @RequestMapping(value="/admin/saveE", method=RequestMethod.POST)
    public String saveUserEdit(Model model, User user, Role roleName, @RequestParam(name = "user.role", required = false) String role
    ){
        userRepository.save(user);
        return "confirmation";
    }
    @RequestMapping(value="/")
    public String home(){
        return"redirect:/index";
    }

    @RequestMapping(value="/403")
    public String accesDenied(){
        return"403";
    }

    @RequestMapping(value="/login")
    public String login(){

        return"login";
    }


}
