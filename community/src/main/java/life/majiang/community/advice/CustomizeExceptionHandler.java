package life.majiang.community.advice;


import life.majiang.community.exception.CustomizeErrorCode;
import life.majiang.community.exception.CustomizeException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * 作者:悠悠我心
 * Customize:定制
 * Exception:异常
 * Handler:处理器
 * Advice:建议,忠告
 */
@ControllerAdvice
public class CustomizeExceptionHandler {
    @ExceptionHandler(Exception.class)
    ModelAndView handle(HttpServletRequest request,
                        Throwable e,
                        Model model) {
        if (e instanceof CustomizeException){
            model.addAttribute("message", ((CustomizeException) e).getErrorCode().getMessage());//message:在线留言
        } else {
            model.addAttribute("message", "服务端异常");//message:在线留言
        }

        final ModelAndView modelAndView = new ModelAndView();
         modelAndView.setViewName("error");
        return modelAndView;
    }
}
