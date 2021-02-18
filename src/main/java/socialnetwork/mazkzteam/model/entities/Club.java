package socialnetwork.mazkzteam.model.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Club {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private int permission;

    //1: public 2: private

    private Timestamp createdDate;

    @OneToMany(mappedBy = "club", cascade = CascadeType.REMOVE)
    private List<Post> postList;

    @ManyToOne
    @JoinColumn(name = "founder_id",insertable = false,updatable = false)
    private User founder;

    private int founder_id;

    @ManyToMany
    @JoinTable(
            name = "clubs_members",
            joinColumns = {@JoinColumn(name="club_id")},
            inverseJoinColumns = {@JoinColumn(name="member_id")}
    )
    private List<User> members;

    @ManyToMany
    @JoinTable(
            name = "clubs_userReqToJoins",
            joinColumns = {@JoinColumn(name="club_id")},
            inverseJoinColumns = {@JoinColumn(name="user_id")}
    )
    private List<User> userReqToJoin;

}

