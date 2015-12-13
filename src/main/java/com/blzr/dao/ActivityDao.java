package com.blzr.dao;

import com.blzr.InjectLogger;
import com.blzr.domain.User;
import com.google.inject.Inject;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import java.util.List;

public class ActivityDao {
    @InjectLogger
    Logger logger;
    @Inject
    EntityManager em;

    public List<User> findAll() {
        return em.createQuery("FROM User", User.class).getResultList();
    }

    public User findById(Long id) {
        return em.find(User.class, id);
    }
}
