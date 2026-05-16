package br.unipar.devbackend.oscontrol.Service;

import br.unipar.devbackend.oscontrol.Entity.Cidade;
import br.unipar.devbackend.oscontrol.Repository.CidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CidadeService {

    @Autowired
    private CidadeRepository repository;

    public List<Cidade> buscarTodos() {
        return repository.findAll();
    }

    public Cidade buscarPorId(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cidade nao encontrada."));
    }

    public Optional<Cidade> buscarPorNomeEEstado(String nome, Integer estadoId) {
        return repository.findByNomeIgnoreCaseAndEstado_Id(nome, estadoId);
    }

    public Cidade salvar(Cidade cidade) {
        return repository.save(cidade);
    }
}
