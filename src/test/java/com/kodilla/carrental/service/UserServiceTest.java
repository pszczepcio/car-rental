package com.kodilla.carrental.service;


import com.kodilla.carrental.domain.User;
import com.kodilla.carrental.exception.UserNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.List;
import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService service;

    @Test
    public void saveUser() throws UserNotFoundException {
        //Given
        User user = new User(
                "name",
                "surname",
                123456789,
                "email@email.com",
                "password"
        );

        //When
        service.saveUser(user);
        User userAfterSave = service.getUser(user.getId());

        //Then
        assertNotNull(user.getId());
        assertEquals("name", userAfterSave.getName());
        assertEquals("surname", userAfterSave.getSurname());
        assertEquals(123456789, userAfterSave.getPhone());
        assertEquals("email@email.com", userAfterSave.getEmail());
        assertEquals("password", userAfterSave.getPassword());
        assertEquals(false, userAfterSave.isLoginStatus());

        //Cleanup
        service.deleteUser(user.getId());
    }

    @Test
    public void getUsersList() {
        //Given
        User marcin = new User(
                "Marcin",
                "Kowalski",
                123456789,
                "marcin@email.com",
                "password"
        );

        User romek = new User(
                "Romek",
                "Czerwony",
                987654321,
                "romek@email.com",
                "password1"
        );

        //When
        service.saveUser(marcin);
        service.saveUser(romek);
        List<User> userList = service.getUsersList();

        //Then
        assertNotNull(marcin.getId());
        assertNotNull(romek.getId());
        assertEquals(2, userList.size());

        //Cleanup
        service.deleteUser(romek.getId());
        service.deleteUser(marcin.getId());
    }
}