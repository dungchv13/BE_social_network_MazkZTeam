package socialnetwork.mazkzteam.controller.hai;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import socialnetwork.mazkzteam.controller.Response;
import socialnetwork.mazkzteam.model.entities.*;
import socialnetwork.mazkzteam.model.service.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/post/api/{username}")
@CrossOrigin(origins = "*")
public class PostController {
    @Autowired
    PostService postService;

    @Autowired
    UserService userService;

    @Autowired
    PhotoService photoService;
    @Autowired
    CommentService commentService;
    Response res = new Response();

    @Autowired
    EmoteService emoteService;

    @GetMapping
    public User getUser(@PathVariable("username") String username) {
        return userService.findUserByUsername(username);
    }

    @GetMapping("/posts")
    public Response getAll(@PathVariable("username") String username) {
        User user = userService.findUserByUsername(username);
        List<Post> posts = postService.getAll();
        Collections.reverse(posts);
        res.data = user;
        res.data = posts;
        res.status = res.SUCCESS;
        res.message = "Success";
        return res;
    }

    @GetMapping("post")
    public Response getPost(@RequestParam Integer id) {
        res.data = postService.findById(id);
        res.status = res.SUCCESS;
        res.message = "Success";
        return res;
    }

    @PostMapping("/create")
    public Response save(@PathVariable("username") String username, @RequestBody Post p) {
        User user = userService.findUserByUsername(username);
        p.setUser_id(user.getId());
        p.setCreatedDate(Timestamp.valueOf(LocalDateTime.now()));
        Post post = postService.save(p);

        List<Photo> photoList = p.getPhotoList();
        for (Photo photo : photoList
        ) {
            photo.setPost_id(p.getId());
        }
        photoService.saveAllPhoto(photoList);
        post.setUser(user);
        res.data = post;
        res.status = res.SUCCESS;
        res.message = "Success";
        return res;
    }

    @PostMapping("/create/comment")
    public Comment createComment(@PathVariable("username") String username, @RequestBody Comment comment){
        User user = userService.findUserByUsername(username);
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

    @PutMapping("/update")
    public Response update(@RequestBody Post p) {
        Post post = postService.save(p);
        res.data = post;
        res.status = res.SUCCESS;
        res.message = "Success";
        return res;
    }

//    @GetMapping("/checkliked/{postId}")
//    public Response isLiked(@PathVariable("username") String username, @PathVariable ("postId") int postId){
//        User user = userService.findUserByUsername(username);
//        List<Emote> emoteList = emoteService.isLiked(postId, user.getId());
//        res.data = emoteList.size();
//        res.status = res.SUCCESS;
//        res.message = "Success";
//        return res;
//    }

    @DeleteMapping("/dislike/{postId}")
    public boolean disLiked(@PathVariable("username") String username, @PathVariable("postId") int postId){
        User user = userService.findUserByUsername(username);
        return emoteService.disLiked(postId,user.getId());
    }

    @PutMapping("/update/post")
    public Post updatePost(@PathVariable("username") String username,@RequestBody Post post){
        User user = userService.findUserByUsername(username);
        Post post1 = postService.findById(post.getId());
        post1.setModifiedAt(Timestamp.valueOf(LocalDateTime.now()));
        post1.setContent(post.getContent());

        photoService.deleteAllPhotoByHai(post1.getId());

        List<Photo> newPhotoList = post.getPhotoList();
        for (Photo photo : newPhotoList) {
            photo.setPost_id(post.getId());
        }
        photoService.saveAllPhoto(newPhotoList);

        return postService.save(post1);
    }

    @DeleteMapping("/delete/comment/{id}")
    public boolean deleteComment(@PathVariable("id") int id){
        return commentService.deleteById(id);
    }

    @PutMapping("update/comment")
    public Comment updateComment(@PathVariable("username") String username,@RequestBody Comment comment){
        User user = userService.findUserByUsername(username);
        Comment comment1 = commentService.findById(comment.getId());
        comment1.setContent(comment.getContent());

        return commentService.save(comment1);
    }

    @DeleteMapping("/delete/emote/comment/{id}")
    public boolean deleteEmoteComment(@PathVariable("username") String username,@PathVariable("id") int id){
        User user = userService.findUserByUsername(username);
        return emoteService.dislikedComment(id, user.getId());
    }

}
