package br.unipar.devbackend.oscontrol.Entity;

public enum PerfilEnum {
    ROLE_ADMIN("admin"),
    ROLE_USUARIO("user");

    private String role;

    PerfilEnum(String role) {
        this.role = role;
    }

    public String getRole() {
        return this.role;
    }
}

