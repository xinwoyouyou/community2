package life.majiang.community.mapper;

import life.majiang.community.pojo.Question;

import java.util.LinkedList;
import java.util.List;

/**
 * 作者:悠悠我心
 */
public interface QuestionExMapper {
    int intView(Question question);

    int commentCount(Question question);

    List<Question> selectRelated(Question question);
}
