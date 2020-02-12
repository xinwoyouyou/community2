package life.majiang.community.provider;

import com.alibaba.fastjson.JSON;
import life.majiang.community.dto.AccessTokenDTO;
import life.majiang.community.dto.GitHupUser;
import okhttp3.*;
import org.springframework.stereotype.Component;


import java.io.IOException;

/**
 * 作者:悠悠我心
 */
@Component
public class GitHupProvider {
    public String getAccessToken(AccessTokenDTO accessTokenDTO) {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(mediaType,JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            final String string = response.body().string();
            final String[] split = string.split("&");
            final String token = split[0].split("=")[1];
            return  token;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public GitHupUser gitUser(String accessToken) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token=" + accessToken)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            final GitHupUser gitHupUser = JSON.parseObject(string, GitHupUser.class);
            return gitHupUser;
        } catch (IOException e) {
        }
        return null;

    }
}
