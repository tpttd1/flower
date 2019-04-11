package application.data.service;

import application.data.model.User;
import application.data.repository.UserRepository;
import application.model.UserLogin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private static final Logger logger = LogManager.getLogger(UserService.class);

    @Autowired
    UserRepository userRepository;

    public List<User> getAllUser() {
        try {
            return userRepository.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("invalid user");
            return null;
        }
    }

    public Boolean login(UserLogin userLogin) {
        try {
            userRepository.login(userLogin.getUsername(), userLogin.getPassword());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("login fail");
            return false;
        }
    }
}
