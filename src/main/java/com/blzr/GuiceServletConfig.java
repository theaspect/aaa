package com.blzr;

import com.blzr.servlet.AjaxServlet;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;

public class GuiceServletConfig extends GuiceServletContextListener {
    @Override
    protected Injector getInjector() {
        return Guice.createInjector(new ServletModule() {
            @Override
            protected void configureServlets() {
                serve("/ajax/*").with(AjaxServlet.class);
            }
        });
    }
}
