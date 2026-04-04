package br.unipar.devbackend.oscontrol.DTO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrdemDeServicoDTO {

    private Integer id;
    private LocalDateTime dataAbertura;
    private LocalDateTime dataFechamento;
    private String statusOs;
    private String observacoes;

    private Double valorTotalPecas;
    private Double valorTotalServico;
    private Double valorTotal;

    private Integer clienteId;
    private String clienteNome;

    private Integer veiculoId;
    private String veiculoPlaca;
    private String veiculoModelo;

    private Integer tecnicoResponsavelId;
    private String tecnicoResponsavelNome;

    private List<OsPecaDTO> pecas = new ArrayList<>();
    private List<OsServicoDTO> servicos = new ArrayList<>();

    public OrdemDeServicoDTO() {
    }

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

    public String getStatusOs() {
        return statusOs;
    }

    public void setStatusOs(String statusOs) {
        this.statusOs = statusOs;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
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

    public Integer getClienteId() {
        return clienteId;
    }

    public void setClienteId(Integer clienteId) {
        this.clienteId = clienteId;
    }

    public String getClienteNome() {
        return clienteNome;
    }

    public void setClienteNome(String clienteNome) {
        this.clienteNome = clienteNome;
    }

    public Integer getVeiculoId() {
        return veiculoId;
    }

    public void setVeiculoId(Integer veiculoId) {
        this.veiculoId = veiculoId;
    }

    public String getVeiculoPlaca() {
        return veiculoPlaca;
    }

    public void setVeiculoPlaca(String veiculoPlaca) {
        this.veiculoPlaca = veiculoPlaca;
    }

    public String getVeiculoModelo() {
        return veiculoModelo;
    }

    public void setVeiculoModelo(String veiculoModelo) {
        this.veiculoModelo = veiculoModelo;
    }

    public Integer getTecnicoResponsavelId() {
        return tecnicoResponsavelId;
    }

    public void setTecnicoResponsavelId(Integer tecnicoResponsavelId) {
        this.tecnicoResponsavelId = tecnicoResponsavelId;
    }

    public String getTecnicoResponsavelNome() {
        return tecnicoResponsavelNome;
    }

    public void setTecnicoResponsavelNome(String tecnicoResponsavelNome) {
        this.tecnicoResponsavelNome = tecnicoResponsavelNome;
    }

    public List<OsPecaDTO> getPecas() {
        return pecas;
    }

    public void setPecas(List<OsPecaDTO> pecas) {
        this.pecas = pecas;
    }

    public List<OsServicoDTO> getServicos() {
        return servicos;
    }

    public void setServicos(List<OsServicoDTO> servicos) {
        this.servicos = servicos;
    }
}