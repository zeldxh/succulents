package com.practica.controller;

import com.practica.service.SuculentaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    // Servicio de suculentas.
    private final SuculentaService suculentaService;

    public IndexController(SuculentaService suculentaService) {
        this.suculentaService = suculentaService;
    }

    @GetMapping("/")
    public String mostrarIndex(Model model) {
        var suculentas = suculentaService.getSuculentas(true);
        model.addAttribute("suculentas", suculentas);
        return "/index";
    }
}
