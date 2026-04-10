package br.unipar.devbackend.oscontrol.Service;

import br.unipar.devbackend.oscontrol.Entity.Peca;
import br.unipar.devbackend.oscontrol.Repository.PecaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PecaService {

    @Autowired
    private PecaRepository repository;

    public Peca cadastrarPeca(Peca peca) {
        validarPeca(peca);

        return repository.save(peca);
    }

    private void validarPeca(Peca peca) {

        if (peca.getDescricao() == null || peca.getDescricao().isBlank()) {
            throw new RuntimeException("Descrição da peça é obrigatória.");
        }

        if (peca.getValorUnitario() == null || peca.getValorUnitario() <= 0) {
            throw new RuntimeException("Valor unitário da peça deve ser maior que zero.");
        }
    }

    public Peca atualizar(Integer id, Peca pecaAtualizada) {
        Peca peca = buscarPorId(id);

        validarPeca(pecaAtualizada);

        peca.setDescricao(pecaAtualizada.getDescricao());
        peca.setValorUnitario(pecaAtualizada.getValorUnitario());

        return repository.save(peca);
    }

    public List<Peca> listarTodas() {
        return repository.findAll();
    }

    public Peca buscarPorId(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Peça não encontrada."));
    }

    public void excluirPeca(Integer id) {
        Peca peca = buscarPorId(id);
        repository.delete(peca);
    }
}