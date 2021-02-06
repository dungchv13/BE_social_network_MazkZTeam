package socialnetwork.mazkzteam.model.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import socialnetwork.mazkzteam.model.entities.Post;
import socialnetwork.mazkzteam.model.entities.User;
import socialnetwork.mazkzteam.model.repositories.PostRepository;
import socialnetwork.mazkzteam.model.service.PostService;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Override
    public List<Post> getAll() {
        return postRepository.findAll();
    }

    @Override
    public Post save(Post post) {
        return postRepository.save(post);
    }

    @Override
    public Post findById(int id) {
        return postRepository.findById(id).get();
    }

    @Override
    public boolean deleteById(int id) {
        if(findById(id) != null){
            postRepository.deleteById(id);
            return true;
        }
        return false;
    }


    @Override
    public List<Post> findAllByUser(User user) {
        return postRepository.findAllByUser(user);
    }

}
