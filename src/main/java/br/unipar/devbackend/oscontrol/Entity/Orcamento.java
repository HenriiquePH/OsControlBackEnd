package br.unipar.devbackend.oscontrol.Entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orcamento")
public class Orcamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nomeOrcamento;
    private LocalDateTime dataCriacao;
    private String observacao;

    private Double valorTotalPecas = 0.0;
    private Double valorTotalServico = 0.0;
    private Double valorTotal = 0.0;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_orcamento")
    private List<OrcamentoPeca> itensPecas;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_orcamento")
    private List<OrcamentoServico> itensServicos;

    public Orcamento() {
        this.dataCriacao = LocalDateTime.now();
    }

    public void calcularValorTotal() {
        double pecas = (this.valorTotalPecas != null) ? this.valorTotalPecas : 0.0;
        double servicos = (this.valorTotalServico != null) ? this.valorTotalServico : 0.0;
        this.valorTotal = pecas + servicos;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNomeOrcamento() {
        return nomeOrcamento;
    }

    public void setNomeOrcamento(String nomeOrcamento) {
        this.nomeOrcamento = nomeOrcamento;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Double getValorTotalPecas() {
        return valorTotalPecas;
    }

    public void setValorTotalPecas(Double valorTotalPecas) {
        this.valorTotalPecas = valorTotalPecas;
        calcularValorTotal();
    }

    public Double getValorTotalServico() {
        return valorTotalServico;
    }

    public void setValorTotalServico(Double valorTotalServico) {
        this.valorTotalServico = valorTotalServico;
        calcularValorTotal();
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public List<OrcamentoPeca> getItensPecas() {
        return itensPecas;
    }

    public void setItensPecas(List<OrcamentoPeca> itensPecas) {
        this.itensPecas = itensPecas;
    }

    public List<OrcamentoServico> getItensServicos() {
        return itensServicos;
    }

    public void setItensServicos(List<OrcamentoServico> itensServicos) {
        this.itensServicos = itensServicos;
    }
}