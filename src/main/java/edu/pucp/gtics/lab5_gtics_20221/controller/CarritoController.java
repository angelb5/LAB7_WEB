package edu.pucp.gtics.lab5_gtics_20221.controller;

import edu.pucp.gtics.lab5_gtics_20221.dao.JuegoDao;
import edu.pucp.gtics.lab5_gtics_20221.entity.*;
import edu.pucp.gtics.lab5_gtics_20221.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
@RequestMapping("/carrito")
public class CarritoController {

    @Autowired
    JuegoDao juegoDao;

    @Autowired
    JuegosxUsuarioRepository juegosxUsuarioRepository;

    @GetMapping("/lista")
    public String listaCarrito (Model model, HttpSession session){

        List<Juego> juegosEnCarrito = (List<Juego>) session.getAttribute("carrito");

        // Ordena los elementos en carrito por precio
        juegosEnCarrito.sort(Comparator.comparing(Juego::getPrecio));
        model.addAttribute("listaJuegos",juegosEnCarrito);

        return "carrito/lista";
    }

    @GetMapping("/nuevo")
    public String anadirCarrito(@RequestParam("id") int id, HttpSession session){

        Optional<Juego> juego = Optional.ofNullable(juegoDao.obtenerJuegoPorId(id));

        if (juego.isPresent()){
            List<Juego> juegosEnCarrito = (List<Juego>) session.getAttribute("carrito");
            int ncarrito = (int) session.getAttribute("ncarrito");
            juegosEnCarrito.add(juego.get());
            session.setAttribute("carrito",juegosEnCarrito);
            session.setAttribute("ncarrito",ncarrito+1);
        }

        return "redirect:/carrito/lista";
    }

    @GetMapping("/comprar")
    public String comprarCarrito(HttpSession session){
        List<Juego> juegoEnCarrito = (List<Juego>) session.getAttribute("carrito");
        List<JuegosxUsuario> juegosComprar = new ArrayList<JuegosxUsuario>();
        User user= (User) session.getAttribute("usuario");
        int ncarrito = (int) session.getAttribute("ncarrito");

        for (Juego juego: juegoEnCarrito) {
            boolean contado = false;
            for(JuegosxUsuario jComprar : juegosComprar ){
                if(jComprar.getIdjuego()==juego.getId()){
                    contado = true;
                    jComprar.setCantidad(jComprar.getCantidad()+1);
                }
            }

            if(!contado){
                JuegosxUsuario jxu= new JuegosxUsuario();
                jxu.setIdjuego(juego.getId());
                jxu.setIdusuario(user.getIdusuario());
                jxu.setCantidad(1);
                juegosComprar.add(jxu);
            }
        }

        juegosxUsuarioRepository.saveAll(juegosComprar);
        session.setAttribute("carrito",new ArrayList<Juego>());
        session.setAttribute("ncarrito",0);
        return "redirect:/juegos/lista";
    }

    @GetMapping("/borrar")
    public String borrarCarrito(@RequestParam("id") int id, HttpSession session){

        Optional<Juego> juego = Optional.ofNullable(juegoDao.obtenerJuegoPorId(id));

        if (juego.isPresent()){
            List<Juego> juegoEnCarrito = (List<Juego>) session.getAttribute("carrito");
            int ncarrito = (int) session.getAttribute("ncarrito");

            Iterator itr = juegoEnCarrito.iterator();
            Juego j;

            while (itr.hasNext()){
                j = (Juego) itr.next();
                if(j.getId()==id){
                    itr.remove();
                    break;
                }
            }

            session.setAttribute("carrito", juegoEnCarrito);
            session.setAttribute("ncarrito",ncarrito-1);
        }

        return "redirect:/carrito/lista";
    }


}
