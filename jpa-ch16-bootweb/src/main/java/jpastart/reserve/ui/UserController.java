package jpastart.reserve.ui;

import jpastart.reserve.application.GetUserListService;
import jpastart.reserve.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class UserController {
    private GetUserListService getUserListService;

    @RequestMapping("/users")
    public String getAllUsers(ModelMap modelMap) {
        List<User> users = getUserListService.getAllUsers();
        modelMap.addAttribute("users", users);
        return "users";
    }

    @Autowired
    public void setGetUserListService(GetUserListService getUserListService) {
        this.getUserListService = getUserListService;
    }
}
