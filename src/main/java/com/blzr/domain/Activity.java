package com.blzr.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Authority authority;

    @Temporal(TemporalType.DATE)
    @Column(name = "login_date")
    private Date loginDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "logout_date")
    private Date logoutDate;

    private Integer volume;

    public Activity(){}

    public Activity(Long id, Authority authority, Date loginDate, Date logoutDate, Integer volume) {
        this.id = id;
        this.authority = authority;
        this.loginDate = loginDate;
        this.logoutDate = logoutDate;
        this.volume = volume;
    }

    public Activity(Authority authority, Date loginDate, Date logoutDate, Integer volume) {
        this(null, authority, loginDate, logoutDate, volume);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Authority getAuthority() {
        return authority;
    }

    public void setAuthority(Authority authority) {
        this.authority = authority;
    }

    public Date getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }

    public Date getLogoutDate() {
        return logoutDate;
    }

    public void setLogoutDate(Date logoutDate) {
        this.logoutDate = logoutDate;
    }

    public Integer getVolume() {
        return volume;
    }

    public void setVolume(Integer volume) {
        this.volume = volume;
    }
}
