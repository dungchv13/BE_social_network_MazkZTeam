package socialnetwork.mazkzteam.controller.hai;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import socialnetwork.mazkzteam.controller.Response;
import socialnetwork.mazkzteam.model.entities.Post;
import socialnetwork.mazkzteam.model.service.PostService;

import java.util.List;

@RestController
@RequestMapping("/post/api")
@CrossOrigin(origins = "*")
public class PostController {
    @Autowired
    PostService postService;

    Response res = new Response();

    @GetMapping()
    public Response getAll(){
        List<Post> posts = postService.getAll();
        res.data = posts;
        res.status = res.SUCCESS;
        res.message = "Success";
        return res;
    }

    @GetMapping("post")
    public Response getPost(@RequestParam Integer id){
        res.data = postService.findById(id);
        res.status = res.SUCCESS;
        res.message = "Success";
        return res;
    }

    @PostMapping("/create")
    public Response save(@RequestBody Post p){
        Post post = postService.save(p);
        res.data = post;
        res.status = res.SUCCESS;
        res.message = "Success";
        return res;
    }

    @DeleteMapping()
    public Response delete(@RequestParam Integer id){
        Boolean isDeleted =  postService.deleteById(id);
        res.data = isDeleted;
        res.status = res.SUCCESS;
        res.message = "Success";
        return res;
    }

    @PutMapping("/update")
    public Response update(@RequestBody Post p){
        Post post = postService.save(p);
        res.data = post;
        res.status = res.SUCCESS;
        res.message = "Success";
        return res;
    }
}
