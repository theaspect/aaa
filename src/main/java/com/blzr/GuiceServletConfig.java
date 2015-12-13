package com.blzr;

import com.blzr.dao.ActivityDao;
import com.blzr.dao.AuthorityDao;
import com.blzr.dao.UserDao;
import com.blzr.servlet.ActivityServlet;
import com.blzr.servlet.AuthorityServlet;
import com.blzr.servlet.UserServlet;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.inject.*;
import com.google.inject.matcher.Matchers;
import com.google.inject.persist.PersistFilter;
import com.google.inject.persist.jpa.JpaPersistModule;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;
import com.google.inject.spi.TypeEncounter;
import com.google.inject.spi.TypeListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;

public class GuiceServletConfig extends GuiceServletContextListener {
    @Override
    protected Injector getInjector() {
        return Guice.createInjector(new ServletModule() {
            @Override
            protected void configureServlets() {
                bindListener(Matchers.any(), new Log4JTypeListener());

                install(new JpaPersistModule("myJpaUnit"));
                filter("/*").through(PersistFilter.class);

                bind(Gson.class).toProvider(GsonProvider.class);

                bind(ActivityDao.class);
                bind(AuthorityDao.class);
                bind(UserDao.class);

                serve("/ajax/activity").with(ActivityServlet.class);
                serve("/ajax/authority").with(AuthorityServlet.class);
                serve("/ajax/user").with(UserServlet.class);
            }
        });
    }

    static class GsonProvider implements Provider<Gson> {
        @Override
        public Gson get() {
            return new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        }
    }

    class Log4JTypeListener implements TypeListener {
        public <T> void hear(TypeLiteral<T> typeLiteral, TypeEncounter<T> typeEncounter) {
            Class<?> clazz = typeLiteral.getRawType();
            while (clazz != null) {
                for (Field field : clazz.getDeclaredFields()) {
                    if (field.getType() == Logger.class &&
                            field.isAnnotationPresent(InjectLogger.class)) {
                        typeEncounter.register(new Log4JMembersInjector<T>(field));
                    }
                }
                clazz = clazz.getSuperclass();
            }
        }
    }

    class Log4JMembersInjector<T> implements MembersInjector<T> {
        private final Field field;
        private final Logger logger;

        Log4JMembersInjector(Field field) {
            this.field = field;
            this.logger = LogManager.getLogger(field.getDeclaringClass());
            field.setAccessible(true);
        }

        public void injectMembers(T t) {
            try {
                field.set(t, logger);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
