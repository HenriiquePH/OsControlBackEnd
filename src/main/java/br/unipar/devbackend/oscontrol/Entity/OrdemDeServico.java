package br.unipar.devbackend.oscontrol.Entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "os")
public class OrdemDeServico {

    // ==========================================
    // ATRIBUTOS
    // ==========================================
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private LocalDateTime dataAbertura;
    private LocalDateTime dataFechamento;

    @Enumerated(EnumType.STRING)
    private StatusOsEnum statusOs;

    private String observacoes;

    private Integer orcamentoId;
    private Double desconto = 0.0;

    private Double valorTotalPecas = 0.0;
    private Double valorTotalServico = 0.0;
    private Double valorTotal = 0.0;

    // Relacionamentos
    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "id_veiculo")
    private Veiculo veiculo;

    @ManyToOne
    @JoinColumn(name = "id_responsavel_tecnico")
    private Usuario tecnicoResponsavel;

    // ==========================================
    // CONSTRUTOR
    // ==========================================
    public OrdemDeServico() {
    }

    // ==========================================
    // MÉTODOS DE NEGÓCIO DO DIAGRAMA
    // ==========================================

    public void criarOs() {
        this.statusOs = StatusOsEnum.ABERTA;
        this.dataAbertura = LocalDateTime.now();
    }

    public void fecharOS() {
        this.statusOs = StatusOsEnum.FECHADA;
        this.dataFechamento = LocalDateTime.now();
    }

    public void atualizarStatus(StatusOsEnum novoStatus) {
        this.statusOs = novoStatus;
    }

    public void calcularValorTotal() {
        double subtotal = (this.valorTotalPecas != null ? this.valorTotalPecas : 0.0) +
                (this.valorTotalServico != null ? this.valorTotalServico : 0.0);

        double valorDesconto = this.desconto != null ? this.desconto : 0.0;

        this.valorTotal = Math.max(0.0, subtotal - valorDesconto);
    }

    public void adicionarPeca(Double valorPeca) {
        this.valorTotalPecas = (this.valorTotalPecas == null ? 0.0 : this.valorTotalPecas) + valorPeca;
        calcularValorTotal();
    }

    public void adicionarServico(Double valorServico) {
        this.valorTotalServico = (this.valorTotalServico == null ? 0.0 : this.valorTotalServico) + valorServico;
        calcularValorTotal();
    }

    // ==========================================
    // GETTERS E SETTERS (SCANNABLE)
    // ==========================================
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getDataAbertura() {
        return dataAbertura;
    }

    public void setDataAbertura(LocalDateTime dataAbertura) {
        this.dataAbertura = dataAbertura;
    }

    public LocalDateTime getDataFechamento() {
        return dataFechamento;
    }

    public void setDataFechamento(LocalDateTime dataFechamento) {
        this.dataFechamento = dataFechamento;
    }

    public StatusOsEnum getStatusOs() {
        return statusOs;
    }

    public void setStatusOs(StatusOsEnum statusOs) {
        this.statusOs = statusOs;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public Integer getOrcamentoId() {
        return orcamentoId;
    }

    public void setOrcamentoId(Integer orcamentoId) {
        this.orcamentoId = orcamentoId;
    }

    public Double getDesconto() {
        return desconto;
    }

    public void setDesconto(Double desconto) {
        this.desconto = desconto;
    }

    public Double getValorTotalPecas() {
        return valorTotalPecas;
    }

    public void setValorTotalPecas(Double valorTotalPecas) {
        this.valorTotalPecas = valorTotalPecas;
    }

    public Double getValorTotalServico() {
        return valorTotalServico;
    }

    public void setValorTotalServico(Double valorTotalServico) {
        this.valorTotalServico = valorTotalServico;
    }

    public Double getValorTotal() {
        return valorTotal;
    }
    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Cliente getCliente() {
        return cliente;
    }
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }

    public Usuario getTecnicoResponsavel() {
        return tecnicoResponsavel;
    }

    public void setTecnicoResponsavel(Usuario tecnicoResponsavel) {
        this.tecnicoResponsavel = tecnicoResponsavel;
    }
}