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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 作者:悠悠我心
 * Profile:配置文件    我的问题页面
 */
@Controller
public class ProfileController {
    @Autowired
    private QuestionService questionService;
    @Autowired(required = false)
    private UserMapper userMapper;

    @GetMapping("/profile/{action}")
    public String profile(@PathVariable("action") String action,
                          Model model,
                          HttpServletRequest request,
                          @RequestParam(name = "page", defaultValue = "1") Integer page, //页面
                          @RequestParam(name = "size", defaultValue = "3") Integer size  //大小
    ) {
        final User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return "redirect:/";
        }

        if (true) { //section:部分
            model.addAttribute("section", "question");
            model.addAttribute("sectionName", "我的提问");
        }
        if ("replies".equals(action)) {   //replies:回复
            model.addAttribute("section", "replies");
            model.addAttribute("sectionName", "最新回复");
        }

        //设置分页规则
        PageHelper.startPage(page, size);
        //取数据，插件会自动按照规则分页显示数据
        final QuestionExample example = new QuestionExample();
        final Integer id = user.getId();
        example.createCriteria()
                .andCreatorEqualTo(user.getId());
        final List<Question> questionList = questionService.selectByExampleWithBLOBs(example);
        final List<QuestionDTO> questionDTOList = new ArrayList<>();
        for (Question question : questionList) {
            final QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        final PageInfo<Object> pageInfo = new PageInfo(questionList);

        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("pagination", questionDTOList);
        return "profile";
    }
}
