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
            notification.setCreatedDate(Timestamp.valueOf(LocalDateTime.now()));
            notificationService.save(notification);
            return new ResponseEntity<>(notification, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{id}/{username}")
//    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<String> deleteNotification(@PathVariable("id") int id, @PathVariable("username") String username) {
        User receiverUser = userService.findUserByUsername(username);
        notificationService.deleteByReceiverIdAndSenderId(receiverUser.getId(), id);
        return new ResponseEntity<>("Notification has been removed",HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
//    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Notification> updateProduct(@PathVariable("id") int id, @RequestBody Notification notification) {
        Notification notification1 = notificationService.findById(id);

            notification1.setTypeNoti(notification.getTypeNoti());
            notification1.setUser_receiver_id(notification.getUser_receiver_id());
            notification1.setUser_sender_id(notification.getUser_sender_id());
            User sender = userService.findById(notification.getUser_sender_id());
            User receiver = userService.findById(notification.getUser_receiver_id());
            notification1.setUserSender(sender);
            notification1.setUserReceiver(receiver);
            notification1.setStatus(notification.isStatus());
            notificationService.save(notification1);
            return new ResponseEntity<>(notification1, HttpStatus.OK);
    }


}
