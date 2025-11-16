package com.proyecto.backend_2.features.itemat;

import com.proyecto.backend_2.features.items.ItemModel;
import com.proyecto.backend_2.features.subjects.SubjectModel;
import com.proyecto.backend_2.ids.ItematId;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "itemat")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItematModel {
    @EmbeddedId
    private ItematId id;

    @Column(name = "estado", nullable = false)
    private Integer estado;

    @Column(name = "ponderacion", nullable = false)
    private Integer ponderacion;

    @Getter(AccessLevel.NONE)
    @ManyToOne
    @MapsId("codmat")
    @JoinColumn(name = "codmat")
    private SubjectModel iteMateria;

    @Getter(AccessLevel.NONE)
    @ManyToOne
    @MapsId("codi")
    @JoinColumn(name = "codi")
    private ItemModel iteItem;
}
