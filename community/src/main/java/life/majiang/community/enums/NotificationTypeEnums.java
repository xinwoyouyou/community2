package life.majiang.community.enums;

/**
 * 作者:悠悠我心
 * Notification:通知
 * Enums:枚举
 * status:状态
 * reply:回复
 * question
 */

public enum NotificationTypeEnums {
    REPLY_QUESTION(1, "回复了问题"),
    REPLY_COMMENT(2, "回复了评论"),
    ;
    private Integer type;
    private String name;

    public Integer getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    NotificationTypeEnums(Integer type, String name) {
        this.type = type;
        this.name = name;
    }
    public static String replyType(Integer type){
        final Integer questionType = NotificationTypeEnums.REPLY_QUESTION.type;
        if (type== questionType){
            return NotificationTypeEnums.REPLY_QUESTION.name;
        }else {
            return NotificationTypeEnums.REPLY_COMMENT.name;
        }
    }
}
