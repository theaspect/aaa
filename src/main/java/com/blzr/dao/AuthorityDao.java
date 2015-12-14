package com.blzr.dao;

import com.blzr.InjectLogger;
import com.blzr.domain.Authority;
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
}
