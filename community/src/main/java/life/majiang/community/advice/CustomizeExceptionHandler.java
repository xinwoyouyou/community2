package life.majiang.community.advice;


import com.alibaba.fastjson.JSON;
import life.majiang.community.dto.Msg;
import life.majiang.community.exception.CustomizeErrorCode;
import life.majiang.community.exception.CustomizeException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

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
                        HttpServletResponse response,
                        Throwable e,
                        Model model) {
        final String contentType = request.getContentType();
        Msg msg = null;
        if ("application/json".equals(contentType)) {
            //返回json
            if (e instanceof CustomizeException) {
                final Integer key = ((CustomizeException) e).getErrorCode().getCode();
                final String message = ((CustomizeException) e).getErrorCode().getMessage();
                msg = msg.errorResult(key, message);
            } else {
                msg = Msg.fail();
            }

            try {
                response.setContentType("application/json");
                response.setStatus(200);
                response.setCharacterEncoding("UTF-8");
                final PrintWriter writer = response.getWriter();
                writer.write(JSON.toJSONString(msg));
                writer.close();
            } catch (IOException ex) {

            }
            return null;

        } else {
            //错误页面跳转
            if (e instanceof CustomizeException) {
                final String message = ((CustomizeException) e).getErrorCode().getMessage();
                model.addAttribute("message", message);//message:在线留言
            } else {
                final String message = CustomizeErrorCode.SERVER_ERROR.getMessage();
                model.addAttribute("message", message);//message:在线留言
            }
            final ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("error");
            return modelAndView;
        }
    }
}
