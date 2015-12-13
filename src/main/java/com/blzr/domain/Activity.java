package com.blzr.domain;

import com.google.gson.annotations.Expose;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Activity {
    @Expose
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Access(AccessType.PROPERTY)
    @ManyToOne
    private Authority authority;

    @Expose
    @Transient
    private Long authorityId;

    @Expose
    @Temporal(TemporalType.DATE)
    @Column(name = "login_date")
    private Date loginDate;

    @Expose
    @Temporal(TemporalType.DATE)
    @Column(name = "logout_date")
    private Date logoutDate;

    @Expose
    private Integer volume;

    @Expose
    @Version
    private Long version;

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
        if (authority != null) {
            this.authorityId = authority.getId();
        }
    }

    public Long getAuthorityId() {
        return authorityId;
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

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}
