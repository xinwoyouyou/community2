package life.majiang.community.controller;

import life.majiang.community.dto.CommentCreateDto;
import life.majiang.community.dto.CommentDTO;
import life.majiang.community.dto.Msg;
import life.majiang.community.enums.CommentTypeEnums;
import life.majiang.community.exception.CustomizeErrorCode;
import life.majiang.community.exception.CustomizeException;
import life.majiang.community.pojo.Comment;
import life.majiang.community.pojo.User;
import life.majiang.community.service.CommentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
    public Object post(@RequestBody CommentCreateDto commentCreateDto,
                       HttpServletRequest request
    ) {
        final User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            throw new CustomizeException(CustomizeErrorCode.NO_LOGIN);
        }
        if (commentCreateDto == null || StringUtils.isBlank(commentCreateDto.getContent())) {
            throw new CustomizeException(CustomizeErrorCode.CONTENT_ID_EMPTY);
        }
        final Comment comment = new Comment();
        BeanUtils.copyProperties(commentCreateDto, comment);
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(System.currentTimeMillis());
        comment.setLikeCount(1L);
        comment.setCommentator(user.getId());
        commentService.insertComment(comment);
        return Msg.success();
    }


    @ResponseBody
    @GetMapping("/comment/{id}")
    public Msg comments(@PathVariable("id") Long id
    ) {
        final Integer type = CommentTypeEnums.COMMENT.getType();
        final List<CommentDTO> commentDTOS = commentService.listByTargetId(id, type);

        return Msg.success().add("commentDTOS", commentDTOS);
    }

}
