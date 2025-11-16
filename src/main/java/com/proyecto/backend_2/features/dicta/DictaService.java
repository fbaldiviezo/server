package com.proyecto.backend_2.features.dicta;

import org.springframework.stereotype.Service;

import com.proyecto.backend_2.dtos.requests.DictaRequest;
import com.proyecto.backend_2.exceptions.ResourceNotFoundException;
import com.proyecto.backend_2.features.mapa.MapaRepository;
import com.proyecto.backend_2.features.personals.PersonalRepository;
import com.proyecto.backend_2.features.users.UserRepository;
import com.proyecto.backend_2.ids.MapaId;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DictaService {
    private final DictaRepository repository;
    private final PersonalRepository personalRepository;
    private final UserRepository userRepository;
    private final MapaRepository mapaRepository;

    @Transactional
    public DictaModel save(DictaRequest dicta) {
        var personal = personalRepository.findById(dicta.codp())
                .orElseThrow(() -> new ResourceNotFoundException("No existe el recurso"));
        if (userRepository.getCodp(dicta.codp()) == false) {
            throw new ResourceNotFoundException("El usuario no tiene login");
        }
        var user = userRepository.findById(dicta.login())
                .orElseThrow(() -> new ResourceNotFoundException("No existe el recurso"));
        var mapaId = new MapaId(dicta.codmat(), dicta.codpar(), dicta.gestion());
        var mapa = mapaRepository.findById(mapaId)
                .orElseThrow(() -> new ResourceNotFoundException("No existe el recurso"));
        var post = new DictaModel();
        post.setPersona(personal);
        post.setUsuario(user);
        post.setMapa(mapa);
        return repository.save(post);
    }

    @Transactional
    public void delete(Integer coddic) {
        if (repository.existsById(coddic)) {
            repository.deleteById(coddic);
        } else {
            throw new ResourceNotFoundException("No existe el recurso");
        }
    }
}
