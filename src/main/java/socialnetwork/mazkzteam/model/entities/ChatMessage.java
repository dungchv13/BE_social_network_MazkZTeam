package socialnetwork.mazkzteam.model.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
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
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Transient
    private MessageType type;

    private String content;

    @ManyToOne
    @JoinColumn(name = "user_sender_id",insertable = false,updatable = false)
    private User sender;

    private int user_sender_id;

    @ManyToOne
    @JoinColumn(name = "user_receiver_id",insertable = false,updatable = false)
    private User receiver;

    private int user_receiver_id;

    @ManyToOne
    @JoinColumn(name = "chat_room_id",insertable = false,updatable = false)
    private ChatRoom chatRoom;

    private int chat_room_id;

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

}
