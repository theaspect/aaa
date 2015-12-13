package com.blzr.domain;

import com.google.gson.annotations.Expose;

import javax.persistence.*;

@Entity
public class Authority {
    @Expose
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @Access(AccessType.PROPERTY)
    private User user;

    @Expose
    @Transient
    private Long userId;

    @Expose
    @Enumerated(EnumType.STRING)
    private Role role;

    @Expose
    private String site;

    @Expose
    @Version
    private Long version;

    public Authority() {
    }

    public Authority(Long id, User user, Role role, String site) {
        this.id = id;
        this.user = user;
        this.role = role;
        this.site = site;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
        if (user != null) {
            userId = user.getId();
        }
    }

    public Long getUserId() {
        return userId;
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

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}
