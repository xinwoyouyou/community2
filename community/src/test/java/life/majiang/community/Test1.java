package life.majiang.community;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import life.majiang.community.dto.Msg;
import life.majiang.community.dto.QuestionDTO;
import life.majiang.community.mapper.QuestionMapper;
import life.majiang.community.mapper.UserMapper;
import life.majiang.community.pojo.Question;
import life.majiang.community.pojo.QuestionExample;
import life.majiang.community.pojo.User;
import life.majiang.community.pojo.UserExample;
import life.majiang.community.service.QuestionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class Test1 {
    @Autowired(required = false)
    private UserMapper userMapper;
    @Autowired
    private QuestionService questionService;
    @Autowired(required = false)
    private QuestionMapper questionMapper;

    @Test
    void test1() {
        PageHelper.startPage(1, 1);
        final List<Question> questionList = questionService.selectByExample(null);

        final List<QuestionDTO> questionDTOList = new ArrayList<>();
        for (Question question : questionList) {
            final User user = userMapper.selectByPrimaryKey(question.getCreator());
            final QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }

        final PageInfo<Object> pageInfo = new PageInfo(questionList);
        System.out.println("总页数" + pageInfo.getPages());
        System.out.println("当前页" + pageInfo.getPageNum());
        System.out.println(pageInfo);
        Msg.success().add("pageInfo", pageInfo);


    }

    @Test
    void test2() {
        String token = "fea6e95c-78ed-46c2-b14e-d54c27124215";
        final UserExample example = new UserExample();
        example.createCriteria().andTokenEqualTo(token);
        final List<User> users = userMapper.selectByExample(example);
        System.out.println(users.get(0));
        System.out.println("------------>" + users.get(1));
    }


}


