package socialnetwork.mazkzteam.model.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@CrossOrigin("*")
@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String typeNoti;

    @ManyToOne
    @JoinColumn(name = "user_sender_id",insertable = false,updatable = false)
    private User userSender;

    private int user_sender_id;


    @ManyToOne
    @JoinColumn(name = "user_receiver_id",insertable = false,updatable = false)
    private User userReceiver;

    private int user_receiver_id;

    private Timestamp createdDate;

    @JsonProperty
    private boolean status;


}
