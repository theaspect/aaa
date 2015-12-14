package com.blzr.dao;

import com.blzr.InjectLogger;
import com.blzr.domain.Activity;
import com.google.inject.Inject;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import java.util.List;

public class ActivityDao {
    @InjectLogger
    Logger logger;
    @Inject
    EntityManager em;

    public List<Activity> findAll() {
        return em.createQuery("FROM Activity", Activity.class).getResultList();
    }

    public Activity findById(Long id) {
        return em.find(Activity.class, id);
    }

    public List<Activity> findByAuthorityId(Long authorityId) {
        return em.createQuery("FROM Activity a WHERE a.authority.id = :authority_id", Activity.class)
                .setParameter("authority_id", authorityId)
                .getResultList();
    }
}
