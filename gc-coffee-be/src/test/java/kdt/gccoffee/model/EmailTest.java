package kdt.gccoffee.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmailTest {

    @Test
    public void testInvalidEmail() {
        assertThrows(IllegalArgumentException.class, () -> {
            Email email = new Email("asdf");
        });
    }

    @Test
    public void testValidEmail() {
        String address = "asdf@naver.com";
        Email email = new Email(address);
        assertEquals(email.getAddress(), address);
    }

    @Test
    public void testEqualEmail() {
        String address = "asdf@naver.com";
        Email email1 = new Email(address);
        Email email2 = new Email(address);
        assertEquals(email1, email2);
    }
}