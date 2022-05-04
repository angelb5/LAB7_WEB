package edu.pucp.gtics.lab5_gtics_20221.controller;

import edu.pucp.gtics.lab5_gtics_20221.entity.*;
import edu.pucp.gtics.lab5_gtics_20221.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import javax.websocket.Session;
import java.util.*;

@Controller
@RequestMapping("/carrito")
public class CarritoController {


    @Autowired
    JuegosRepository juegosRepository;

    @Autowired
    JuegosxUsuarioRepository juegosxUsuarioRepository;

    @GetMapping("/lista")
    public String listaCarrito (Model model, HttpSession session){

        List<Juegos> juegosEnCarrito = (List<Juegos>) session.getAttribute("carrito");

        // Ordena los elementos en carrito por precio
        juegosEnCarrito.sort(Comparator.comparing(Juegos::getPrecio));
        model.addAttribute("listaJuegos",juegosEnCarrito);

        return "carrito/lista";
    }

    @GetMapping("/nuevo")
    public String anadirCarrito(@RequestParam("id") int id, HttpSession session){

        Optional<Juegos> juego = juegosRepository.findById(id);

        if (juego.isPresent()){
            List<Juegos> juegosEnCarrito = (List<Juegos>) session.getAttribute("carrito");
            int ncarrito = (int) session.getAttribute("ncarrito");
            juegosEnCarrito.add(juego.get());
            session.setAttribute("carrito",juegosEnCarrito);
            session.setAttribute("ncarrito",ncarrito+1);
        }

        return "redirect:/carrito/lista";
    }

    @GetMapping("/comprar")
    public String comprarCarrito(HttpSession session){
        List<Juegos> juegosEnCarrito = (List<Juegos>) session.getAttribute("carrito");
        JuegosxUsuario jxu= new JuegosxUsuario();
        User user= (User) session.getAttribute("usuario");
        int ncarrito = (int) session.getAttribute("ncarrito");
        for (Juegos juego: juegosEnCarrito) {
            jxu.setIdjuego(juego.getIdjuego());
            jxu.setIdusuario(user.getIdusuario());
            jxu.setCantidad(1);
            juegosxUsuarioRepository.save(jxu);
            System.out.println(juego.getNombre());
        }
        session.setAttribute("carrito",new ArrayList<Juegos>());
        session.setAttribute("ncarrito",0);
        return "redirect:/juegos/lista";
    }

    @GetMapping("/borrar")
    public String borrarCarrito(@RequestParam("id") int id, HttpSession session){

        Optional<Juegos> juego = juegosRepository.findById(id);

        if (juego.isPresent()){
            List<Juegos> juegosEnCarrito = (List<Juegos>) session.getAttribute("carrito");
            int ncarrito = (int) session.getAttribute("ncarrito");
            juegosEnCarrito.removeIf(juegos -> (juegos.getIdjuego()==juego.get().getIdjuego()));
            session.setAttribute("carrito",juegosEnCarrito);
            session.setAttribute("ncarrito",ncarrito-1);
        }

        return "redirect:/carrito/lista";
    }


}
