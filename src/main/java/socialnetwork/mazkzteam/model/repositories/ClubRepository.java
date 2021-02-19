package socialnetwork.mazkzteam.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import socialnetwork.mazkzteam.model.entities.Club;
import socialnetwork.mazkzteam.model.entities.User;

import java.util.List;

public interface ClubRepository extends JpaRepository<Club,Integer> {
//    @Query(value = "SELECT *\n" +
//            "FROM club where founder_id = ?1",nativeQuery = true)
//    List<Club> clubListByUserCreate(Integer id);

    List<Club> getClubsByFounder_Id(int user_id);

    List<Club> getClubByMembersContains(User user);

    List<Club> getClubsByMembersIsNotContaining(User user);
}
