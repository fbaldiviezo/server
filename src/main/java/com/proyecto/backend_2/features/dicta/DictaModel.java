package com.proyecto.backend_2.features.dicta;

import com.proyecto.backend_2.features.mapa.MapaModel;
import com.proyecto.backend_2.features.personals.PersonalModel;
import com.proyecto.backend_2.features.users.UserModel;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "dicta")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DictaModel {
    @Id
    @Column(name = "coddic", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer coddic;

    @Getter(AccessLevel.NONE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "codp")
    private PersonalModel persona;

    @Getter(AccessLevel.NONE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "login")
    private UserModel usuario;

    @Getter(AccessLevel.NONE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "codmat", referencedColumnName = "codmat"),
            @JoinColumn(name = "codpar", referencedColumnName = "codpar"),
            @JoinColumn(name = "gestion", referencedColumnName = "gestion"),
    })
    private MapaModel mapa;
}
