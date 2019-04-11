package application.controller.api;

import application.data.model.User;
import application.data.service.UserService;
import application.model.BaseApiResult;
import application.model.DataApiResult;
import application.model.UserLogin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController("/user")
public class UserApiController {
    private static final Logger logger = LogManager.getLogger(UserApiController.class);

    @Autowired
    UserService userService;

    @RequestMapping(value = "/sendemail")
    public String sendEmail() {
        return "Email sent successfully";
    }

    @GetMapping("/getAllUser")
    public BaseApiResult getAllUser() {
        DataApiResult result = new DataApiResult();

        try {
            List<User> users = userService.getAllUser();
            result.setData(users);
            result.setMessage("get users success");
            result.setSuccess(true);
        } catch (Exception e) {
            result.setMessage("get users fail");
            result.setSuccess(false);
        }
        return result;
    }


}
