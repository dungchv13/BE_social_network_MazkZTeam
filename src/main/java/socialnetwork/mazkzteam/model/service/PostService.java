package socialnetwork.mazkzteam.model.service;

import org.springframework.stereotype.Service;
import socialnetwork.mazkzteam.model.entities.Post;

import socialnetwork.mazkzteam.model.entities.User;

import java.util.List;


public interface PostService extends CommonService<Post>{
    List<Post> findAllByUser(User user);
}
