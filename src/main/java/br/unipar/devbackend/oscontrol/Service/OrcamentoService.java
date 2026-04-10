package br.unipar.devbackend.oscontrol.Service;

import br.unipar.devbackend.oscontrol.Entity.Orcamento;
import br.unipar.devbackend.oscontrol.Entity.OrcamentoPeca;
import br.unipar.devbackend.oscontrol.Entity.OrcamentoServico;
import br.unipar.devbackend.oscontrol.Repository.OrcamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrcamentoService {

    @Autowired
    private OrcamentoRepository repository;

    public Orcamento cadastrar(Orcamento orcamento) {
        calcularTotais(orcamento);
        return repository.save(orcamento);
    }

    public Orcamento editar(Integer id, Orcamento orcamentoAtualizado) {
        Orcamento orcamento = buscarPorId(id);

        orcamento.setNomeOrcamento(orcamentoAtualizado.getNomeOrcamento());
        orcamento.setDataCriacao(orcamentoAtualizado.getDataCriacao());
        orcamento.setObservacao(orcamentoAtualizado.getObservacao());
        orcamento.setItensPecas(orcamentoAtualizado.getItensPecas());
        orcamento.setItensServicos(orcamentoAtualizado.getItensServicos());

        calcularTotais(orcamento);

        return repository.save(orcamento);
    }

    public List<Orcamento> listarTodos() {
        return repository.findAll();
    }

    public Orcamento buscarPorId(Integer id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Orçamento não encontrado. ID: " + id));
    }

    public List<Orcamento> buscarPorNome(String nome) {
        return repository.findByNomeOrcamentoContainingIgnoreCase(nome);
    }

    public void excluir(Integer id) {
        Orcamento orcamento = buscarPorId(id);
        repository.delete(orcamento);
    }

    private void calcularTotais(Orcamento orcamento) {
        double totalServicos = 0.0;
        double totalPecas = 0.0;

        if (orcamento.getItensServicos() != null) {
            for (OrcamentoServico servicoItem : orcamento.getItensServicos()) {
                double valorUnitario = servicoItem.getValorUnitario() != null ? servicoItem.getValorUnitario() : 0.0;

                int quantidade = servicoItem.getQuantidade() != null ? servicoItem.getQuantidade() : 0;

                double totalItem = valorUnitario * quantidade;
                servicoItem.setValorTotal(totalItem);
                totalServicos += totalItem;
            }
        }

        if (orcamento.getItensPecas() != null) {
            for (OrcamentoPeca pecaItem : orcamento.getItensPecas()) {
                double valorUnitario = pecaItem.getValorUnitario() != null ? pecaItem.getValorUnitario() : 0.0;

                int quantidade = pecaItem.getQuantidade() != null ? pecaItem.getQuantidade() : 0;

                double totalItem = valorUnitario * quantidade;
                pecaItem.setValorTotal(totalItem);
                totalPecas += totalItem;
            }
        }

        orcamento.setValorTotalServico(totalServicos);
        orcamento.setValorTotalPecas(totalPecas);
        orcamento.setValorTotal(totalServicos + totalPecas);
    }
}