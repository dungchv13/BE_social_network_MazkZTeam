package socialnetwork.mazkzteam.model.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import socialnetwork.mazkzteam.model.entities.Comment;
import socialnetwork.mazkzteam.model.repositories.CommentRepository;
import socialnetwork.mazkzteam.model.service.CommentService;

import java.util.List;

public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public List<Comment> getAll() {
        return commentRepository.findAll();
    }

    @Override
    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public Comment findById(int id) {
        return commentRepository.findById(id).get();
    }

    @Override
    public boolean deleteById(int id) {
        if(findById(id) != null){
            commentRepository.deleteById(id);
            return true;
        }
        return false;
    }


}
