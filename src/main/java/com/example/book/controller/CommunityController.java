package com.example.book.controller;

import com.example.book.domain.CommentDetails;
import com.example.book.domain.PostDetails;
import com.example.book.domain.PostHandling;
import com.example.book.entity.Comment;
import com.example.book.service.CommentService;
import com.example.book.service.PostService;
import com.example.book.service.RateService;
import com.example.book.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class CommunityController {
    private final UserService userService;
    private final PostService postService;
    private final RateService rateService;
    private final CommentService commentService;

    @RequestMapping(value = "/admin/community", method = RequestMethod.GET)
    public String communityPage(Model model, RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute("message","");
        userService.addUserAttributesToModel(model);
        List<PostDetails> posts = postService.getPosts();
        for(PostDetails post : posts){
            post.setPost_id(post.getId().toString());
            // Calculate star rating of a post
            float average_star = rateService.calculateAverageRateInPost(post);
            String star_text = String.format("%.1f", average_star).replace('.', ',');
            String[] starRatings = rateService.calculateStarRatings(average_star);
            String people_rates = rateService.countRateByPost(post.getId());
            // Get comments and their replies of a post
            List<CommentDetails> comments = commentService.viewCommentsInPost(post.getId());
            post.setComments(comments);
            // Add to model
            model.addAttribute("average_star_" + post.getPost_id(), star_text);
            model.addAttribute("people_rates_" + post.getPost_id(), people_rates);
            model.addAttribute("star_rating_" + post.getPost_id(), starRatings);
        }
        model.addAttribute("post_create", new PostHandling());
        model.addAttribute("posts",posts);
        return "admin/community";
    }

    @RequestMapping(value = "/admin/provide-rating/{id}", method = RequestMethod.POST)
    public String provideRating(@PathVariable("id") UUID post_id, @RequestParam("star") int star){
        rateService.updateRatingForPost(post_id,star);
        return "redirect:/admin/community";
    }

    @RequestMapping(value = "/admin/write-comment/{id}", method = RequestMethod.POST)
    public String writeComment(@PathVariable("id") UUID post_id, @RequestParam("comment") String comment_text){
        Comment comment = commentService.writeCommentForPost(post_id, comment_text);
        return "redirect:/admin/community";
    }

    @RequestMapping(value = "/admin/edit-comment/{id}", method = RequestMethod.POST)
    public String editComment(@PathVariable("id") Integer comment_id, @RequestParam("comment") String comment_text){
        Comment updated_comment = commentService.updateCommentForPost(comment_id,comment_text);
        return "redirect:/admin/community";
    }

    @RequestMapping(value = "/admin/delete-comment/{id}", method = RequestMethod.GET)
    public String deleteComment(@PathVariable("id") Integer comment_id){
        commentService.deleteComment(comment_id);
        return "redirect:/admin/community";
    }

    @RequestMapping(value = "/admin/reply/{id}", method = RequestMethod.POST)
    public String replyComment(@PathVariable("id") Integer comment_id, @RequestParam("reply") String reply){
        Comment reply_comment = commentService.saveReply(comment_id, reply);
        return "redirect:/admin/community";
    }

    @RequestMapping(value = "/admin/edit-reply/{id}", method = RequestMethod.POST)
    public String editReply(@PathVariable("id") Integer reply_id, @RequestParam("reply") String reply_text){
        Comment updated_reply = commentService.updateReplyForComment(reply_id, reply_text);
        return "redirect:/admin/community";
    }

    @RequestMapping(value = "/admin/delete-reply/{id}", method = RequestMethod.GET)
    public String deleteReply(@PathVariable("id") Integer reply_id){
        commentService.deleteReply(reply_id);
        return "redirect:/admin/community";
    }
}
