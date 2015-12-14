package com.blzr.servlet;

import com.blzr.dao.ActivityDao;
import com.google.gson.Gson;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Singleton
public class ActivityServlet extends HttpServlet {
    @Inject
    ActivityDao dao;

    @Inject
    Provider<Gson> gsonProvider;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final Long id = req.getParameter("id") == null ? null : Long.valueOf(req.getParameter("id"));
        final Long authorityId = req.getParameter("authorityId") == null ? null : Long.valueOf(req.getParameter("authorityId"));
        if (id != null) {
            gsonProvider.get().toJson(dao.findById(id), resp.getWriter());
        } else if (authorityId != null) {
            gsonProvider.get().toJson(dao.findByAuthorityId(authorityId), resp.getWriter());
        } else {
            gsonProvider.get().toJson(dao.findAll(), resp.getWriter());
        }
    }
}
