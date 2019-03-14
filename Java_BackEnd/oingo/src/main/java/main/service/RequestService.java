package main.service;

import main.dao.RequestDao;
import main.model.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("RequestService")
public class RequestService {

    @Autowired
    private RequestDao requestDao;

    public int addRequest(Request request){
        return requestDao.addRequest(request);
    }

    public List<Request> getRequest(int answerId){
        return requestDao.listRequest(-1,answerId);
    }

    public int updateRequest(Request request){
        return requestDao.updateRequest(request);
    }
}
