package life.majiang.community.controller;

import life.majiang.community.dto.CommentDTO;
import life.majiang.community.dto.QuestionDTO;
import life.majiang.community.enums.CommentTypeEnums;
import life.majiang.community.service.CommentService;
import life.majiang.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * 作者:悠悠我心
 * Question:问题
 * Controller
 */
@Controller
public class QuestionController {
    @Autowired
    private QuestionService questionService;
    @Autowired
    private CommentService commentService;


    @GetMapping("/question/{id}")
    public String question(@PathVariable("id") long id,
                           Model model
                           ) {
        QuestionDTO questionDTO= questionService.getById(id);
       List<QuestionDTO>  relatedQuestion=questionService.selectRelated(questionDTO);

        List<CommentDTO> commentDTOS= commentService.listByTargetId(id, CommentTypeEnums.QUESTION.getType());


        //累加阅读数
        questionService.intView(id);

        model.addAttribute("question", questionDTO);
        model.addAttribute("comments", commentDTOS);
        model.addAttribute("relatedQuestion", relatedQuestion);

        return "question";
    }

}
