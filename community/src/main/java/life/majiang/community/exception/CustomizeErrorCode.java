package life.majiang.community.exception;

/**
 * 作者:悠悠我心
 * Customize:定制
 * Error:错误
 * Code:代码
 */
public enum  CustomizeErrorCode implements ICustomizeErrorCode {
    QUESTION_NOT_FOUND(100,"你找的问题不存在,请换一个..."),
    TARGET_PARAM_NOT_FOUND(101,"未选中任何问题进行回复"),
    NO_LOGIN(102,"未登录不能进行评论,请先登录!"),
    SERVER_ERROR(500,"服务器冒烟了..."),
    TYPE_PARAM_WRONG(103,"评论类型错误或不存在"),//type_param(参数)_wrong(错误的)
    COMMENT_NOT_FOUNT(104,"回复的评论不存在."),
    CONTENT_ID_EMPTY(105,"评论的内容不能为空"),

    ;

    private Integer code;
    private String message;

    CustomizeErrorCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
