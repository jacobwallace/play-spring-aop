package io.github.jacobwallace.play_spring_aop.post.comment;

import org.springframework.stereotype.Component;

@Component
public class CommentRepository {

    private final long DELETED_COMMENT_ID = 99L;

    public Comment findComment(Long postId, Long commentId) {
        return new Comment() {{
            setId(commentId);
            setText(String.format("Sample comment (ID = %d, post ID = %d)", commentId, postId));
            setDeleted(commentId == DELETED_COMMENT_ID);
        }};
    }

    public Comment findLightweightComment(Long postId, Long commentId) {
        return new Comment() {{
            setId(commentId);
            setText(String.format("Sample lightweight comment (ID = %d, post ID = %d)", commentId, postId));
            setDeleted(commentId == DELETED_COMMENT_ID);
        }};
    }

}
