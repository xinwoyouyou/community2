package life.majiang.community.enums;

/**
 * 作者:悠悠我心
 * unread:未读
 * read:阅读
 */
public enum NotificationStatusEnums {
    UNREAD(0), READ(1);
    private Integer status;

    public Integer getStatus() {
        return status;
    }

    NotificationStatusEnums(Integer status) {
        this.status = status;
    }
}
