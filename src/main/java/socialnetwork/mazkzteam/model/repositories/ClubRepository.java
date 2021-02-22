package socialnetwork.mazkzteam.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import socialnetwork.mazkzteam.model.entities.Club;
import socialnetwork.mazkzteam.model.entities.User;

import java.util.List;

public interface ClubRepository extends JpaRepository<Club,Integer> {

//    @Query(value = "SELECT *\n" +
//            "FROM club where founder_id = ?1",nativeQuery = true)
//    List<Club> clubListByUserCreate(Integer id);

    List<Club> getClubsByFounder_Id(int user_id);

    List<Club> getClubByMembersContains(User user);

    List<Club> getClubByUserReqToJoiContains(User user);

    List<Club> getClubsByMembersIsNotContainingAndUserReqToJoiIsNotContaining(User user1,User user2);

    @Transactional
    @Modifying
    @Query(value = "DELETE from clubs_members where member_id = ?1 and club_id = ?2",nativeQuery = true)
    void leaveClub(int user_id,int club_id);

    @Transactional
    @Modifying
    @Query(value = "DELETE from clubs_members where user_id = ?1 and club_id = ?2",nativeQuery = true)
    void cancelJoinReq(int user_id,int club_id);


    @Transactional
    @Modifying
    @Query(value = "INSERT into clubs_user_req_to_joins values (?1,?2)",nativeQuery = true)
    void reqToJoin(int club_id,int user_id);

    @Transactional
    @Modifying
    @Query(value = "INSERT into clubs_members values (?1,?2)",nativeQuery = true)
    void acceptJoinClub(int club_id,int member_id);


    Club findClubByName(String club_name);
}
