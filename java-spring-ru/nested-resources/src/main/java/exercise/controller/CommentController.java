package exercise.controller;

import exercise.model.Comment;
import exercise.repository.CommentRepository;
import exercise.model.Post;
import exercise.repository.PostRepository;
import exercise.ResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;


@RestController
@RequestMapping("/posts")
public class CommentController {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @GetMapping(path = "/{postId}/comments")
    public Iterable<Comment> getCommentByPostId(@PathVariable("postId") long id) {
        return this.commentRepository.findAllByPostId(id);
    }

    @GetMapping(path = "/{postId}/comments/{commentId}")
    public Comment getCommentByPostIdAndId(@PathVariable("postId") long postId, @PathVariable("commentId") long id) {
        return commentRepository.findByIdAndPostId(id, postId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found"));
    }

    @PostMapping(path = "/{postId}/comments")
    public Iterable<Comment> createCommentInPost(@PathVariable("postId") long postId, @RequestBody Comment comment) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found"));
        comment.setPost(post);
        commentRepository.save(comment);

        return commentRepository.findAllByPostId(postId);
    }

    @PatchMapping(path = "/{postId}/comments/{commentId}")
    public void patchComment(@PathVariable("postId") long postId, @PathVariable("commentId") long id, @RequestBody Comment comment) {
        Comment existComment = commentRepository.findByIdAndPostId(id, postId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found"));
        existComment.setContent(comment.getContent());

        commentRepository.save(existComment);
    }

    @DeleteMapping(path = "/{postId}/comments/{commentId}")
    public void deleteComment(@PathVariable("postId") long postId, @PathVariable("commentId") long id) {
        Comment existComment = commentRepository.findByIdAndPostId(id, postId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found"));
        commentRepository.delete(existComment);
    }
}
