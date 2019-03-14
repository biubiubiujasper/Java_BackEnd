package main.controller;

import main.config.Const;
import main.model.Request;
import main.model.User;
import main.service.RequestService;
import main.service.UserService;
import main.util.CommonUtil;
import main.util.TimeConvert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static main.config.Const.REQUEST_STATUS_ASKING;

@RestController
@RequestMapping("friends")
public class FriendsController {

    static final Logger logger = LoggerFactory.getLogger(FriendsController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private RequestService requestService;

    @GetMapping(value = "listFriends")
    public String listFriends(HttpServletRequest request){
        int uid = CommonUtil.loginTable.get(request.getSession().getId());
        User user = userService.getUserByUid(uid);
        String myFriends = user.getFriends();
        if(myFriends!=null){
            String result = myFriends.replaceAll(",","<br>");
            return result;
        }
        return "no friends yet";
    }

    @GetMapping(value = "sendRequest")
    public Map<String,Object> sendRequest(HttpServletRequest request){
        Map<String,Object> result = CommonUtil.getResult();
        int askId = CommonUtil.loginTable.get(request.getSession().getId());
        String answerName = request.getParameter("toName");
        User user = userService.getUser(answerName);
        if(user==null){
            result.put("code","-1");
            result.put("message","no this user");
            return result;
        }

        int answerId = user.getId();
        Request request1 = new Request(askId,answerId, TimeConvert.getTimeNow(),REQUEST_STATUS_ASKING);
        requestService.addRequest(request1);

        return result;
    }

    @GetMapping(value = "getAskingRequest")
    public List<Map<String,Object>> getAskingRequest(HttpServletRequest request){
        int uid = CommonUtil.loginTable.get(request.getSession().getId());
        List<Request> requests = requestService.getRequest(uid);
        List<Map<String,Object>> result = new ArrayList<>();

        for(Request request1:requests){
            Map<String,Object> requestMap = new HashMap<>();
            if(request1.getStatus()== Const.REQUEST_STATUS_ASKING){
                int askId = request1.getAskId();
                User user = userService.getUserByUid(askId);
                requestMap.put("askName",user.getName());
                requestMap.put("askId",askId);
                requestMap.put("requestTime",request1.getRequestTime());
                result.add(requestMap);
            }
        }

        return result;
    }

    @GetMapping(value = "replyRequest")
    public Map<String,Object> replyRequest(HttpServletRequest request){
        Map<String,Object> result = CommonUtil.getResult();
        String reply = request.getParameter("reply");
        int answerId = CommonUtil.loginTable.get(request.getSession().getId());
        int askId = Integer.parseInt(request.getParameter("askId"));
        String requestTime = request.getParameter("requestTime");
        if(reply.equals("yes")){
            Request request1 = new Request(askId,answerId,requestTime,Const.REQUEST_STATUS_OK);
            requestService.updateRequest(request1);
            // 更新ask用户的好友列表
            User answerUser = userService.getUserByUid(answerId);
            User askUser = userService.getUserByUid(askId);
            if(askUser.getFriends()==null){
                askUser.setFriends(answerUser.getName());
            }else{
                askUser.setFriends(askUser.getFriends()+","+answerUser.getName());
            }

            if(answerUser.getFriends()==null){
                answerUser.setFriends(askUser.getName());
            }else{
                answerUser.setFriends(answerUser.getFriends()+","+askUser.getName());
            }

            userService.updateUser(askUser);
            userService.updateUser(answerUser);
        }else if(reply.equals("no")){
            Request request1 = new Request(askId,answerId,requestTime,Const.REQUEST_STATUS_REJECTED);
            requestService.updateRequest(request1);
        }

        return result;
    }

}
