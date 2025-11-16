package com.proyecto.backend_2.features.levels;

import java.util.Set;

import com.proyecto.backend_2.features.subjects.SubjectModel;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "niveles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class LevelModel {
    @Id
    @Column(name = "codn", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codn;

    @Column(name = "nombre", nullable = false, length = 60)
    private String nombre;

    @Column(name = "estado", nullable = false)
    private Integer estado;

    @Getter(AccessLevel.NONE)
    @OneToMany(mappedBy = "nivel")
    private Set<SubjectModel> materias;
}
