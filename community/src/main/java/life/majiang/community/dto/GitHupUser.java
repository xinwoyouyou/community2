package life.majiang.community.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 作者:悠悠我心
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GitHupUser {
    private String name;
    private Long id;
    private String bio;
    private String avatar_url;
}
