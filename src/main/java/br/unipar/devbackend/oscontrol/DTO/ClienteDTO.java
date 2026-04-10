package br.unipar.devbackend.oscontrol.DTO;

import java.util.List;

public class ClienteDTO {

    private Integer id;
    private String nome;
    private String cpf;
    private String telefone;
    private String email;
    private EnderecoClienteDTO endereco;
    private List<VeiculoDTO> veiculos;

    public ClienteDTO() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public EnderecoClienteDTO getEndereco() {
        return endereco;
    }

    public void setEndereco(EnderecoClienteDTO endereco) {
        this.endereco = endereco;
    }

    public List<VeiculoDTO> getVeiculos() {
        return veiculos;
    }

    public void setVeiculos(List<VeiculoDTO> veiculos) {
        this.veiculos = veiculos;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }


    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}