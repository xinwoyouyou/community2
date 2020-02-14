package life.majiang.community.dto;

import life.majiang.community.pojo.User;
import lombok.Data;

/**
 * 作者:悠悠我心
 */
@Data
public class QuestionDTO {

    private Integer id;

    private String title;

    private Long gmtCreate;

    private Long gmtModified;

    private Integer creator;

    private Integer commentCount;

    private Integer viewCount;

    private Integer likeCount;

    private String tag;

    private String description;

    private User user;
}
