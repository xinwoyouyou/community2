package life.majiang.community.service;


import life.majiang.community.dto.QuestionDTO;
import life.majiang.community.mapper.QuestionMapper;
import life.majiang.community.pojo.Question;
import life.majiang.community.pojo.QuestionExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 作者:悠悠我心
 * Question:问题
 */
@Service
public class QuestionService {
    @Autowired(required = false)
    private QuestionMapper questionMapper;

    public void createOrUpdate(Question question) {
        final int i = questionMapper.insertSelective(question);

    }

    public QuestionDTO getById(Integer id) {
        return null;
    }

    public List<Question> selectByExample(QuestionExample example) {
        return questionMapper.selectByExample(example);
    }

    public List<Question> selectByExampleWithBLOBs(QuestionExample example) {
        return questionMapper.selectByExampleWithBLOBs(example);
    }
}
