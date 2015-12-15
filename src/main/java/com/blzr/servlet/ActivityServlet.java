package com.blzr.servlet;

import com.blzr.InjectLogger;
import com.blzr.dao.ActivityDao;
import com.blzr.service.CliService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Singleton
public class ActivityServlet extends HttpServlet {
    @InjectLogger
    Logger log;

    @Inject
    ActivityDao dao;

    @Inject
    Provider<Gson> gsonProvider;

    @Inject
    CliService cliService;

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

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String> json = new Gson().fromJson(
                req.getReader(),
                new TypeToken<HashMap<String, String>>() {
                }.getType());
        log.debug(json);

        resp.getWriter().write(cliService.parseArgs(
                json.get("username"), json.get("pass"),
                json.get("res"), json.get("role"),
                json.get("ds"), json.get("de"), json.get("vol")).toString());
    }
}
