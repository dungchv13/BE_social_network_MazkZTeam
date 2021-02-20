package socialnetwork.mazkzteam.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import socialnetwork.mazkzteam.model.entities.Notification;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification,Integer> {
    @Query(value = "select * " +
            "from social_network.notification " +
            "where user_receiver_id =?1", nativeQuery = true)
    List<Notification> findAllByUser_receiver_id (int id);


    @Query(value = "delete from\n" +
            "            social_network.notification\n" +
            "            where user_receiver_id = ?1 and user_sender_id = ?2", nativeQuery = true)
    void deleteByReceiverIdAndSenderId(int receiverId, int senderId);
}
