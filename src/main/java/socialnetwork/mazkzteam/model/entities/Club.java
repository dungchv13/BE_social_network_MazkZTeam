package socialnetwork.mazkzteam.model.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @JsonProperty
    @Column(unique = true)
    private String name;

    @JsonProperty
    private int permission;

    //1: public 2: private

    private Timestamp createdDate;

    private String detail;

//    @OneToMany(mappedBy = "club", cascade = CascadeType.REMOVE)
//    private List<Post> postList;

    @ManyToOne
    @JoinColumn(name = "founder_id",insertable = false,updatable = false)
    private User founder;

    private int founder_id;


    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
    @ManyToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
    @JoinTable(
            name = "clubs_members",
            joinColumns = {@JoinColumn(name="club_id")},
            inverseJoinColumns = {@JoinColumn(name="member_id")}
    )
    private Set<User> members = new HashSet<>();


    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
    @ManyToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
    @JoinTable(
            name = "clubs_user_req_to_joins",
            joinColumns = {@JoinColumn(name="club_id")},
            inverseJoinColumns = {@JoinColumn(name="user_id")}
    )
    private Set<User> userReqToJoi = new HashSet<>();

}

