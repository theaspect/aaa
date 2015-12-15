package com.blzr.dao;

import com.blzr.InjectLogger;
import com.blzr.domain.Authority;
import com.blzr.domain.Role;
import com.blzr.domain.User;
import com.google.inject.Inject;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import java.util.List;

public class AuthorityDao {
    @InjectLogger
    Logger logger;
    @Inject
    EntityManager em;

    public List<Authority> findAll() {
        return em.createQuery("FROM Authority", Authority.class).getResultList();
    }

    public Authority findById(Long id) {
        return em.find(Authority.class, id);
    }

    public List<Authority> findByUserId(Long userId) {
        return em.createQuery("FROM Authority a WHERE a.user.id = :user_id", Authority.class)
                .setParameter("user_id", userId)
                .getResultList();
    }

    public List<Authority> getAuthoritiesByUserAndRole(User user, Role role) {
        return em.createQuery("FROM Authority a WHERE a.user = :user and a.role = :role", Authority.class)
                .setParameter("user", user)
                .setParameter("role", role)
                .getResultList();
    }
}
