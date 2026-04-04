package br.unipar.devbackend.oscontrol.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "os_peca")
public class OsPeca {

    // ==========================================
    // ATRIBUTOS
    // ==========================================
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer quantidade = 0;
    private Double valorUnitario = 0.0;
    private Double valorTotal = 0.0;

    @ManyToOne
    @JoinColumn(name = "id_os")
    private OrdemDeServico os;

    @ManyToOne
    @JoinColumn(name = "id_peca")
    private Peca peca;

    // ==========================================
    // CONSTRUTOR
    // ==========================================
    public OsPeca() {
    }

    // ==========================================
    // MÉTODOS DE NEGÓCIO
    // ==========================================

    public void calcularSubtotal() {
        double qtd = (this.quantidade != null) ? this.quantidade : 0.0;
        double valorUn = (this.valorUnitario != null) ? this.valorUnitario : 0.0;
        this.valorTotal = qtd * valorUn;
    }

    // ==========================================
    // GETTERS E SETTERS
    // ==========================================
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getQuantidade() { return quantidade; }
    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
        calcularSubtotal(); // Atualiza automaticamente
    }

    public Double getValorUnitario() { return valorUnitario; }
    public void setValorUnitario(Double valorUnitario) {
        this.valorUnitario = valorUnitario;
        calcularSubtotal(); // Atualiza automaticamente
    }

    public Double getValorTotal() { return valorTotal; }
    public void setValorTotal(Double valorTotal) { this.valorTotal = valorTotal; }

    public OrdemDeServico getOs() { return os; }
    public void setOs(OrdemDeServico os) { this.os = os; }

    public Peca getPeca() { return peca; }
    public void setPeca(Peca peca) { this.peca = peca; }
}