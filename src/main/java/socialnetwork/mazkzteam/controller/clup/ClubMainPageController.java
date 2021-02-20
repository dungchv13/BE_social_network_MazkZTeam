package socialnetwork.mazkzteam.controller.clup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import socialnetwork.mazkzteam.model.entities.Club;
import socialnetwork.mazkzteam.model.service.ClubService;
import socialnetwork.mazkzteam.model.service.PostService;
import socialnetwork.mazkzteam.model.service.UserService;

@RestController
@RequestMapping("/clubmainpage/{username}")
@CrossOrigin("*")
public class ClubMainPageController {

    @Autowired
    private ClubService clubService;

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

//    @GetMapping("/{club_name}")
//    public Club getClub(){
//
//    }
}
