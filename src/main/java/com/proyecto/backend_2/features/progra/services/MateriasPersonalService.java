package com.proyecto.backend_2.features.progra.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.proyecto.backend_2.dtos.responses.ItemDto;
import com.proyecto.backend_2.dtos.responses.SubjectsByStudent;
import com.proyecto.backend_2.features.itemat.ItematModel;
import com.proyecto.backend_2.features.itemat.ItematRepository;
import com.proyecto.backend_2.features.progra.PrograModel;
import com.proyecto.backend_2.features.progra.PrograRepository;
import com.proyecto.backend_2.features.subjects.SubjectModel;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MateriasPersonalService {

    private final PrograRepository prograRepository;
    private final ItematRepository itematRepository;

    public List<SubjectsByStudent> getSubjectsByStudent(String login) {
        List<SubjectsByStudent> subjectsByStudentList = new ArrayList<>();

        // 1) Trae materias del estudiante
        List<PrograModel> progras = prograRepository.findByUsuarioLogin(login);

        for (PrograModel progra : progras) {
            SubjectModel materia = progra.getMapa().getMapaMateria();

            SubjectsByStudent subjectDto = new SubjectsByStudent();
            subjectDto.setCodmat(materia.getCodmat());
            subjectDto.setNombreMateria(materia.getNombre());

            // 2) Trae items de la materia
            List<ItematModel> itemats = itematRepository.findByIteMateriaCodmat(materia.getCodmat());
            List<ItemDto> itemDtos = new ArrayList<>();

            for (ItematModel itemat : itemats) {
                ItemDto itemDto = new ItemDto();
                itemDto.setNombreItem(itemat.getIteItem().getNombre());
                itemDto.setPonderacion(itemat.getPonderacion());
                itemDtos.add(itemDto);
            }

            subjectDto.setItems(itemDtos);
            subjectsByStudentList.add(subjectDto);
        }

        return subjectsByStudentList;
    }
}
