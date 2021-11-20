package com.md.blog;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.md.blog.dto.converter.CommentDtoConverter;
import com.md.blog.dto.converter.PostDtoConverter;
import com.md.blog.dto.converter.UserDtoConverter;
import com.md.blog.repository.CommentRepository;
import com.md.blog.repository.PostRepository;
import com.md.blog.repository.UserRepository;
import com.md.blog.service.CommentService;
import com.md.blog.service.PostService;
import com.md.blog.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.util.regex.Pattern;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // Context ayaga kaldirir
@TestPropertySource(locations = "classpath:application.properties") // Test context icin kullanilacak propertyleri ayarlar.
@DirtiesContext
@AutoConfigureMockMvc // Context icerisindeki servletleri ayaga kaldirir.
public class IntegrationTestSupport extends TestSupport{
    @Autowired
    public CommentService commentService;
    @Autowired
    public CommentRepository commentRepository;
    @Autowired
    public CommentDtoConverter commentDtoConverter;
    @Autowired
    public UserService userService;
    @Autowired
    public UserRepository userRepository;
    @Autowired
    public UserDtoConverter userDtoConverter;
    @Autowired
    public PostService postService;
    @Autowired
    public PostRepository postRepository;
    @Autowired
    public PostDtoConverter postDtoConverter;
    @Autowired
    public MockMvc mockMvc;

    public final ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    public void setup() {
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS, false);
    }


}
