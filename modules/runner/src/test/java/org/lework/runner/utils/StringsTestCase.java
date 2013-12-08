package org.lework.runner.utils;


import org.junit.Test;

import static org.junit.Assert.*;

/**
 * {@link Strings} test case.
 */
public class StringsTestCase {

    /**
     * Tests method {@link Strings#isEmail(String)}.
     */
    @Test
    public void isEmail() {
        assertTrue(Strings.isEmail("skygongle@gmail.com"));
        assertTrue(Strings.isEmail("gongle@outlook.com"));

        assertFalse(Strings.isEmail(null));
        assertFalse(Strings.isEmail(""));
        assertFalse(Strings.isEmail("test@a.b"));
    }
}
