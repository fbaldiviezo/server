package com.proyecto.backend_2.features.subjects;

import java.util.Set;

import com.proyecto.backend_2.features.itemat.ItematModel;
import com.proyecto.backend_2.features.levels.LevelModel;
import com.proyecto.backend_2.features.mapa.MapaModel;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "materias")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class SubjectModel {
    @Id
    @Column(name = "codmat", nullable = false, length = 15)
    private String codmat;

    @Column(name = "nombre", nullable = false, length = 30)
    private String nombre;

    @Column(name = "estado", nullable = false)
    private Integer estado;

    @ManyToOne
    @JoinColumn(name = "codn")
    private LevelModel nivel;

    @Getter(AccessLevel.NONE)
    @OneToMany(mappedBy = "mapaMateria")
    private Set<MapaModel> mapa;

    @Getter(AccessLevel.NONE)
    @OneToMany(mappedBy = "iteMateria")
    private Set<ItematModel> items;
}
