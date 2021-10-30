package com.md.blog.Controller;

import com.md.blog.Dto.CreateBlogDto;
import com.md.blog.Dto.CreateBlogRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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

    @PostMapping
    public ResponseEntity<CreateBlogDto> createBlog(@Valid @RequestBody CreateBlogRequest createBlogRequest){
        int birthday = 2021 - createBlogRequest.getAge();
        CreateBlogDto createBlogDto = new CreateBlogDto(createBlogRequest.getName(),birthday);
        return new ResponseEntity<>(createBlogDto,HttpStatus.CREATED);
    }
}
