package socialnetwork.mazkzteam.controller.hai;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import socialnetwork.mazkzteam.model.entities.Notification;
import socialnetwork.mazkzteam.model.entities.User;
import socialnetwork.mazkzteam.model.service.NotificationService;
import socialnetwork.mazkzteam.model.service.UserService;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/notification")
public class NotificationController {
    @Autowired
    NotificationService notificationService;

    @Autowired
    UserService userService;

    @GetMapping("/all/{id}")
    public List<Notification> getNotification(@PathVariable("id") int id){
        return notificationService.findAllByUser_receiver_id(id);
    }

    @GetMapping("/{id}")
    public Notification getNotificationById(@PathVariable("id") int id) {
        Notification notification = notificationService.findById(id);
        return notification;
    }


    @PostMapping("/create")
//    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER') ")
    public ResponseEntity<Notification> createNotification(@RequestBody Notification notification) {
        try {
            User sender = userService.findById(notification.getUser_sender_id());
            User receiver = userService.findById(notification.getUser_receiver_id());
            notification.setStatus(false);
            notification.setUserSender(sender);
            notification.setUserReceiver(receiver);
            Date date = new Date();
            Timestamp created_date = new Timestamp(date.getTime());
            notification.setCreatedDate(created_date);
            notificationService.save(notification);
            return new ResponseEntity<>(notification, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{id}/{username}")
    public void deleteNotification(@PathVariable("id") int id, @PathVariable("username") String username) {
        User receiverUser = userService.findUserByUsername(username);
        notificationService.deleteByReceiverIdAndSenderId(receiverUser.getId(), id);
    }

    //readNotification.
    @PutMapping("/read")
    public ResponseEntity<Notification> updateProduct(@RequestBody Notification notification) {
            Notification updatedNotification = notification;
            updatedNotification.setStatus(true);
            notificationService.save(updatedNotification);
            return new ResponseEntity<>(updatedNotification, HttpStatus.OK);
    }


}
