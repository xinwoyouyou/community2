package life.majiang.community.service;

import life.majiang.community.dto.NotificationDTO;
import life.majiang.community.enums.NotificationStatusEnums;
import life.majiang.community.enums.NotificationTypeEnums;
import life.majiang.community.exception.CustomizeErrorCode;
import life.majiang.community.exception.CustomizeException;
import life.majiang.community.mapper.NotificationMapper;
import life.majiang.community.pojo.Notification;
import life.majiang.community.pojo.NotificationExample;
import life.majiang.community.pojo.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 作者:悠悠我心
 * Notification:通知
 * Service:服务
 */
@Service
public class NotificationService {
    @Autowired(required = false)
    private NotificationMapper notificationMapper;


    public List<NotificationDTO> queryAllReplies(Long id) {
        final NotificationExample example = new NotificationExample();
        example.createCriteria()
                .andReceiverEqualTo(id)
                .andStatusEqualTo(NotificationStatusEnums.UNREAD.getStatus());
        final List<Notification> notifications = notificationMapper.selectByExample(example);

        final List<NotificationDTO> notificationDTOS = new ArrayList<>();
        for (Notification notification : notifications) {
            final NotificationDTO notificationDTO = new NotificationDTO();

            BeanUtils.copyProperties(notification, notificationDTO);
            final String typeName = NotificationTypeEnums.replyType(notification.getType());
            notificationDTO.setTypeName(typeName);
            notificationDTOS.add(notificationDTO);
        }


        return notificationDTOS;
    }

    public NotificationDTO read(Long id, User user) {
        final Notification notification = notificationMapper.selectByPrimaryKey(id);
        if (notification.getReceiver() != user.getId()) {
            throw new CustomizeException(CustomizeErrorCode.ERROR_READ);
        }
        if (notification == null) {
            throw new CustomizeException(CustomizeErrorCode.MESSAGE_LOSE);
        }

        notification.setStatus(NotificationStatusEnums.READ.getStatus());
        notificationMapper.updateByPrimaryKey(notification);

        final NotificationDTO notificationDTO = new NotificationDTO();
        BeanUtils.copyProperties(notification, notificationDTO);
        final String typeName = NotificationTypeEnums.replyType(notification.getType());
        notificationDTO.setTypeName(typeName);

        return notificationDTO;
    }
}
