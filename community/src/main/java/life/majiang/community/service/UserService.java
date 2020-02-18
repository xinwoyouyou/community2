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


    public void createOrUpdate(User user) {
        final UserExample example = new UserExample();
        example.createCriteria()
                .andAccountIdEqualTo(user.getAccountId());
        final List<User> users = userMapper.selectByExample(example);
        if (users.size() == 0) {
            //插入
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insertSelective(user);
        } else {
            //更新
            final User updateUser= users.get(0);
            updateUser.setGmtModified(System.currentTimeMillis());
            updateUser.setName(user.getName());
            updateUser.setAvatarUrl(user.getAvatarUrl());
            updateUser.setToken(user.getToken());
            updateUser.setBio(user.getBio());

            userMapper.updateByExampleSelective(updateUser, example);
        }
    }
}
