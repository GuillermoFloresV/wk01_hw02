package com.example.wk01_hw02;

import org.junit.Test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class LoginTest {
    @Test
    public void incorrectPassword() {
        assertEquals(false, LoginActivity.validPassword("adm1n","admin"));
    }
    @Test
    public void correctPassword() {
        assertEquals(true, LoginActivity.validPassword("admin","admin"));
    }
    @Test
    public void validateNewUser() {
        List<User> users = new ArrayList<>();
        User user = new User(1, "John", "JDoe", "jdoe@google.com");
        users.add(user);
        assertEquals(true, LoginActivity.validateUser(users,"JDoe"));
    }
    @Test
    public void failedValidationOfNewUser() {
        List<User> users = new ArrayList<>();
        User user = new User(1, "John", "JDoe", "jdoe@google.com");
        users.add(user);
        assertEquals(false, LoginActivity.validateUser(users,"JD03"));
    }
}