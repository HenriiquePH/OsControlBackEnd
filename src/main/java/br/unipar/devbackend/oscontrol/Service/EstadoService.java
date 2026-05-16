package br.unipar.devbackend.oscontrol.Service;

import br.unipar.devbackend.oscontrol.Entity.Estado;
import br.unipar.devbackend.oscontrol.Repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EstadoService {

    @Autowired
    private EstadoRepository repository;

    public List<Estado> buscarTodos() {
        return repository.findAll();
    }

    public Estado buscarPorId(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estado nao encontrado."));
    }

    public Optional<Estado> buscarPorUf(String uf) {
        return repository.findByUfIgnoreCase(uf);
    }

    public Optional<Estado> buscarPorNome(String nome) {
        return repository.findByNomeIgnoreCase(nome);
    }

    public Estado salvar(Estado estado) {
        return repository.save(estado);
    }
}
