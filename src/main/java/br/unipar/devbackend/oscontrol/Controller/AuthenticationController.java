package br.unipar.devbackend.oscontrol.Controller;

import br.unipar.devbackend.oscontrol.DTO.AuthenticationDTO;
import br.unipar.devbackend.oscontrol.DTO.LoginResponseDTO;
import br.unipar.devbackend.oscontrol.DTO.RegisterDTO;
import br.unipar.devbackend.oscontrol.Entity.Usuario;
import br.unipar.devbackend.oscontrol.InfraSecurity.TokenService;
import br.unipar.devbackend.oscontrol.Repository.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private TokenService tokenService;


    @PostMapping("/login")
    public ResponseEntity login (@RequestBody @Valid AuthenticationDTO data){

    var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());   //Valida se usuário e senha existem no bd
    var auth = this.authenticationManager.authenticate(usernamePassword);

    var token = tokenService.generateToken((Usuario)auth.getPrincipal());

    return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity register (@RequestBody @Valid RegisterDTO data) {
        if (this.repository.findByLogin(data.login()) != null) return ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        Usuario newUser = new Usuario(data.login(), encryptedPassword, data.role());
        newUser.setNome(data.nome());
        newUser.setCpf(data.cpf());
        newUser.setTelefone(data.telefone());

        this.repository.save(newUser);

        return ResponseEntity.ok().build();
    }
}
