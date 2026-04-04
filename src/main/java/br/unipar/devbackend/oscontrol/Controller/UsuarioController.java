package br.unipar.devbackend.oscontrol.Controller;

import br.unipar.devbackend.oscontrol.Entity.Usuario;
import br.unipar.devbackend.oscontrol.Service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    // Cadastra um novo usuário
    @PostMapping
    public Usuario cadastrar(@RequestBody Usuario usuario) {
        return service.cadastrar(usuario);
    }

    // Lista todos os usuários
    @GetMapping
    public List<Usuario> listarTodos() {
        return service.listarTodos();
    }

    // Busca um usuário pelo ID
    @GetMapping("/{id}")
    public Usuario buscarPorId(@PathVariable Integer id) {
        return service.buscarPorId(id);
    }

    // Edita um usuário pelo ID
    @PutMapping("/{id}")
    public Usuario editar(@PathVariable Integer id, @RequestBody Usuario usuario) {
        return service.editar(id, usuario);
    }

    // Exclui um usuário pelo ID
    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Integer id) {
        service.excluir(id);
    }
}