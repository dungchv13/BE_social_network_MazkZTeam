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
public class Emote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id",insertable = false,updatable = false)
    private User user;

    private Integer user_id;

    @ManyToOne
    @JoinColumn(name = "post_id",insertable = false,updatable = false)
    private Post post;

    private Integer post_id;

    @ManyToOne
    @JoinColumn(name = "comment_id",insertable = false,updatable = false)
    private Comment comment;

    private Integer comment_id;
}
