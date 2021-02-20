package socialnetwork.mazkzteam.model.service;

import socialnetwork.mazkzteam.model.entities.Club;
import socialnetwork.mazkzteam.model.entities.User;

import java.util.List;

public interface ClubService extends CommonService<Club> {
    List<Club> getClubsByUserCreate(int user_id);

    List<Club> getClubByMembersContains(User user);

    List<Club> getClubsByMembersIsNotContaining(User user);

    boolean leaveClub(int user_id,int club_id);

    boolean cancelJoinReq(int user_id,int club_id);

    boolean reqToJoin(int user_id,int club_id);
}
