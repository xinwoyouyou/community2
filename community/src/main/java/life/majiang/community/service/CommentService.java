package life.majiang.community.service;

import life.majiang.community.enums.CommentTypeEnums;
import life.majiang.community.exception.CustomizeErrorCode;
import life.majiang.community.exception.CustomizeException;
import life.majiang.community.mapper.CommentMapper;
import life.majiang.community.mapper.QuestionExMapper;
import life.majiang.community.mapper.QuestionMapper;
import life.majiang.community.pojo.Comment;
import life.majiang.community.pojo.CommentExample;
import life.majiang.community.pojo.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 作者:悠悠我心
 */
@Service
public class CommentService {
    @Autowired(required = false)
    private CommentMapper commentMapper;
    @Autowired(required = false)
    private QuestionMapper questionMapper;
    @Autowired(required = false)
    private QuestionExMapper questionExMapper;

    @Transactional
    public int insertComment(Comment comment) {
        if (comment.getParentId() == null || comment.getParentId() == 0) {
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }
        if (comment.getType() == null || !CommentTypeEnums.isExist(comment.getType())) {
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
        }
        final Integer type = CommentTypeEnums.COMMENT.getType();
        if (comment.getType() == type) {
            //回复评论
            final CommentExample example = new CommentExample();
            example.createCriteria()
                    .andParentIdEqualTo(comment.getParentId());
            final List<Comment> comments = commentMapper.selectByExample(example);
            if (comments.size() == 0) {
                final CustomizeErrorCode commentNotFount = CustomizeErrorCode.COMMENT_NOT_FOUNT;
                throw new CustomizeException(commentNotFount);
            }
        } else {
            //回复问题
            final Question question = questionMapper.selectByPrimaryKey(comment.getParentId());
            if (question == null) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            question.setCommentCount(1);
            questionExMapper.commentCount(question);
        }
        return commentMapper.insert(comment);
    }
}
