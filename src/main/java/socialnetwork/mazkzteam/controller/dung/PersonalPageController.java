package socialnetwork.mazkzteam.controller.dung;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import socialnetwork.mazkzteam.model.entities.*;
import socialnetwork.mazkzteam.model.service.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/personal/{username}")
@CrossOrigin("*")
public class PersonalPageController {

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    @Autowired
    private PhotoService photoService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private EmoteService emoteService;

    @GetMapping
    public User getUser(@PathVariable("username") String username){
        return userService.findUserByUsername(username);
    }

    @GetMapping("/posts")
    public List<Post> getPosts(@PathVariable("username") String username){
        User user = userService.findUserByUsername(username);

        List<Post> postList = postService.findAllByUser(user);
        Collections.reverse(postList);

        return postList;
    }

    @PostMapping("/create/post")
    public Post createPost(@PathVariable("username") String username,@RequestBody Post post){
        User user = userService.findUserByUsername(username);
        post.setUser_id(user.getId());
        post.setCreatedDate(Timestamp.valueOf(LocalDateTime.now()));
        if(post.getClub_id() == 0){
            post.setClub_id(9999);
        }
        Post post1 = postService.save(post);

        List<Photo> photoList = post.getPhotoList();
        for (Photo photo : photoList) {
            photo.setPost_id(post.getId());
        }
        photoService.saveAllPhoto(photoList);
        post1.setUser(user);
        return post1;
    }

    @PostMapping("/create/comment")
    public Comment createComment(@PathVariable("username") String username,@RequestBody Comment comment){
        User user = userService.findById(comment.getUser_id());
        comment.setUser_id(user.getId());
        comment.setCreatedDate(Timestamp.valueOf(LocalDateTime.now()));
        Comment newComment = commentService.save(comment);
        newComment.setUser(user);
        return newComment;
    }

    @PostMapping("/create/emote")
    public Emote createEmote(@RequestBody Emote emote){
        User user = userService.findById(emote.getUser_id());
        Emote emote1 = emoteService.save(emote);
        emote1.setUser(user);

        return emote1;
    }

    @DeleteMapping("/delete/post/{id}")
    public boolean deletePost(@PathVariable("id") int id){
        return postService.deleteById(id);
    }

    @DeleteMapping("/delete/comment/{id}")
    public boolean deleteComment(@PathVariable("id") int id){
        return commentService.deleteById(id);
    }

    @DeleteMapping("/delete/emote/post/{id}")
    public boolean deleteEmote(@PathVariable("username") String username,@PathVariable("id") int id){
        User user = userService.findUserByUsername(username);
        return emoteService.dislikedPost(id, user.getId());
    }

    @DeleteMapping("/delete/emote/comment/{id}")
    public boolean deleteEmoteComment(@PathVariable("username") String username,@PathVariable("id") int id){
        User user = userService.findUserByUsername(username);
        return emoteService.dislikedComment(id, user.getId());
    }

    @PutMapping("update/post")
    public Post updatePost(@PathVariable("username") String username,@RequestBody Post post){
        User user = userService.findUserByUsername(username);

        Post post1 = postService.findById(post.getId());
        post1.setModifiedAt(Timestamp.valueOf(LocalDateTime.now()));
        post1.setContent(post.getContent());

        photoService.deleteAllPhoto(post1.getId());

        List<Photo> newPhotoList = post.getPhotoList();
        for (Photo photo : newPhotoList) {
            photo.setPost_id(post.getId());
        }
        photoService.saveAllPhoto(newPhotoList);

        return postService.save(post1);
    }

    @PutMapping("update/comment")
    public Comment updateComment(@PathVariable("username") String username,@RequestBody Comment comment){
        Comment comment1 = commentService.findById(comment.getId());
        comment1.setContent(comment.getContent());

        return commentService.save(comment1);
    }

    @GetMapping("/post/{id}")
    public Post search(@PathVariable("id") int id){
        return postService.findById(id);
    }

    @GetMapping("/comment/{id}")
    public Comment searchcomment(@PathVariable("id") int id){
        return commentService.findById(id);
    }


    
}
