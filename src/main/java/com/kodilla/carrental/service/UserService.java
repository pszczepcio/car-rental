package com.kodilla.carrental.service;

import com.kodilla.carrental.dao.UserDao;
import com.kodilla.carrental.domain.Mail;
import com.kodilla.carrental.domain.User;
import com.kodilla.carrental.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class UserService {
    private static final String SUBJECT = "New account in Car Rental";

    @Autowired
    private UserDao userDao;

    @Autowired
    private SimpleEmailService emailService;

    public User saveUser(final User user) {
        User newUser = userDao.save(user);
        if (newUser.getId() != null) {
            emailService.send(new Mail(
                    newUser.getEmail(),
                    SUBJECT,
                    "Hello " + newUser.getName() + "\n" + "You created account in Car Rental.\n" +
                            "Best regards,\n Car Rentals team"
            ));
            return user;
        }
        return new User();
    }

    public List<User> getUsersList() {
        return userDao.findAll();
    }

    public User getUser(final Long id) throws UserNotFoundException {
        return userDao.findById(id).orElseThrow(UserNotFoundException::new);
    }

    public void deleteUser(final Long id) {
        userDao.deleteById(id);
    }
}
