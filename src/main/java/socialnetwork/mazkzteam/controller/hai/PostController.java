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

    @PostMapping("/create/like")
    public Emote addLike(@PathVariable("username") String username, @RequestBody Emote emote){
        User user = userService.findUserByUsername(username);
        emote.setPost_id(emote.getPost_id());
        emote.setUser_id(user.getId());
        Emote newLike = emoteService.save(emote);
        return newLike;
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

    @GetMapping("/checkliked/{postId}")
    public Response isLiked(@PathVariable("username") String username, @PathVariable ("postId") int postId){
        User user = userService.findUserByUsername(username);
        List<Emote> emoteList = emoteService.isLiked(postId, user.getId());
        res.data = emoteList.size();
        res.status = res.SUCCESS;
        res.message = "Success";
        return res;
    }

    @DeleteMapping("/dislike/{postId}")
    public void disLiked(@PathVariable("username") String username, @PathVariable("postId") int postId){
        User user = userService.findUserByUsername(username);
        emoteService.disLiked(postId,user.getId());
    }
}
