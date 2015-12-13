package com.blzr.service;

import com.blzr.domain.Activity;
import com.blzr.domain.Authority;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class AccountingService {
    private final ConnectionService connectionService;

    public AccountingService(ConnectionService connectionService) {
        this.connectionService = connectionService;
    }

    public Long addActivity(Authority authority, String dateStart, String dateEnd, String volume) throws ParseException, NumberFormatException {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        connectionService.addActivity(new Activity(authority,
                format.parse(dateStart), format.parse(dateEnd), Integer.valueOf(volume)));
        return connectionService.countActivity();
    }
}
