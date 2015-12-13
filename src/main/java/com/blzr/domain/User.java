package com.blzr.domain;

import com.google.gson.annotations.Expose;

import javax.persistence.*;

@Entity
public class User {
    @Expose
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Expose
    private String name;

    @Expose
    private String login;

    private String hash;

    private String salt;

    @Expose(serialize = false)
    @Transient
    private String password;

    @Expose
    @Version
    private Long version;

    public User() {
    }

    public User(Long id, String name, String login, String hash, String salt) {
        this.id = id;
        this.name = name;
        this.login = login;
        this.hash = hash;
        this.salt = salt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}
