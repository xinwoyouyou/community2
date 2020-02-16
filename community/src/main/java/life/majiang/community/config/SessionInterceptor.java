package life.majiang.community.config;

import life.majiang.community.mapper.UserMapper;
import life.majiang.community.pojo.User;
import life.majiang.community.pojo.UserExample;
import life.majiang.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 作者:悠悠我心
 * Handler:处理器
 * Interceptor:拦截器
 */
@Component
public class SessionInterceptor implements HandlerInterceptor {
    @Autowired(required = false)
    private UserMapper userMapper;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        final Cookie[] cookies = request.getCookies();
        if (cookies != null)
            if (cookies != null && cookies.length != 0)
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("token")) {
                        final String token = cookie.getValue();
                        final UserExample example = new UserExample();
                        example.createCriteria().andTokenEqualTo(token);
                        final List<User> users = userMapper.selectByExample(example);
                        if (users.size() != 0) {
                            request.getSession().setAttribute("user", users.get(0));
                        }
                        break;
                    }
                }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
