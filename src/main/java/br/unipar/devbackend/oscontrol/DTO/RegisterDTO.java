package br.unipar.devbackend.oscontrol.DTO;

import br.unipar.devbackend.oscontrol.Entity.PerfilEnum;

public record RegisterDTO(String nome, String login, String password, PerfilEnum role) {
}
