package socialnetwork.mazkzteam.controller.clup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import socialnetwork.mazkzteam.model.entities.Club;
import socialnetwork.mazkzteam.model.entities.Post;
import socialnetwork.mazkzteam.model.entities.User;
import socialnetwork.mazkzteam.model.service.ClubService;
import socialnetwork.mazkzteam.model.service.PostService;
import socialnetwork.mazkzteam.model.service.UserService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/clubmainpage/{username}/{club_name}")
@CrossOrigin("*")
public class ClubMainPageController {

    @Autowired
    private ClubService clubService;

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    @GetMapping
    public Club getClub(@PathVariable("club_name") String club_name){
        return clubService.findClubByName(club_name);
    }

    @GetMapping("/ismember")
    public boolean checkMember(@PathVariable("club_name") String club_name,
                               @PathVariable("username") String username){
        User user = userService.findUserByUsername(username);
        Club club = clubService.findClubByName(club_name);
        if(club.getMembers().contains(user)){
            return true;
        }
        return false;
    }

    @GetMapping("/posts")
    public List<Post> getPosts(@PathVariable("club_name") String club_name,
                               @PathVariable("username") String username){
        User user = userService.findUserByUsername(username);
        Club club = clubService.findClubByName(club_name);
        List<User> members = club.getMembers();
        if(members.contains(user) || club.getPermission() == 1){
            return postService.getPostsByClub(club);
        }
        else{
            return new ArrayList<>();
        }
    }

    @GetMapping("/members")
    public List<User> getMembers(@PathVariable("club_name") String club_name,
                               @PathVariable("username") String username){
        User user = userService.findUserByUsername(username);
        Club club = clubService.findClubByName(club_name);
        if(user.getId() == club.getFounder_id()) {
            club.getMembers().remove(userService.findById(club.getFounder_id()));
        }
        return club.getMembers();
    }

    @GetMapping("/reqjoins")
    public List<User> getReqJoins(@PathVariable("club_name") String club_name,
                                 @PathVariable("username") String username){
        User user = userService.findUserByUsername(username);
        Club club = clubService.findClubByName(club_name);
        if(club.getFounder_id() == user.getId()){
            return club.getUserReqToJoi();
        }else{
            return new ArrayList<>();
        }
    }

    @GetMapping("/acceptjoinreq/{reqjoin_id}")
    public boolean acceptJoinReq(@PathVariable("club_name") String club_name,
                              @PathVariable("reqjoin_id") int reqjoin_id){
        Club club = clubService.findClubByName(club_name);
        User user = userService.findById(reqjoin_id);
        club.getUserReqToJoi().remove(user);
        club.getMembers().add(user);
        try{
            clubService.save(club);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @DeleteMapping("/kick/{member_id}")
    public boolean kickMember(@PathVariable("club_name") String club_name,
                              @PathVariable("member_id") int member_id){
        Club club = clubService.findClubByName(club_name);
        return clubService.leaveClub(member_id, club.getId());
    }

    @DeleteMapping("/refuse/{reqjoin_id}")
    public boolean refuseJoinReq(@PathVariable("club_name") String club_name,
                              @PathVariable("reqjoin_id") int reqjoin_id){
        Club club = clubService.findClubByName(club_name);
        User user = userService.findById(reqjoin_id);
        club.getUserReqToJoi().remove(user);
        try{
            clubService.save(club);
            return true;
        }catch (Exception e){
            return false;
        }
    }


}
