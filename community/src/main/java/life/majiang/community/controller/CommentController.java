package life.majiang.community.controller;

import life.majiang.community.dto.CommentDto;
import life.majiang.community.dto.Msg;
import life.majiang.community.exception.CustomizeErrorCode;
import life.majiang.community.exception.CustomizeException;
import life.majiang.community.mapper.QuestionMapper;
import life.majiang.community.pojo.Comment;
import life.majiang.community.pojo.Question;
import life.majiang.community.pojo.QuestionExample;
import life.majiang.community.pojo.User;
import life.majiang.community.service.CommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.List;

/**
 * 作者:悠悠我心
 * Comment:评论
 * Controller:控制器
 */
@Controller
public class CommentController {
    @Autowired
    private CommentService commentService;


    @ResponseBody
    @PostMapping("/comment")
    public Object post(@RequestBody CommentDto commentDto,
                       HttpServletRequest request
                       ) {
        final User user = (User) request.getSession().getAttribute("user");
        if (user==null){
            throw new CustomizeException(CustomizeErrorCode.NO_LOGIN);
        }

        final Comment comment = new Comment();
        BeanUtils.copyProperties(commentDto, comment);
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(System.currentTimeMillis());
        comment.setLikeCount(1L);
        comment.setCommentator(user.getId());
        commentService.insertComment(comment);
        return Msg.success();
    }
}
