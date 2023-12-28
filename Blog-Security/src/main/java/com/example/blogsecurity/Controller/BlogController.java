package com.example.blogsecurity.Controller;

import com.example.blogsecurity.Model.Blog;
import com.example.blogsecurity.Model.User;
import com.example.blogsecurity.Service.BlogService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/blog")
@RequiredArgsConstructor
public class BlogController {
    private final BlogService blogService;

    //ADMIN
    @GetMapping("/get")
    public ResponseEntity getAllTodos(){
        return ResponseEntity.status(200).body(blogService.getAllBlogs());
    }

    //USER
    //مافيه غير اليوزرنيم و الباسوورد هذا الاوبجكت
    @GetMapping("/get-blogs")
    public ResponseEntity getMyTodos(@AuthenticationPrincipal User user){
        return ResponseEntity.status(200).body(blogService.getMyBlogs(user.getId()));
    }
    //USER
    @PostMapping("/add")
    public ResponseEntity addToMyBlog(@RequestBody @Valid Blog blog, @AuthenticationPrincipal User user){
        blogService.addToMyBlog(blog,user.getId());
        return ResponseEntity.status(200).body("Blog Added");
    }
    //USER
    @PutMapping("/update/{id}")
    public ResponseEntity updateBlog(@RequestBody @Valid Blog todo, @PathVariable Integer id, @AuthenticationPrincipal User user){
        blogService.updateBlog(id,todo,user.getId());
        return ResponseEntity.status(200).body(("Blog Updated"));
    }

    // User
    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteBlog(@PathVariable Integer id, @AuthenticationPrincipal User user){
        blogService.deleteBlog(id,user.getId());
        return ResponseEntity.status(200).body(("Blog deleted"));
    }

    //findBlogByTitle
    @GetMapping("/title/{title}")
    public ResponseEntity findBlogByTitle(@PathVariable String title){
        return ResponseEntity.status(200).body(blogService.findBlogByTitle(title));
    }
}
