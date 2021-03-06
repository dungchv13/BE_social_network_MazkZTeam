package socialnetwork.mazkzteam.model.service;

import org.springframework.stereotype.Service;
import socialnetwork.mazkzteam.model.entities.Club;
import socialnetwork.mazkzteam.model.entities.Post;
import socialnetwork.mazkzteam.model.entities.User;


import java.util.List;


import java.util.List;

@Service
public interface PostService extends CommonService<Post>{
    List<Post> findAllByUser(User user);

    List<Post> findAllCommonFriendPublicPost(int id);

    List<Post> getPostsByClub(Club club);

    List<Post> findAllPublicUserPost(int id);

    List<Post> findAllPublicAndFriendUserPost(int id);

    boolean deleteAllByClub_id(int id);
}
