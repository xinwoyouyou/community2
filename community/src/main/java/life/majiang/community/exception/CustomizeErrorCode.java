package life.majiang.community.exception;

/**
 * 作者:悠悠我心
 * Customize:定制
 * Error:错误
 * Code:代码
 */
public enum  CustomizeErrorCode implements ICustomizeErrorCode {

    QUESTION_NOT_FOUND(100,"你找的问题不存在,请换一个...");

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
