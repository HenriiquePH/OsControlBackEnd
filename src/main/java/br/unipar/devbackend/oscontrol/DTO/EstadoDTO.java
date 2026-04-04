package br.unipar.devbackend.oscontrol.DTO;

public class EstadoDTO {

    private Integer id;
    private String nome;

    public EstadoDTO() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}