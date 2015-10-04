package com.blzr;

import com.blzr.domain.Authority;
import com.blzr.domain.Role;
import com.blzr.domain.User;

import java.util.ArrayList;
import java.util.List;

public class ValidationService {
    private static List<User> users = new ArrayList<User>() {{
        add(new User(1L, "John Doe", "jdoe", "sup3rpaZZ"));
        add(new User(2L, "Jane Row", "jrow", "Qweqrty12"));
    }};

    private static List<Authority> authorities = new ArrayList<Authority>() {{
        add(new Authority(1L, users.get(1), Role.READ, "a"));
        add(new Authority(2L, users.get(1), Role.WRITE, "a.b"));
        add(new Authority(3L, users.get(2), Role.EXECUTE, "a.b.c"));
    }};

    public User getUser(String user) {
        for (User u : users) {
            if (u.getLogin().equals(user)) {
                return u;
            }
        }
        return null;
    }

    public boolean isUserExist(String username) {
        return getUser(username) != null;
    }

    public boolean isPasswordCorrect(String username, String password) {
        return getUser(username).validatePassword(password);
    }

    public boolean isAuthorized(String username, String site, String role) {
        User user = getUser(username);
        Role r = Role.getRole(role);
        if (r == null) {
            return false;
        }

        for (Authority a : authorities) {
            if (a.getUser() == user && a.getRole() == r) {
                if (site.startsWith(a.getSite())) {
                    return true;
                }
            }
        }
        return false;
    }
}
