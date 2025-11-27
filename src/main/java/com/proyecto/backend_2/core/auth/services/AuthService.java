package com.proyecto.backend_2.core.auth.services;

import java.time.LocalDate;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.proyecto.backend_2.core.auth.dtos.AuthResponse;
import com.proyecto.backend_2.core.auth.dtos.ChangePasswordRequest;
import com.proyecto.backend_2.core.auth.dtos.LoginRequest;
import com.proyecto.backend_2.core.auth.dtos.RegisterRequest;
import com.proyecto.backend_2.exceptions.ResourceAlreadyExistsException;
import com.proyecto.backend_2.exceptions.ResourceNotFoundException;
import com.proyecto.backend_2.features.general.GeneralModel;
import com.proyecto.backend_2.features.general.GeneralRepository;
import com.proyecto.backend_2.features.users.UserModel;
import com.proyecto.backend_2.features.users.UserRepository;
import com.proyecto.backend_2.features.users.services.UsuariosMenuService;
import com.proyecto.backend_2.ids.GeneralId;
import com.proyecto.backend_2.utils.ApiResponse;
import com.proyecto.backend_2.utils.CustomResponseBuilder;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
        private final UserRepository repository;
        private final UsuariosMenuService usuariosMenuService;
        private final JwtService jwtService;
        private final GeneralRepository generalRepository;
        private final PasswordEncoder passwordEncoder;
        private final AuthenticationManager authenticationManager;
        private final CustomResponseBuilder customResponseBuilder;

        public AuthResponse login(LoginRequest login) {
                authenticationManager
                                .authenticate(new UsernamePasswordAuthenticationToken(login.getLogin(),
                                                login.getPassword()));

                UserDetails user = repository.findByLogin(login.getLogin())
                                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

                String token = jwtService.getToken(user);
                return AuthResponse.builder()
                                .persona(repository.getPersonaUsuario(login.getLogin()))
                                .token(token)
                                .roles(this.usuariosMenuService.obtenerRolesMenusProcesos(login.getLogin()))
                                .build();
        }

        public ResponseEntity<ApiResponse> login2(LoginRequest login) {
                authenticationManager
                                .authenticate(new UsernamePasswordAuthenticationToken(login.getLogin(),
                                                login.getPassword()));
                UserModel user = repository.findByLogin(login.getLogin())
                                .orElseThrow(() -> new ResourceNotFoundException("No se encontro el usuario"));
                Boolean getAccess = repository.existsByLoginAndPerson_Tipo(login.getLogin(), "E");
                if (!getAccess) {
                        throw new ResourceNotFoundException("Solo estudiante pueden ingresar");
                }
                String token = jwtService.getToken(user);
                AuthResponse response = AuthResponse.builder()
                                .persona(repository.getPersonaUsuario(login.getLogin()))
                                .token(token)
                                .build();
                return customResponseBuilder.buildResponse("Bienvenido!!", response);
        }

        public ResponseEntity<ApiResponse> register(RegisterRequest register) {
                if (repository.existsById(register.getLogin())) {
                        throw new ResourceAlreadyExistsException("El recurso ya existe");
                }
                if (repository.getCodp(register.getCodp())) {
                        throw new ResourceAlreadyExistsException("La persona ya tiene un login");
                }
                UserModel userRegister = UserModel.builder()
                                .login(register.getLogin())
                                .password(passwordEncoder.encode(register.getPassword()))
                                .estado(1)
                                .build();
                repository.save(userRegister);
                repository.changeCodp(register.getCodp(), register.getLogin());

                String codg = "G" + register.getLogin();
                Integer gestion = LocalDate.now().getYear();
                GeneralId gId = new GeneralId(codg, register.getLogin());
                generalRepository.save(new GeneralModel(gId, gestion, userRegister));

                AuthResponse auth = AuthResponse.builder()
                                .token(jwtService.getToken(userRegister))
                                .build();
                return customResponseBuilder.buildResponse("Registrado con exito", auth);
        }

        public ResponseEntity<ApiResponse> changePassword(ChangePasswordRequest request) {
                UserModel user = repository.findByLogin(request.getLogin())
                                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
                user.setPassword(passwordEncoder.encode(request.getNewPassword()));
                repository.save(user);
                return customResponseBuilder.buildResponse("Contraseña modificada con exito", null);
        }
}