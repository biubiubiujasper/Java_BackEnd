package main.service;


import main.dao.UserDao;
import main.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("UserService")
public class UserService {

    static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserDao userDao;

    public List<User> getUserList(){
        return userDao.listUser(null);
    }

    public User getUser(String name){
        if(userDao.listUser(name).size()>0){
            return userDao.listUser(name).get(0);
        }else{
            return null;
        }

    }

    public User getUserByUid(int uid){
        return userDao.listUser(uid);
    }

    public int login(String name,String password){
        List<User> users = userDao.listUser(name);
        if(users.size()==1){
            if(users.get(0).getPassword().equals(password)){
                return 0;// success
            }else{
                return 1;// wrong pwd
            }
        }else{
            return 2;// no user
        }
    }

    public int register(User user){
        List<User> users = userDao.listUser(user.getName());
        if(users.size()>=1){
            return -1;// already exist
        }else{
            return userDao.addUser(user);
        }
    }

    public int updateUser(User user){
        return userDao.updateUser(user);
    }


}
