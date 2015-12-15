package com.blzr.service;

import com.blzr.InjectLogger;
import com.blzr.ResultType;
import com.google.inject.Inject;
import org.apache.logging.log4j.Logger;

public class CliService {
    @InjectLogger
    private Logger log;

    @Inject
    private AaaService aaaService;

    public ResultType parseArgs(String login, String pass, String res, String role, String ds, String de, String vol) {
        ResultType result;

        // First step: Authentication
        if (login != null && pass != null) {
            log.info("Received login {} and pass {}", login, pass);
            result = authenticate(login, pass);

            // Second step: Authorization
            if (result == ResultType.SUCCESS && res != null && role != null) {
                log.info("Received res {} and role {}", role, res);
                result = authorize(login, role, res);

                // Third step: Accounting
                if (result == ResultType.SUCCESS &&
                        // And all activity values specified
                        ds != null && de != null && vol != null) {
                    log.info("Received ds {} de {} vol {}", ds, de, vol);
                    result = account(login, role, res, ds, de, vol);
                }
            }
            return result;
        } else {
            log.info("No Login and password specified");
            result = ResultType.SUCCESS;
        }

        log.info("Result {}", result);
        return result;
    }

    public ResultType authenticate(String username, String password) {
        if (!aaaService.isUserExist(username)) {
            log.warn("User {} not exists", username);
            return ResultType.UNKNOWN_LOGIN;
        } else if (!aaaService.isPasswordCorrect(username, password)) {
            log.warn("Password {} incorrect", password);
            return ResultType.INVALID_PASSWORD;
        } else {
            log.info("User {} authenticated with password {}", username, password);
            return ResultType.SUCCESS;
        }
    }

    public ResultType authorize(String username, String role, String site) {
        if (!aaaService.isRoleExist(role)) {
            log.warn("Role {} not exist", role);
            return ResultType.UNKNOWN_ROLE;
        } else if (!aaaService.isAuthorized(username, role, site)) {
            log.warn("User {} not authorized {} to {}", username, role, site);
            return ResultType.ACCESS_DENIED;
        } else {
            log.info("User {} authorized {} to {}", username, role, site);
            return ResultType.SUCCESS;
        }
    }

    public ResultType account(String login, String role, String res, String ds, String de, String vol) {
        try {
            Long total = aaaService.addActivity(
                    aaaService.getAuthority(login, role, res),
                    ds, de, vol);
            log.info("Successfully accounted {} total {} activities", login, total);
            return ResultType.SUCCESS;
        } catch (java.text.ParseException e) {
            log.error("Cannot parse data {} or {}", ds, de);
            return ResultType.INVALID_ACTIVITY;
        } catch (NumberFormatException e) {
            log.error("Cannot parse vol {}", vol);
            return ResultType.INVALID_ACTIVITY;
        }
    }
}
