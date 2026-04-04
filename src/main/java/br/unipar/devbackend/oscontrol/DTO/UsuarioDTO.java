package br.unipar.devbackend.oscontrol.DTO;

import br.unipar.devbackend.oscontrol.Entity.PerfilEnum;

public class UsuarioDTO {

    private Integer id;
    private String nome;
    private String login;
    private PerfilEnum perfil;

    public UsuarioDTO() {
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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public PerfilEnum getPerfil() {
        return perfil;
    }

    public void setPerfil(PerfilEnum perfil) {
        this.perfil = perfil;
    }
}