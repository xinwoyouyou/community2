package life.majiang.community.dto;

import life.majiang.community.pojo.User;
import lombok.Data;

/**
 * 作者:悠悠我心
 */
@Data
public class CommentDTO {
    private Long id;
    private Long parentId;
    private Integer type;
    private Long commentator;
    private Long gmtCreate;
    private Long gmtModified;
    private Long likeCount;
    private String content;
    private User user;
}
