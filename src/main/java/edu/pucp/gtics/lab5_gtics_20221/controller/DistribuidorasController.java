package edu.pucp.gtics.lab5_gtics_20221.controller;

import edu.pucp.gtics.lab5_gtics_20221.dao.DistribuidorasDao;
import edu.pucp.gtics.lab5_gtics_20221.entity.Distribuidoras;
import edu.pucp.gtics.lab5_gtics_20221.entity.Paises;
import edu.pucp.gtics.lab5_gtics_20221.repository.PaisesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/distribuidoras")

public class DistribuidorasController {

    @Autowired
    DistribuidorasDao distribuidorasDao;

    @Autowired
    PaisesRepository paisesRepository;

    @GetMapping(value = {"/lista"})
    public String listaDistribuidoras (Model model){
        model.addAttribute("listadistribuidoras", distribuidorasDao.listarDistribuidoras());
        return "distribuidoras/lista";
    }

    @GetMapping("/editar")
    public String editarDistribuidoras(@RequestParam("id") int id, Model model){
        Optional<Distribuidoras> opt = Optional.ofNullable(distribuidorasDao.obtenerDistribuidoraPorId(id));
        List<Paises> listaPaises = paisesRepository.findAll();
        if (opt.isPresent()){
            Distribuidoras distribuidora = opt.get();
            model.addAttribute("distribuidora", distribuidora);
            model.addAttribute("listaPaises", listaPaises);
            return "distribuidoras/editarFrm";
        }else {
            return "redirect:/distribuidoras/lista";
        }
    }

    @GetMapping("/nuevo")
    public String nuevaDistribuidora(Model model, @ModelAttribute("distribuidora") Distribuidoras distribuidora){
        List<Paises> listaPaises = paisesRepository.findAll();
        model.addAttribute("listaPaises", listaPaises);
        return "distribuidoras/editarFrm";
    }

    @PostMapping("/guardar")
    public String guardarDistribuidora(Model model, RedirectAttributes attr, @ModelAttribute("distribuidora") @Valid Distribuidoras distribuidora , BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            List<Paises> listaPaises = paisesRepository.findAll();
            model.addAttribute("distribuidora", distribuidora);
            model.addAttribute("listaPaises", listaPaises);
            return "distribuidoras/editarFrm";
        } else {
            if (distribuidora.getIddistribuidora() == 0) {
                attr.addFlashAttribute("msg", "Distribuidora creada exitosamente");
            } else {
                attr.addFlashAttribute("msg", "Distribuidora actualizada exitosamente");
            }
            distribuidorasDao.guardarDistribuidoras(distribuidora);
            return "redirect:/distribuidoras/lista";
        }
    }

    @GetMapping("/borrar")
    public String borrarDistribuidora(@RequestParam("id") int id){
        Optional<Distribuidoras> opt = Optional.ofNullable(distribuidorasDao.obtenerDistribuidoraPorId(id));
        if (opt.isPresent()) {
            distribuidorasDao.borrarDistribuidoras(id);
        }
        return "redirect:/distribuidoras/lista";
    }

}
