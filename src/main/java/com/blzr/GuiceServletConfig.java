package com.blzr;

import com.blzr.servlet.IndexServlet;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Scopes;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;
import org.apache.jasper.servlet.JspServlet;

public class GuiceServletConfig extends GuiceServletContextListener {
    @Override
    protected Injector getInjector() {
        return Guice.createInjector(new ServletModule() {
            @Override
            protected void configureServlets() {
                System.setProperty("org.apache.jasper.compiler.disablejsr199","false");
                System.setProperty("javax.servlet.context.tempdir",System.getProperty("java.io.tmpdir"));

                bind(JspServlet.class).in(Scopes.SINGLETON);
                serve("*.jsp").with(JspServlet.class);

                serve("/*").with(IndexServlet.class);
            }
        });
    }
}
