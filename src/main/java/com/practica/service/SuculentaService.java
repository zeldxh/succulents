package com.practica.service;

import com.practica.domain.Suculenta;
import com.practica.repository.SuculentaRepository;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class SuculentaService {

    // Repositorio de suculentas y servicio de imágenes.
    private final SuculentaRepository suculentaRepository;
    private final FirebaseStorageService firebaseStorageService;

    public SuculentaService(SuculentaRepository suculentaRepository,
                            FirebaseStorageService firebaseStorageService) {
        this.suculentaRepository = suculentaRepository;
        this.firebaseStorageService = firebaseStorageService;
    }

    // Listado: todas o solo las activas.
    @Transactional(readOnly = true)
    public List<Suculenta> getSuculentas(boolean activo) {
        if (activo) {
            return suculentaRepository.findByActivoTrue();
        }
        return suculentaRepository.findAll();
    }

    // Busca una suculenta por su id.
    @Transactional(readOnly = true)
    public Optional<Suculenta> getSuculenta(Integer idSuculenta) {
        return suculentaRepository.findById(idSuculenta);
    }

    // Inserta o actualiza; si llega una imagen la sube a Firebase.
    @Transactional
    public void save(Suculenta suculenta, MultipartFile imagen) {
        suculenta = suculentaRepository.save(suculenta);
        if (imagen != null && !imagen.isEmpty()) {
            try {
                String ruta = firebaseStorageService.uploadImage(
                        imagen, "suculenta", suculenta.getIdSuculenta());
                suculenta.setRutaImagen(ruta);
                suculentaRepository.save(suculenta);
            } catch (IOException e) {
            }
        }
    }

    // Elimina una suculenta por su id.
    @Transactional
    public void delete(Integer idSuculenta) {
        if (!suculentaRepository.existsById(idSuculenta)) {
            throw new IllegalArgumentException("No se puede eliminar el registro, no existe id " + idSuculenta);
        }
        try {
            suculentaRepository.deleteById(idSuculenta);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("No se elimina porque tiene datos asociados.", e);
        }
    }
}
