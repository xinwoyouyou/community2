package life.majiang.community.controller;

import life.majiang.community.dto.AccessTokenDTO;
import life.majiang.community.dto.GitHupUser;
import life.majiang.community.pojo.User;
import life.majiang.community.provider.GitHupProvider;
import life.majiang.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * 作者:悠悠我心
 * Authorize:授权
 * callback:回调函数
 */
@Controller
public class AuthorizeController {
    @Autowired
    private GitHupProvider gitHupProvider;
    @Autowired
    private UserService userService;

    @Value("${githup.client_id}")
    private String client_id;
    @Value("${githup.client_secret}")
    private String client_secret;
    @Value("${githup.redirect_uri}")
    private String redirect_uri;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletResponse response
    ) {
        final AccessTokenDTO tokenDTO = new AccessTokenDTO();
        tokenDTO.setClient_id(client_id);
        tokenDTO.setClient_secret(client_secret);
        tokenDTO.setCode(code);
        tokenDTO.setRedirect_uri(redirect_uri);
        tokenDTO.setState(state);

        final String accessToken = gitHupProvider.getAccessToken(tokenDTO);
        final GitHupUser gitHupUser = gitHupProvider.gitUser(accessToken);
        if (gitHupUser != null && gitHupUser.getId() != null) {
            final User user = new User();
            final String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(gitHupUser.getName());
            user.setBio(gitHupUser.getBio());
            user.setAccountId(String.valueOf(gitHupUser.getId()));
            user.setAvatarUrl(gitHupUser.getAvatar_url());

            userService.createOrUpdate(user);
            //登录成功写cookie和session
            final Cookie cookie = new Cookie("token", token);
            response.addCookie(cookie);
        } else {
            //登录失败,重新登录
        }
        return "redirect:/";
    }

    @RequestMapping("/d")
    public String a() {
        return "d";
    }
}
