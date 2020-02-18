package life.majiang.community.enums;



/**
 * 作者:悠悠我心
 */
public enum  CommentTypeEnums {
    QUESTION(1),
    COMMENT(2);

    private Integer type;


    public static boolean isExist(Integer type) {//isExist(是存在)
        final CommentTypeEnums[] values = CommentTypeEnums.values();
        for (CommentTypeEnums commentTypeEnums : values) {
            if (commentTypeEnums.getType()==type){
                return true;
            }
        }
        return false;
    }

    public Integer getType() {
        return type;
    }

    CommentTypeEnums(Integer type) {
        this.type = type;
    }


}
