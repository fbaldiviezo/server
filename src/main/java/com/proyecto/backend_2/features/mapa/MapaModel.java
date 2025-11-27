package com.proyecto.backend_2.features.mapa;

import java.util.List;

import com.proyecto.backend_2.features.dicta.DictaModel;
import com.proyecto.backend_2.features.parallels.ParallelModel;
import com.proyecto.backend_2.features.progra.PrograModel;
import com.proyecto.backend_2.features.subjects.SubjectModel;
import com.proyecto.backend_2.ids.MapaId;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "mapa")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MapaModel {
    @EmbeddedId
    private MapaId id;

    @Column(name = "estado", nullable = false)
    private Integer estado;

    @Getter(AccessLevel.NONE)
    @ManyToOne
    @MapsId("codpar")
    @JoinColumn(name = "codpar")
    private ParallelModel mapaParalelo;

    @Getter(AccessLevel.NONE)
    @ManyToOne
    @MapsId("codmat")
    @JoinColumn(name = "codmat")
    private SubjectModel mapaMateria;

    @Getter(AccessLevel.NONE)
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "mapa", cascade = CascadeType.ALL)
    private List<DictaModel> dictas;

    @Getter(AccessLevel.NONE)
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "mapa", cascade = CascadeType.ALL)
    private List<PrograModel> progras;
}
