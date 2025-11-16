package com.proyecto.backend_2.features.general;

import com.proyecto.backend_2.features.users.UserModel;
import com.proyecto.backend_2.ids.GeneralId;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "general")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GeneralModel {
    @EmbeddedId
    private GeneralId id;

    @Column(name = "gestion", nullable = false)
    private Integer gestion;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @OneToOne
    @MapsId("login")
    @JoinColumn(name = "login")
    private UserModel usuario;
}
