package life.majiang.community.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import life.majiang.community.dto.QuestionDTO;
import life.majiang.community.mapper.UserMapper;
import life.majiang.community.pojo.Question;
import life.majiang.community.pojo.QuestionExample;
import life.majiang.community.pojo.User;
import life.majiang.community.service.QuestionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 作者:悠悠我心
 * create:创建
 * Criteria:条件
 * and:并且
 * Account:账户
 * Id
 * EqualTo:相同
 */
@Controller
public class IndexController {
    @Autowired
    private QuestionService questionService;
    @Autowired(required = false)
    private UserMapper userMapper;

    @GetMapping("/")
    public String index(HttpServletRequest request,
                        Model model,
                        @RequestParam(name = "page", defaultValue = "1") Integer page, //页面
                        @RequestParam(name = "size", defaultValue = "4") Integer size  //大小
    ) {
        //设置分页规则
        PageHelper.startPage(page, size);
        //取数据，插件会自动按照规则分页显示数据
        final QuestionExample example = new QuestionExample();
        example.setOrderByClause("gmt_create desc");
        final List<Question> questionList = questionService.selectByExampleWithBLOBs(example);
        final List<QuestionDTO> questionDTOList = new ArrayList<>();

        if (questionList.size() != 0) {
            for (Question question : questionList) {
                final User user = userMapper.selectByPrimaryKey(question.getCreator());
                final QuestionDTO questionDTO = new QuestionDTO();
                BeanUtils.copyProperties(question, questionDTO);
                questionDTO.setUser(user);
                questionDTOList.add(questionDTO);
                final PageInfo<Object> pageInfo = new PageInfo(questionList);


                model.addAttribute("pageInfo", pageInfo);
                model.addAttribute("pagination", questionDTOList);
            }
        }
        return "index";
    }

    @GetMapping("/logout")
    public String logout(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        request.getSession().removeAttribute("user");
        final Cookie cookie = new Cookie("token", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";
    }
}
