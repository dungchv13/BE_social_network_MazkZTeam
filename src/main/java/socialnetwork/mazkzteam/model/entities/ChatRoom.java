package socialnetwork.mazkzteam.model.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.persistence.*;

@CrossOrigin("*")
@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    @ManyToOne
    @JoinColumn(name = "first_user_id",insertable = false,updatable = false)
    private User firstUser;

    private int first_user_id;

    @ManyToOne
    @JoinColumn(name = "second_user_id",insertable = false,updatable = false)
    private User secondUser;

    private int second_user_id;

//    @OneToMany(mappedBy = "chatRoom")
//    private List<ChatMessage> messages;
}
