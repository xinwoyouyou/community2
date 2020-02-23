package life.majiang.community.service;

import life.majiang.community.dto.CommentDTO;
import life.majiang.community.enums.CommentTypeEnums;
import life.majiang.community.exception.CustomizeErrorCode;
import life.majiang.community.exception.CustomizeException;
import life.majiang.community.mapper.CommentMapper;
import life.majiang.community.mapper.QuestionExMapper;
import life.majiang.community.mapper.QuestionMapper;
import life.majiang.community.mapper.UserMapper;
import life.majiang.community.pojo.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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
    @Autowired(required = false)
    private UserMapper userMapper;

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
                    .andIdEqualTo(comment.getParentId());
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

    public List<CommentDTO> listByTargetId(long id, Integer type) {
        final CommentExample example = new CommentExample();
        example.createCriteria()
                .andParentIdEqualTo(id)
                .andTypeEqualTo(type);
        example.setOrderByClause("gmt_create desc");
        final List<Comment> comments = commentMapper.selectByExample(example);
        if (comments.size() == 0) {
            return new ArrayList<>();
        }
        //获取去重的评论人
        final Set<Long> collect = comments.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());
        final List<Long> userIds = new ArrayList<>();
        userIds.addAll(collect);

        //获取评论人信息并转换为Map
        final UserExample userExample = new UserExample();
        example.createCriteria()
                .andIdIn(userIds);
        final List<User> users = userMapper.selectByExample(userExample);
        final Map<Long, User> userMap = users.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));

        //转换comment为commentDTO
        final List<CommentDTO> commentDTOList = comments.stream().map(comment -> {
            final CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment, commentDTO);
            commentDTO.setUser(userMap.get(comment.getCommentator()));
            return commentDTO;
        }).collect(Collectors.toList());

        return commentDTOList;
    }
}
