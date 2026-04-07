package br.unipar.devbackend.oscontrol.Service;

import br.unipar.devbackend.oscontrol.Entity.Usuario;
import br.unipar.devbackend.oscontrol.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    public Usuario cadastrar(Usuario usuario) {
        validarUsuario(usuario);
        return repository.save(usuario);
    }

    public Usuario editar(Integer id, Usuario usuarioAtualizado) {
        Usuario usuario = buscarPorId(id);

        validarUsuario(usuarioAtualizado);

        usuario.setNome(usuarioAtualizado.getNome());
        usuario.setLogin(usuarioAtualizado.getLogin());
        usuario.setSenha(usuarioAtualizado.getSenha());
        usuario.setPerfil(usuarioAtualizado.getPerfil());
        usuario.setCpf(usuarioAtualizado.getCpf());
        usuario.setTelefone(usuarioAtualizado.getTelefone());

        return repository.save(usuario);
    }

    public List<Usuario> listarTodos() {
        return repository.findAll();
    }

    public Usuario buscarPorId(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));
    }

    public void excluir(Integer id) {
        Usuario usuario = buscarPorId(id);
        repository.delete(usuario);
    }

    private void validarUsuario(Usuario usuario) {
        if (usuario.getNome() == null || usuario.getNome().isBlank()) {
            throw new RuntimeException("Nome é obrigatório.");
        }
        if (usuario.getLogin() == null || usuario.getLogin().isBlank()) {
            throw new RuntimeException("Login é obrigatório.");
        }
        if (usuario.getSenha() == null || usuario.getSenha().isBlank()) {
            throw new RuntimeException("Senha é obrigatória.");
        }
        if (usuario.getPerfil() == null) {
            throw new RuntimeException("Perfil é obrigatório.");
        }

        if (usuario.getCpf() == null || usuario.getCpf().isBlank()) {
            throw new RuntimeException("CPF é obrigatório.");
        }

        if (usuario.getTelefone() == null || usuario.getTelefone().isBlank()) {
            throw new RuntimeException("Telefone é obrigatório.");
        }

    }
}