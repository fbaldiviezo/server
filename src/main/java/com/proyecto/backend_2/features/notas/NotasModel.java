package com.proyecto.backend_2.features.notas;

import com.proyecto.backend_2.features.progra.PrograModel;
import com.proyecto.backend_2.ids.NotaId;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "notas")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class NotasModel {
        @EmbeddedId
        private NotaId id;

        @Getter(AccessLevel.NONE)
        @Setter(AccessLevel.NONE)
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumns({
                        @JoinColumn(name = "codp", referencedColumnName = "codp", insertable = false, updatable = false),
                        @JoinColumn(name = "codmat", referencedColumnName = "codmat", insertable = false, updatable = false),
                        @JoinColumn(name = "codpar", referencedColumnName = "codpar", insertable = false, updatable = false),
                        @JoinColumn(name = "gestion", referencedColumnName = "gestion", insertable = false, updatable = false),
        })
        private PrograModel progra;

        @Column(name = "nota", nullable = false)
        private Integer nota;
}
