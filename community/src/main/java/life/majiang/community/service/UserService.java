package life.majiang.community.service;

import life.majiang.community.mapper.UserMapper;
import life.majiang.community.pojo.User;
import life.majiang.community.pojo.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 作者:悠悠我心
 */
@Service
public class UserService {
    @Autowired(required = false)
    private UserMapper userMapper;

    public int insertSelective(User record) {
        return userMapper.insertSelective(record);
    }

}
