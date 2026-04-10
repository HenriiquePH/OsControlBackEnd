package br.unipar.devbackend.oscontrol.DTO;


import java.time.LocalDateTime;
import java.util.List;

public class OrcamentoDTO {

    private Integer id;
    private String nomeOrcamento;
    private LocalDateTime dataCriacao;
    private String observacao;

    private Double valorTotalPecas;
    private Double valorTotalServico;
    private Double valorTotal;

    private List<OrcamentoPecaDTO> itensPecas;
    private List<OrcamentoServicoDTO> itensServicos;

    public OrcamentoDTO() {
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

    public List<OrcamentoPecaDTO> getItensPecas() {
        return itensPecas;
    }

    public void setItensPecas(List<OrcamentoPecaDTO> itensPecas) {
        this.itensPecas = itensPecas;
    }

    public List<OrcamentoServicoDTO> getItensServicos() {
        return itensServicos;
    }

    public void setItensServicos(List<OrcamentoServicoDTO> itensServicos) {
        this.itensServicos = itensServicos;
    }
}