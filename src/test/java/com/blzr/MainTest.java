package com.blzr;

import org.junit.Before;
import org.junit.Test;

import static com.blzr.ResultType.*;
import static org.junit.Assert.assertEquals;

public class MainTest {
    Main main;

    @Before
    public void init() {
        main = new Main();
    }

    /**
     * C1.1
     */
    @Test
    public void empty() {
        assertEquals(SUCCESS, main.connect(new String[]{
                ""
        }));
    }

    /**
     * C1.2
     */
    @Test
    public void help() {
        assertEquals(SUCCESS, main.connect(new String[]{
                "-h"
        }));
    }

    /**
     * C2.1
     */
    @Test
    public void unknownLogin() {
        assertEquals(UNKNOWN_LOGIN, main.connect(new String[]{
                "-login", "XXX", "-pass", "XXX"
        }));
    }

    /**
     * C2.2
     */
    @Test
    public void invalidPassword() {
        assertEquals(INVALID_PASSWORD, main.connect(new String[]{
                "-login", "jdoe", "-pass", "XXX"
        }));
    }

    /**
     * C2.3
     */
    @Test
    public void successfulAuthentication() {
        assertEquals(SUCCESS, main.connect(new String[]{
                "-login", "jdoe", "-pass", "sup3rpaZZ"
        }));
    }

    /**
     * C3.1
     */
    @Test
    public void athorize() {
        assertEquals(SUCCESS, main.connect(new String[]{
                "-login", "jdoe", "-pass", "sup3rpaZZ",
                "-role", "READ", "-res", "a"
        }));
    }

    /**
     * C3.2
     */
    @Test
    public void authorizeNested() {
        assertEquals(SUCCESS, main.connect(new String[]{
                "-login", "jdoe", "-pass", "sup3rpaZZ",
                "-role", "READ", "-res", "a.b"
        }));
    }

    /**
     * C3.3
     */
    @Test
    public void unknownRole() {
        assertEquals(UNKNOWN_ROLE, main.connect(new String[]{
                "-login", "jdoe", "-pass", "sup3rpaZZ",
                "-role", "XXX", "-res", "a.b"
        }));
    }

    /**
     * C3.4
     */
    @Test
    public void incorrectResource() {
        assertEquals(ACCESS_DENIED, main.connect(new String[]{
                "-login", "jdoe", "-pass", "sup3rpaZZ",
                "-role", "READ", "-res", "XXX"
        }));
    }

    /**
     * C3.5
     */
    @Test
    public void accessDenied() {
        assertEquals(ACCESS_DENIED, main.connect(new String[]{
                "-login", "jdoe", "-pass", "sup3rpaZZ",
                "-role", "WRITE", "-res", "a"
        }));
    }

    /**
     * C3.6
     */
    @Test
    public void accessDeniedNested() {
        assertEquals(ACCESS_DENIED, main.connect(new String[]{
                "-login", "jdoe", "-pass", "sup3rpaZZ",
                "-role", "WRITE", "-res", "a.bc"
        }));
    }

    /**
     * C4.1
     */
    @Test
    public void accounting() {
        assertEquals(SUCCESS, main.connect(new String[]{
                "-login", "jdoe", "-pass", "sup3rpaZZ",
                "-role", "READ", "-res", "a",
                "-ds", "2015-05-01", "-de", "2015-05-02", "-vol", "100"
        }));
    }

    /**
     * C4.2
     */
    @Test
    public void invalidDate() {
        assertEquals(INVALID_ACTIVITY, main.connect(new String[]{
                "-login", "jdoe", "-pass", "sup3rpaZZ",
                "-role", "READ", "-res", "a",
                "-ds", "XXX", "-de", "XXX", "-vol", "XXX"
        }));
    }

    /**
     * C4.3
     */
    @Test
    public void invalidVolume() {
        assertEquals(INVALID_ACTIVITY, main.connect(new String[]{
                "-login", "jdoe", "-pass", "sup3rpaZZ",
                "-role", "READ", "-res", "a",
                "-ds", "2015-05-01", "-de", "2015-05-02", "-vol", "XXX"
        }));
    }
}
