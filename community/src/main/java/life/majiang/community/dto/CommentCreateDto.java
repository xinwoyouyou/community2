package life.majiang.community.dto;

import lombok.Data;

/**
 * 作者:悠悠我心
 */
@Data
public class CommentCreateDto {
    private Long parentId;   //问题(question)id
    private Integer type;
    private String content;  //content:内容
}

