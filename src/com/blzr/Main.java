package com.blzr;

import org.apache.commons.cli.*;

public class Main {
    private final Options options;
    private final ValidationService validationService = new ValidationService();

    public static void main(String[] args) {
        ResultType result = new Main().parseArgs(args);
        System.exit(result.getCode());
    }

    private Main() {
        options = new Options()
                .addOption("u", true, "username")
                .addOption("p", true, "password")
                .addOption("s", true, "site")
                .addOption("r", true, "role")
                .addOption("ds", false, "date start yyyy-mm-dd")
                .addOption("de", false, "date end yyyy-mm-dd")
                .addOption("v", false, "volume")
                .addOption("h", false, "help");
    }

    private ResultType parseArgs(String[] args) {
        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine cmd = parser.parse(options, args);
            if (cmd.getOptionValue("h") != null) {
                printHelp();
                return ResultType.SUCCESS;
            } else {
                return aaa(cmd.getOptionValue("u"), cmd.getOptionValue("p"),
                        cmd.getOptionValue("s"), cmd.getOptionValue("r"));
            }
        } catch (ParseException e) {
            printHelp();
            return ResultType.SUCCESS;
        }
    }

    private void printHelp() {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("aaa", options);
    }

    private ResultType aaa(String username, String password, String site, String role) {
        if (!validationService.isUserExist(username)) {
            return ResultType.UNKNOWN_LOGIN;
        } else if (!validationService.isPasswordCorrect(username, password)) {
            return ResultType.INVALID_PASSWORD;
        } else if (!validationService.isAuthorized(username, site, role)) {
            return ResultType.ACCESS_DENIED;
        } else {
            return ResultType.SUCCESS;
        }
    }
}
