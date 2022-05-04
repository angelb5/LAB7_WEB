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
        List<JuegosxUsuario> juegosComprar = new ArrayList<JuegosxUsuario>();
        User user= (User) session.getAttribute("usuario");
        int ncarrito = (int) session.getAttribute("ncarrito");

        for (Juegos juego: juegosEnCarrito) {
            boolean contado = false;
            for(JuegosxUsuario jComprar : juegosComprar ){
                if(jComprar.getIdjuego()==juego.getIdjuego()){
                    contado = true;
                    jComprar.setCantidad(jComprar.getCantidad()+1);
                }
            }

            if(!contado){
                JuegosxUsuario jxu= new JuegosxUsuario();
                jxu.setIdjuego(juego.getIdjuego());
                jxu.setIdusuario(user.getIdusuario());
                jxu.setCantidad(1);
                juegosComprar.add(jxu);
            }
        }

        juegosxUsuarioRepository.saveAll(juegosComprar);
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

            Iterator itr = juegosEnCarrito.iterator();
            Juegos j;

            while (itr.hasNext()){
                j = (Juegos) itr.next();
                if(j.getIdjuego()==id){
                    itr.remove();
                    break;
                }
            }

            session.setAttribute("carrito",juegosEnCarrito);
            session.setAttribute("ncarrito",ncarrito-1);
        }

        return "redirect:/carrito/lista";
    }


}
