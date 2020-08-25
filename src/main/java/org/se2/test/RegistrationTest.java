package org.se2.test;



import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.se2.hausarbeit.model.dao.UserDao;
import org.se2.hausarbeit.model.entity.User;
import org.se2.hausarbeit.process.exception.DatabaseException;
import org.se2.hausarbeit.process.exception.NoSuchUserOrPasswordException;
import org.se2.hausarbeit.process.exception.UserAlreadyRegisteredException;
import org.se2.hausarbeit.services.util.ROLE;

import static org.junit.Assert.*;

public class RegistrationTest {

    private UserDao dao;

    @BeforeEach
    public void daoSetup() throws DatabaseException {
        this.dao = new UserDao();
    }


    @Test
    public void registrationRoundTripTest() throws DatabaseException, UserAlreadyRegisteredException, NoSuchUserOrPasswordException {
        User user = new User();
        user.setRole(ROLE.BENUTZER);
        user.setPassword("password123");
        user.setLastname("nachname");
        user.setName("vorname");
        user.setEmail("x@example.com");

        assertFalse(dao.checkIfEmailExists(user.getEmail()));
        // register user
        dao.registerUser(user);
        // check user
        User savedUser = dao.getByEmailAndPassword(user.getEmail(), user.getPassword());
        assertEquals(user.getEmail(), savedUser.getEmail());
        assertEquals(user.getPassword(), savedUser.getPassword());
        assertEquals(user.getLastname(), savedUser.getLastname());
        assertEquals(user.getName(), savedUser.getName());
        // delete
        dao.deleteUser(savedUser.getId());
        assertFalse(dao.checkIfEmailExists(user.getEmail()));
    }
}
