package main.service;

import main.dao.CommentDao;
import main.model.Comment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("CommentService")
public class CommentService {

    static final Logger logger = LoggerFactory.getLogger(CommentService.class);

    @Autowired
    private CommentDao commentDao;

    public int addComment(Comment comment){
        return commentDao.addComment(comment);
    }

    public int deleteComment(int cid){
        return commentDao.deleteComment(cid);
    }

    public List<Comment> getComment(int nid){
        return commentDao.listComment(nid);
    }
}
