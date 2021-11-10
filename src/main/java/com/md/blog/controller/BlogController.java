package com.md.blog.controller;

//import com.md.blog.dto.CreateBlogDto;
//import com.md.blog.dto.CreateBlogRequest;
import com.md.blog.dto.PostDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/blog")
public class BlogController {

    @GetMapping
    public ResponseEntity<String> deneme(){
        return ResponseEntity.ok("deneme");
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<String> getMovieById(@PathVariable String id){
        return ResponseEntity.ok("get by id");
    }

    @PostMapping("/{id}")
    public ResponseEntity<String> createMovie(@RequestBody String id){
        return new ResponseEntity<>("id" + id + "is created", HttpStatus.CREATED);
    }

    /* TODO */
    @PutMapping("/{id}")
    public ResponseEntity<String> updateBlogById(@PathVariable String id, @RequestBody String blogId) {
        return ResponseEntity.ok("blog id : " + id + " is updated with" + blogId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBlogById(@PathVariable String id) {
        return ResponseEntity.ok("blog id : " + id + " deleted");
    }

//    @PostMapping("/aaa")
//    public ResponseEntity<CreateBlogDto> createBlog(@Valid @RequestBody CreateBlogRequest createBlogRequest){
//       String author = createBlogRequest.getAuthor();
//       String body = createBlogRequest.getBody();
//
//
//        CreateBlogDto createBlogDto = new CreateBlogDto(createBlogRequest.getTitle(),body,author);
//        return new ResponseEntity<>(createBlogDto,HttpStatus.CREATED);
//    }
}
