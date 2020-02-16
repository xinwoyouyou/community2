package life.majiang.community.exception;

/**
 * 作者:悠悠我心
 * I:我
 * Customize:定制
 * ErrorCode:错误码
 */
public interface ICustomizeErrorCode {
    /*
     * 获取状态码
     * @return 状态码
     */
    Integer getCode();

    /*
      获取提示信息
      @return 提示信息
      */
    String getMessage();
}


