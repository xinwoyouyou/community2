package life.majiang.community.service;


import life.majiang.community.dto.QuestionDTO;
import life.majiang.community.exception.CustomizeErrorCode;
import life.majiang.community.exception.CustomizeException;
import life.majiang.community.mapper.QuestionExMapper;
import life.majiang.community.mapper.QuestionMapper;
import life.majiang.community.mapper.UserMapper;
import life.majiang.community.pojo.Question;
import life.majiang.community.pojo.QuestionExample;
import life.majiang.community.pojo.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 作者:悠悠我心
 * Question:问题
 */
@Service
public class QuestionService {
    @Autowired(required = false)
    private QuestionMapper questionMapper;
    @Autowired(required = false)
    private UserMapper userMapper;
    @Autowired(required = false)
    private QuestionExMapper questionExMapper;

    public void createOrUpdate(Question question) {
        if (question.getId() == null) {
            //创建
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            questionMapper.insertSelective(question);
        } else {
            //更新
            question.setGmtModified(System.currentTimeMillis());//更新时间
            final QuestionExample example = new QuestionExample();
            example.createCriteria()
                    .andIdEqualTo(question.getId());
            final int updateI = questionMapper.updateByExampleSelective(question, example);
            if (updateI != 1) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
        }
    }


    public QuestionDTO getById(Long id) {
        final QuestionDTO questionDTO = new QuestionDTO();
        final Question question = questionMapper.selectByPrimaryKey(id);
        if (question == null) {
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        BeanUtils.copyProperties(question, questionDTO);
        final User user = userMapper.selectByPrimaryKey(question.getCreator());
        questionDTO.setUser(user);
        return questionDTO;
    }

    public List<Question> selectByExample(QuestionExample example) {
        return questionMapper.selectByExample(example);
    }

    public List<Question> selectByExampleWithBLOBs(QuestionExample example) {
        return questionMapper.selectByExampleWithBLOBs(example);
    }

    public void intView(Long id) {
        final Question question = new Question();
        question.setId(id);
        question.setViewCount(1);
        questionExMapper.intView(question);

    }

    public List<QuestionDTO> selectRelated(QuestionDTO questiondto) {
        if (StringUtils.isBlank(questiondto.getTag())) {
            return new ArrayList<>();
        }
        final String[] tags = StringUtils.split(questiondto.getTag(), ',');
        final String replaceTag = StringUtils.replace(questiondto.getTag(), ",", "|");
        final Question question = new Question();
        question.setId(questiondto.getId());
        question.setTag(replaceTag);

        final List<Question> questions = questionExMapper.selectRelated(question);
        final List<QuestionDTO> questionDTOList = questions.stream().map(q -> {
            final QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(q, questionDTO);
            return questionDTO;
        }).collect(Collectors.toList());

        return questionDTOList;
    }
}
