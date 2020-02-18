package life.majiang.community.dto;

import lombok.Data;

/**
 * 作者:悠悠我心
 */
@Data
public class CommentDto {
    private Long parentId;
    private Integer type;
    private String content;  //content:内容
}

