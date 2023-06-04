package com.example.book.service;

import com.example.book.domain.PostDetails;
import com.example.book.domain.PostHandling;
import com.example.book.domain.UserInfoDetails;
import com.example.book.entity.Post;
import com.example.book.entity.User;
import com.example.book.repository.PostRepository;
import com.example.book.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class PostService {
    private final ModelMapper mapper;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    public List<PostDetails> getPosts(){
        List<Post> posts = postRepository.findPostsOrderByTime();
        List<PostDetails> community_posts = new ArrayList<>();
        for (Post post : posts){
            User creator = post.getCreator();
            PostDetails postDetails = mapper.map(post, PostDetails.class);
            // Format created_time for post
            LocalDateTime created_time = post.getCreated_time();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy 'at' hh:mm a");
            postDetails.setLast_updated(created_time.format(formatter));
            // Mapper for post creator image
            UserInfoDetails creator_detail = mapper.map(creator, UserInfoDetails.class);
            // Set creator image for post
            postDetails.setCreator_detail(creator_detail);
            // Configure for post that contains image as content
            community_posts.add(postDetails);
        }
        return community_posts;
    }

    public void saveNewPost(PostHandling postHandling){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findUserByEmail(auth.getName()).get();
        Post post = mapper.map(postHandling, Post.class);
        if (postHandling.getContent_image().isEmpty()){
            post.setContent_image(null);
        }
        post.setId(UUID.randomUUID());
        post.setCreator(user);
        post.setTitle(postHandling.getTitle());
        post.setContent_text(postHandling.getContent_text());
        post.setCreated_time(LocalDateTime.now());
        postRepository.save(post);
    }

    public void saveUpdatedPost(UUID post_id, String title, String content_text) {
        Post post = postRepository.findById(post_id).orElseThrow();
        post.setTitle(title);
        post.setContent_text(content_text);
        post.setCreated_time(LocalDateTime.now());
        postRepository.save(post);
    }

    public void deletePost(UUID post_id){
        Post post = postRepository.findById(post_id).orElseThrow();
        postRepository.delete(post);
    }
}
