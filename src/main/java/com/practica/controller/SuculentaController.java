package com.practica.controller;

import com.practica.domain.Suculenta;
import com.practica.service.SuculentaService;
import jakarta.validation.Valid;
import java.util.Locale;
import java.util.Optional;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/suculenta")
public class SuculentaController {

    // Servicio de suculentas y origen de mensajes.
    private final SuculentaService suculentaService;
    private final MessageSource messageSource;

    public SuculentaController(SuculentaService suculentaService, MessageSource messageSource) {
        this.suculentaService = suculentaService;
        this.messageSource = messageSource;
    }

    @GetMapping("/listado")
    public String listado(Model model) {
        var suculentas = suculentaService.getSuculentas(false);
        model.addAttribute("suculentas", suculentas);
        model.addAttribute("totalSuculentas", suculentas.size());
        return "/suculenta/listado";
    }

    @GetMapping("/modificar/{idSuculenta}")
    public String modificar(@PathVariable("idSuculenta") Integer idSuculenta,
                            Model model, RedirectAttributes redirectAttributes) {
        Optional<Suculenta> suculentaOpt = suculentaService.getSuculenta(idSuculenta);
        if (suculentaOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("error",
                    messageSource.getMessage("suculenta.error01", null, Locale.getDefault()));
            return "redirect:/suculenta/listado";
        }
        model.addAttribute("suculenta", suculentaOpt.get());
        return "/suculenta/modifica";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid Suculenta suculenta, @RequestParam MultipartFile imagenFile,
                          RedirectAttributes redirectAttributes) {
        suculentaService.save(suculenta, imagenFile);
        redirectAttributes.addFlashAttribute("todoOk",
                messageSource.getMessage("mensaje.actualizado", null, Locale.getDefault()));
        return "redirect:/suculenta/listado";
    }

    @PostMapping("/eliminar")
    public String eliminar(@RequestParam Integer idSuculenta, RedirectAttributes redirectAttributes) {
        String titulo = "todoOk";
        String detalle = "mensaje.eliminado";
        try {
            suculentaService.delete(idSuculenta);
        } catch (IllegalArgumentException e) {
            titulo = "error";
            detalle = "suculenta.error01";
        } catch (IllegalStateException e) {
            titulo = "error";
            detalle = "suculenta.error02";
        } catch (Exception e) {
            titulo = "error";
            detalle = "suculenta.error03";
        }
        redirectAttributes.addFlashAttribute(titulo,
                messageSource.getMessage(detalle, null, Locale.getDefault()));
        return "redirect:/suculenta/listado";
    }
}
