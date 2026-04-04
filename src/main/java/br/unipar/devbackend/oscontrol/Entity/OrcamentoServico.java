package br.unipar.devbackend.oscontrol.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "orcamento_servico")
public class OrcamentoServico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer quantidade = 0;
    private Double valorUnitario = 0.0;
    private Double valorTotal = 0.0;

    @ManyToOne
    @JoinColumn(name = "id_servico")
    private Servico servico;

    public OrcamentoServico() {
    }

    public void calcularSubtotal() {
        double qtd = (this.quantidade != null) ? this.quantidade : 0.0;
        double valorUn = (this.valorUnitario != null) ? this.valorUnitario : 0.0;
        this.valorTotal = qtd * valorUn;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
        calcularSubtotal();
    }

    public Double getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(Double valorUnitario) {
        this.valorUnitario = valorUnitario;
        calcularSubtotal();
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Servico getServico() {
        return servico;
    }

    public void setServico(Servico servico) {
        this.servico = servico;
    }
}