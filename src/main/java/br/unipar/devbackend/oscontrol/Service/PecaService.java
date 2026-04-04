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

    // Cadastra uma nova peça
    public Peca cadastrarPeca(Peca peca) {

        // Valida os dados da peça antes de salvar
        validarPeca(peca);

        // Salva no banco
        return repository.save(peca);
    }

    // Atualiza uma peça existente
    public Peca atualizar(Integer id, Peca pecaAtualizada) {

        // Busca a peça atual no banco
        Peca peca = buscarPorId(id);

        // Valida os novos dados
        validarPeca(pecaAtualizada);

        // Atualiza os campos
        peca.setDescricao(pecaAtualizada.getDescricao());
        peca.setValorUnitario(pecaAtualizada.getValorUnitario());

        // Salva as alterações
        return repository.save(peca);
    }

    // Lista todas as peças
    public List<Peca> listarTodas() {
        return repository.findAll();
    }

    // Busca uma peça pelo ID
    public Peca buscarPorId(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Peça não encontrada."));
    }

    // Exclui uma peça
    public void excluirPeca(Integer id) {
        Peca peca = buscarPorId(id);
        repository.delete(peca);
    }

    // Método privado para validar os dados obrigatórios da peça
    private void validarPeca(Peca peca) {

        // Verifica se a descrição foi preenchida
        if (peca.getDescricao() == null || peca.getDescricao().isBlank()) {
            throw new RuntimeException("Descrição da peça é obrigatória.");
        }

        // Verifica se o valor unitário foi informado e é maior que zero
        if (peca.getValorUnitario() == null || peca.getValorUnitario() <= 0) {
            throw new RuntimeException("Valor unitário da peça deve ser maior que zero.");
        }
    }
}