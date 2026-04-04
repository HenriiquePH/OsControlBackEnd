package br.unipar.devbackend.oscontrol.DTO;

public class OsPecaDTO {

    private Integer id;
    private Integer pecaId;
    private String descricaoPeca;
    private Integer quantidade;
    private Double valorUnitario;
    private Double valorTotal;

    public OsPecaDTO() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public Integer getPecaId() {
        return pecaId;
    }

    public void setPecaId(Integer pecaId) {
        this.pecaId = pecaId;
    }


    public String getDescricaoPeca() {
        return descricaoPeca;
    }

    public void setDescricaoPeca(String descricaoPeca) {
        this.descricaoPeca = descricaoPeca;
    }


    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }


    public Double getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(Double valorUnitario) {
        this.valorUnitario = valorUnitario;
    }


    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }
}