package life.majiang.community.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 作者:悠悠我心
 * AccessToken:访问令牌
 */
@Data
@NoArgsConstructor
@AllArgsConstructor//有参构造
public class AccessTokenDTO {
    private String client_id;
    private String client_secret;
    private String code;
    private String redirect_uri;
    private String state;
}
