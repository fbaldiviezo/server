package com.proyecto.backend_2.features.users;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.proyecto.backend_2.features.dicta.DictaModel;
import com.proyecto.backend_2.features.general.GeneralModel;
import com.proyecto.backend_2.features.personals.PersonalModel;
import com.proyecto.backend_2.features.progra.PrograModel;
import com.proyecto.backend_2.features.usurol.UsurolModel;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = {})
@Builder
public class UserModel implements UserDetails {
    @Id
    @Column(name = "login", nullable = false, length = 10)
    @EqualsAndHashCode.Include
    private String login;

    @Column(name = "estado", nullable = false)
    private Integer estado;

    @JsonIgnore
    @Column(name = "password", nullable = false, length = 200)
    private String password;

    @Getter(AccessLevel.NONE)
    @OneToOne
    @JoinColumn(name = "codp")
    private PersonalModel person;

    @Getter(AccessLevel.NONE)
    @OneToMany(mappedBy = "usuarioRol", fetch = FetchType.LAZY)
    private Set<UsurolModel> roles;

    @Getter(AccessLevel.NONE)
    @OneToOne(mappedBy = "usuario")
    private GeneralModel general;

    @Getter(AccessLevel.NONE)
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "usuario", cascade = CascadeType.ALL)
    List<DictaModel> dicta;

    @Getter(AccessLevel.NONE)
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "usuario", cascade = CascadeType.ALL)
    List<PrograModel> progra;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getUsername() {
        return this.login;
    }

    @Override
    public String getPassword() {
        return this.password;
    }
}
