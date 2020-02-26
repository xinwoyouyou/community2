package life.majiang.community.dto;

import lombok.Data;

/**
 * 作者:悠悠我心
 */
@Data
public class NotificationDTO {
    private Long id;
    private Long gmtCreate;
    private Integer status;
    private Long notifier;
    private String notifierName;//通知人名字
    private String outerTitle;
    private Long outerid;
    private String typeName;
    private Integer type;
}
