package com.blzr.domain;

public class Authority {
    private Long id;
    private Long idUser;
    private Role role;
    private String site;

    public Authority(Long id, Long idUser, Role role, String site) {
        this.id = id;
        this.idUser = idUser;
        this.role = role;
        this.site = site;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }
}
