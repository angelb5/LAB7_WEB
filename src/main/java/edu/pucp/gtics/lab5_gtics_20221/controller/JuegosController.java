package edu.pucp.gtics.lab5_gtics_20221.controller;

import edu.pucp.gtics.lab5_gtics_20221.dao.DistribuidoraDao;
import edu.pucp.gtics.lab5_gtics_20221.dao.JuegoDao;
import edu.pucp.gtics.lab5_gtics_20221.entity.*;
import edu.pucp.gtics.lab5_gtics_20221.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller

public class JuegosController {

    @Autowired
    JuegoDao juegoDao;

    @Autowired
    PlataformasRepository plataformasRepository;

    @Autowired
    DistribuidoraDao distribuidoraDao;

    @Autowired
    GenerosRepository generosRepository;

    @Autowired
    UserRepository userRepository;

     @GetMapping(value = {"/juegos/lista"})
     public String listaJuegos (Model model, HttpSession session){
         User user = (User) session.getAttribute("usuario");

         if(user.getAutorizacion().equals("ADMIN")){
             List<Juego> listaJuegos = juegoDao.listarJuegosPrecio();
             model.addAttribute("listaJuegos",listaJuegos);
             return "juegos/lista";
         }else{
             List<JuegosUserDto> listaJuegosDTO = juegoDao.listarJuegosUser(user.getIdusuario());
             model.addAttribute("listaJuegos",listaJuegosDTO);
             return "juegos/comprado";
         }
     }

    @GetMapping(value = {"", "/", "juegos/vista"})
    public String vistaJuegos(Model model, HttpSession session){
        User user = (User) session.getAttribute("usuario");
        List<Juego> listaJuegos;
        if(user!=null){
            listaJuegos = juegoDao.listarJuegosNoComprados(user.getIdusuario());
        }else{
            listaJuegos = juegoDao.listarJuegos();
        }
        model.addAttribute("listaJuegos",listaJuegos);
        return "juegos/vista";
    }

    @GetMapping("/juegos/nuevo")
    public String nuevoJuegos(Model model, @ModelAttribute("juego") Juego juego){
        List<Plataformas> listaPlataformas = plataformasRepository.findAll();
        List<Distribuidoras> listaDistribuidoras = distribuidoraDao.listarDistribuidoras();
        List<Generos> listaGeneros = generosRepository.findAll();
        model.addAttribute("listaPlataformas", listaPlataformas);
        model.addAttribute("listaDistribuidoras", listaDistribuidoras);
        model.addAttribute("listaGeneros", listaGeneros);
        return "juegos/editarFrm";
    }

    @GetMapping("/juegos/editar")
    public String editarJuegos(@RequestParam("id") int id, Model model){
        Optional<Juego> opt = Optional.ofNullable(juegoDao.obtenerJuegoPorId(id));
        List<Plataformas> listaPlataformas = plataformasRepository.findAll();
        List<Distribuidoras> listaDistribuidoras = distribuidoraDao.listarDistribuidoras();
        List<Generos> listaGeneros = generosRepository.findAll();
        if (opt.isPresent()){
            Juego juego = opt.get();
            model.addAttribute("juego", juego);
            model.addAttribute("listaPlataformas", listaPlataformas);
            model.addAttribute("listaDistribuidoras", listaDistribuidoras);
            model.addAttribute("listaGeneros", listaGeneros);
            return "juegos/editarFrm";
        }else {
            return "redirect:/juegos/lista";
        }
    }

    @PostMapping("/juegos/guardar")
    public String guardarJuegos(Model model, RedirectAttributes attr, @ModelAttribute("juego") @Valid Juego juego, BindingResult bindingResult ){
        if(bindingResult.hasErrors( )){
            List<Plataformas> listaPlataformas = plataformasRepository.findAll();
            List<Distribuidoras> listaDistribuidoras = distribuidoraDao.listarDistribuidoras();
            List<Generos> listaGeneros = generosRepository.findAll();
            model.addAttribute("juego", juego);
            model.addAttribute("listaPlataformas", listaPlataformas);
            model.addAttribute("listaDistribuidoras", listaDistribuidoras);
            model.addAttribute("listaGeneros", listaGeneros);
            return "juegos/editarFrm";
        } else {
            if (juego.getId() == 0) {
                attr.addFlashAttribute("msg", "Juego creado exitosamente");
            } else {
                attr.addFlashAttribute("msg", "Juego actualizado exitosamente");
            }
            juegoDao.guardarJuego(juego);
            return "redirect:/juegos/lista";
        }


    }

    @GetMapping("/juegos/borrar")
    public String borrarDistribuidora(@RequestParam("id") int id){
        Optional<Juego> opt = Optional.ofNullable(juegoDao.obtenerJuegoPorId(id));
        if (opt.isPresent()) {
            juegoDao.borrarJuego(id);
        }
        return "redirect:/juegos/lista";
    }





}
