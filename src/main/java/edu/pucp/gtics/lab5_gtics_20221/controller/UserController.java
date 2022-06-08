package edu.pucp.gtics.lab5_gtics_20221.controller;

import edu.pucp.gtics.lab5_gtics_20221.entity.Juego;
import edu.pucp.gtics.lab5_gtics_20221.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@Controller
@RequestMapping("")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/user/signIn")
    public String pantallaLogueo(){
        return "user/signIn";
    }

    @GetMapping("/user/signInRedirect")
    public String signInRedirect(Authentication auth, RedirectAttributes attr, HttpSession session){
        String rol = "";

        for (GrantedAuthority role : auth.getAuthorities()){
            rol = role.getAuthority();
            break;
        }
        session.setAttribute("usuario",userRepository.findByCorreo(auth.getName()));

        if(rol.equals("USER")){
            session.setAttribute("carrito", new ArrayList<Juego>());
            session.setAttribute("ncarrito",0);
        }

        return "redirect:/juegos/lista";
    }


}