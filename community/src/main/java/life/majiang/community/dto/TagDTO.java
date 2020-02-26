package life.majiang.community.dto;

import lombok.Data;

import java.util.List;

/**
 * 作者:悠悠我心
 * category:种类
 * Name
 */
@Data
public class TagDTO {
    private String categoryName;
    private List<String> tags;
}
