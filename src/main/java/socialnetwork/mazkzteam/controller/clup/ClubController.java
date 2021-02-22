package socialnetwork.mazkzteam.controller.clup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import socialnetwork.mazkzteam.model.entities.Club;
import socialnetwork.mazkzteam.model.entities.User;
import socialnetwork.mazkzteam.model.service.ClubService;
import socialnetwork.mazkzteam.model.service.UserService;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/club/{username}")
@CrossOrigin("*")
public class ClubController {

    @Autowired
    private ClubService clubService;

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public Club createClub(@RequestBody Club club,@PathVariable("username") String username){
        User user = userService.findUserByUsername(username);
        club.setCreatedDate(Timestamp.valueOf(LocalDateTime.now()));
        club.setFounder_id(user.getId());
        club.getMembers().add(user);
        return clubService.save(club);
    }

    @GetMapping("/listclubbyusercreate")
    public List<Club> getListClubByUserCreate(@PathVariable("username") String username){
        User user = userService.findUserByUsername(username);
        List<Club> listClubByUserCreate = clubService.getClubsByUserCreate(user.getId());
        Collections.reverse(listClubByUserCreate);
        return listClubByUserCreate;
    }

    @GetMapping("/getclubsjoined")
    public List<Club> getClubsUserJoined(@PathVariable("username") String username){
        User user = userService.findUserByUsername(username);
        List<Club> listClubs = clubService.getClubByMembersContains(user);

        List<Club> listClubByUserCreate = clubService.getClubsByUserCreate(user.getId());

        listClubs.removeAll(listClubByUserCreate);

        Collections.reverse(listClubs);

        return listClubs;
    }

    @GetMapping("/getclubsnotjoinedyet")
    public List<Club> getClubsUserNotJoinedYet(@PathVariable("username") String username){
        User user = userService.findUserByUsername(username);
        List<Club> listClubs = clubService.getClubsByMembersIsNotContainingAndUserReqToJoiIsNotContaining(user);
        listClubs.remove(0);

        Collections.reverse(listClubs);
        return listClubs;
    }

    @GetMapping("/getclubsrequested")
    public List<Club> getClubsRequested(@PathVariable("username") String username){
        User user = userService.findUserByUsername(username);
        List<Club> listClubs = clubService.getClubByUserReqToJoiContains(user);
        Collections.reverse(listClubs);
        return listClubs;
    }

    @GetMapping("/reqjoin/{club_id}")
    public boolean reqJoin(@PathVariable("username")String username,@PathVariable("club_id")int club_id){
        User user = userService.findUserByUsername(username);
        return clubService.reqToJoin(user.getId(), club_id);
    }



    @DeleteMapping("/leaveclub/{club_id}")
    public boolean leaveClub(@PathVariable("username") String username,@PathVariable("club_id") int club_id){
        User user = userService.findUserByUsername(username);
        return clubService.leaveClub(user.getId(),club_id);
    }

    @DeleteMapping("/canceljoinreq/{club_id}")
    public boolean cancelJoinReq(@PathVariable("username") String username,@PathVariable("club_id") int club_id){
        Club club = clubService.findById(club_id);
        User user = userService.findUserByUsername(username);
        club.getUserReqToJoi().remove(user);
        try{
            clubService.save(club);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @DeleteMapping("/deleteclub/{club_id}")
    public boolean deleteClub(@PathVariable("club_id") int club_id){
        Club club = clubService.findById(club_id);

        for (User user :club.getMembers()) {
            clubService.leaveClub(user.getId(),club_id);
        }
        for (User user :club.getUserReqToJoi()) {
            clubService.cancelJoinReq(user.getId(),club_id);
        }

        return clubService.deleteById(club_id);
    }


}
