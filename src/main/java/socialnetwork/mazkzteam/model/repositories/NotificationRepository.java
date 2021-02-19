package socialnetwork.mazkzteam.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import socialnetwork.mazkzteam.model.entities.Notification;

public interface NotificationRepository extends JpaRepository<Notification,Integer> {
}
