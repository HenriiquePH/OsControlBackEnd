package br.unipar.devbackend.oscontrol.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "os_servico")
public class OsServico {

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
    @JoinColumn(name = "id_servico")
    private Servico servico;

    // ==========================================
    // CONSTRUTOR
    // ==========================================
    public OsServico() {
    }

    // ==========================================
    // MÉTODOS DE NEGÓCIO
    // ==========================================

    /**
     * Regra de negócio: Cálculo matemático do item.
     * Importante para coerência com o Diagrama de Classes.
     */
    public void calcularSubtotal() {
        double qtd = (this.quantidade != null) ? this.quantidade : 0.0;
        double valorUn = (this.valorUnitario != null) ? this.valorUnitario : 0.0;
        this.valorTotal = qtd * valorUn;
    }

    // ==========================================
    // GETTERS E SETTERS (FORMATADOS)
    // ==========================================
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
        calcularSubtotal(); // Atualiza o total sempre que a quantidade mudar
    }

    public Double getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(Double valorUnitario) {
        this.valorUnitario = valorUnitario;
        calcularSubtotal(); // Atualiza o total sempre que o preço unitário mudar
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public OrdemDeServico getOs() {
        return os;
    }

    public void setOs(OrdemDeServico os) {
        this.os = os;
    }

    public Servico getServico() {
        return servico;
    }

    public void setServico(Servico servico) {
        this.servico = servico;
    }
}