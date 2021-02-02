package socialnetwork.mazkzteam.model.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Friendship {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_sender_id",insertable = false,updatable = false)
    private User userSender;

    private Integer user_sender_id;

    @ManyToOne
    @JoinColumn(name = "user_receiver_id",insertable = false,updatable = false)
    private User userReceiver;

    private Integer user_receiver_id;

    @JsonProperty
    private boolean status;
}
