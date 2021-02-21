package socialnetwork.mazkzteam.model.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import socialnetwork.mazkzteam.model.entities.Notification;
import socialnetwork.mazkzteam.model.repositories.NotificationRepository;
import socialnetwork.mazkzteam.model.service.NotificationService;

import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Override
    public List<Notification> getAll() {
        return notificationRepository.findAll();
    }

    @Override
    public Notification save(Notification notification) {
        return notificationRepository.save(notification);
    }

    @Override
    public Notification findById(int id) {
        return notificationRepository.findById(id).get();
    }

    @Override
    public boolean deleteById(int id) {
        if (findById(id) != null) {
            notificationRepository.deleteById(id);
            return true;
        }
        return false;
    }


    @Override
    public List<Notification> findAllByUser_receiver_id(int id) {
        return notificationRepository.findAllByUser_receiver_id(id);
    }

    @Override
    public void deleteByReceiverIdAndSenderId(int receiverId, int senderId) {
        notificationRepository.deleteByReceiverIdAndSenderId(receiverId, senderId);
    }
}
