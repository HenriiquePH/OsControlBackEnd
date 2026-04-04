package br.unipar.devbackend.oscontrol.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "orcamento_peca")
public class OrcamentoPeca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Double valorUnitario;
    private Integer quantidade;
    private Double valorTotal;

    @ManyToOne
    @JoinColumn(name = "id_peca")
    private Peca peca;

    public OrcamentoPeca() {
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

    public Double getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(Double valorUnitario) {
        this.valorUnitario = valorUnitario;
        calcularSubtotal();
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
        calcularSubtotal();
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Peca getPeca() {
        return peca;
    }

    public void setPeca(Peca peca) {
        this.peca = peca;
    }
}