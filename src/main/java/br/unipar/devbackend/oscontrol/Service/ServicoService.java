package br.unipar.devbackend.oscontrol.Service;

import br.unipar.devbackend.oscontrol.Entity.Servico;
import br.unipar.devbackend.oscontrol.Repository.OsServicoRepository;
import br.unipar.devbackend.oscontrol.Repository.ServicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicoService {

    @Autowired
    private ServicoRepository repository;

    @Autowired
    private OsServicoRepository osServicoRepository;

    public Servico cadastrarServico(Servico servico) {
        validarServico(servico);

        if (repository.existsByDescricao(servico.getDescricao())) {
            throw new RuntimeException("Já existe um serviço cadastrado com este nome.");
        }

        return repository.save(servico);
    }

    public Servico editarServico(Servico servicoAtualizado) {
        Servico servico = buscarPorId(servicoAtualizado.getId());

        validarServico(servicoAtualizado);

        if (!servico.getDescricao().equals(servicoAtualizado.getDescricao())
                && repository.existsByDescricao(servicoAtualizado.getDescricao())) {
            throw new RuntimeException("Já existe um serviço cadastrado com este nome.");
        }

        if (osServicoRepository.existsByServicoId(servico.getId())) {
            throw new RuntimeException("Este serviço não pode ser alterado pois está vinculado a uma OS em aberto.");
        }

        servico.setDescricao(servicoAtualizado.getDescricao());
        servico.setValor(servicoAtualizado.getValor());

        return repository.save(servico);
    }

    public List<Servico> listarTodos() {
        return repository.findAll();
    }

    public Servico buscarPorId(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado."));
    }

    public void excluirServico(Integer id) {
        Servico servico = buscarPorId(id);

        if (osServicoRepository.existsByServicoId(servico.getId())) {
            throw new RuntimeException("Este serviço não pode ser excluído pois está vinculado a uma OS em aberto.");
        }

        repository.delete(servico);
    }

    private void validarServico(Servico servico) {
        if (servico.getDescricao() == null || servico.getDescricao().isBlank()) {
            throw new RuntimeException("Descrição do serviço é obrigatória.");
        }

        if (servico.getValor() == null || servico.getValor() <= 0) {
            throw new RuntimeException("Valor do serviço deve ser maior que zero.");
        }
    }
}