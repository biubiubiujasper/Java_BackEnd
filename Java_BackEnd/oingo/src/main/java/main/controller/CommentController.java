package main.controller;

import main.model.Comment;
import main.service.CommentService;
import main.util.CommonUtil;
import main.util.TimeConvert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("comment")
public class CommentController {

    static final Logger logger = LoggerFactory.getLogger(CommentController.class);

    @Autowired
    private CommentService commentService;

    @GetMapping(value = "addComment")
    public Map<String,Object> addComment(HttpServletRequest request){
        Map<String,Object> result = CommonUtil.getResult();
        int nid = Integer.parseInt(request.getParameter("nid"));
        String content = request.getParameter("content");

        Comment comment = new Comment(0,nid, TimeConvert.getTimeNow(),content);
        commentService.addComment(comment);
        return result;
    }

    @GetMapping(value = "deleteComment")
    public Map<String,Object> deleteComment(HttpServletRequest request){
        Map<String,Object> result = CommonUtil.getResult();
        int cid = Integer.parseInt(request.getParameter("cid"));
        commentService.deleteComment(cid);
        return result;
    }

    @GetMapping(value = "listComment")
    public List<Comment> listComment(HttpServletRequest request){
        int nid = Integer.parseInt(request.getParameter("nid"));
        return commentService.getComment(nid);
    }

}
