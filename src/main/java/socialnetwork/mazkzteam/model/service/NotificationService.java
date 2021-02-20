package socialnetwork.mazkzteam.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import socialnetwork.mazkzteam.model.entities.Notification;

import java.util.List;

public interface NotificationService extends CommonService<Notification> {

    List<Notification> findAllByUser_receiver_id(int id);

    void deleteByReceiverIdAndSenderId(int receiverId, int senderId);
}
