package life.majiang.community.controller;

import life.majiang.community.dto.QuestionDTO;
import life.majiang.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 作者:悠悠我心
 * Question:问题
 * Controller
 */
@Controller
public class QuestionController {
    @Autowired
    private QuestionService questionService;


    @GetMapping("/question/{id}")
    public String question(@PathVariable("id") long id,
                           Model model
                           ) {
        QuestionDTO questionDTO= questionService.getById(id);
        //累加阅读数
        questionService.intView(id);

        model.addAttribute("question", questionDTO);
        return "question";
    }

}
