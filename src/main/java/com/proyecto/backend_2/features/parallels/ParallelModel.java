package com.proyecto.backend_2.features.parallels;

import java.util.Set;

import com.proyecto.backend_2.features.mapa.MapaModel;

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
@Table(name = "paralelos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ParallelModel {
    @Id
    @Column(name = "codpar", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codpar;

    @Column(name = "nombre", nullable = false, length = 40)
    private String nombre;

    @Column(name = "estado", nullable = false)
    private Integer estado;

    @Getter(AccessLevel.NONE)
    @OneToMany(mappedBy = "mapaParalelo")
    private Set<MapaModel> mapa;
}
