package com.proyecto.backend_2.features.progra;

import java.util.List;

import com.proyecto.backend_2.features.mapa.MapaModel;
import com.proyecto.backend_2.features.notas.NotasModel;
import com.proyecto.backend_2.features.personals.PersonalModel;
import com.proyecto.backend_2.features.users.UserModel;
import com.proyecto.backend_2.ids.PrograId;

import jakarta.persistence.CascadeType;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
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
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "progra")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class PrograModel {
    @EmbeddedId
    private PrograId id;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("codp")
    @JoinColumn(name = "codp")
    private PersonalModel persona;

    @Getter(AccessLevel.NONE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "login")
    private UserModel usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("id")
    @JoinColumns({
            @JoinColumn(name = "codmat", referencedColumnName = "codmat"),
            @JoinColumn(name = "codpar", referencedColumnName = "codpar"),
            @JoinColumn(name = "gestion", referencedColumnName = "gestion"),
    })
    private MapaModel mapa;

    @Getter(AccessLevel.NONE)
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "progra", cascade = CascadeType.ALL)
    List<NotasModel> notas;
}
