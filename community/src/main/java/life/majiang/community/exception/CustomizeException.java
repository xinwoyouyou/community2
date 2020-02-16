package life.majiang.community.exception;

/**
 * 作者:悠悠我心
 * Customize:定制
 * Exception:异常
 */
public class CustomizeException extends RuntimeException {
    private CustomizeErrorCode errorCode;

    public CustomizeException(CustomizeErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public CustomizeErrorCode getErrorCode() {
        return errorCode;
    }

    //用来输出异常信息和状态码
    public void printException(CustomizeException e) {
        CustomizeErrorCode errorCode = e.getErrorCode();
        System.out.println("异常代码:"+errorCode.getCode()+"异常信息:"+errorCode.getMessage());
    }
}
