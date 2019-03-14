package main.controller;

import main.model.User;
import main.service.UserService;
import main.util.CommonUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@RestController
@RequestMapping("user")
public class LoginController {

    static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private UserService userService;

    @GetMapping(value = "login")
    public Map<String, Object> login(HttpServletRequest request){
        Map<String, Object> result = CommonUtil.getResult();
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        try{
            if(StringUtils.isBlank(name) || StringUtils.isBlank(password)){
                result.put("code","-1");
                result.put("message","please enter all msg");
                return result;
            }

            int i = userService.login(name,password);
            if(i==0){
                HttpSession session = request.getSession();
                int uid = userService.getUser(name).getId();
                CommonUtil.loginTable.put(session.getId(),uid);
                return result;
            }else if(i==1){
                result.put("code","1");
                result.put("message","wrong password");
                return result;
            }else if(i==2){
                result.put("code","2");
                result.put("message","no user");
                return result;
            }

        }catch (Exception e){
            logger.info("login error："+e);
            result.put("code","-1");
            result.put("message","something error");
            return result;
        }
        return result;
    }

    @GetMapping(value = "register")
    public Map<String,Object> register(HttpServletRequest request){
        Map<String, Object> result = CommonUtil.getResult();
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        String email = request.getParameter("email");

        if(StringUtils.isBlank(name) || StringUtils.isBlank(password)|| StringUtils.isBlank(email)){
            result.put("code","-1");
            result.put("message","please enter all msg");
            return result;
        }

        User user = new User(0,name,email,password,null);
        int i = userService.register(user);
        if(i==-1){
            result.put("code","-1");
            result.put("message","name already exist");
            return result;
        }

        return result;
    }
}
