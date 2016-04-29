package io.github.jacobwallace.play_spring_aop.post.comment;

import io.github.jacobwallace.play_spring_aop.authorization.NotFoundException;
import io.github.jacobwallace.play_spring_aop.authorization.UrlParameterGuardian;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.core.annotation.Order;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;

/**
 * Vetoes access to comments that have been soft deleted.
 */
@Order(2)
@AllArgsConstructor
public class CommentGuardian extends UrlParameterGuardian<Comment> {

    private final CommentRepository commentRepository;

    @Override
    protected String[] getTargetedParameters() {
        return new String[] { "postId", "commentId" };
    }

    @Override
    protected String shortName() {
        return "comment";
    }

    @Override
    protected Comment findObject(Map<String, Object> parameters, List<Annotation> annotations) {
        val postId = (Long) parameters.get("postId");
        val commentId = (Long) parameters.get("commentId");

        // TODO: In a real app this should not be hard-coded. Inject a strategy for fetching a comment instead.
        if (annotations.stream().anyMatch(a -> a.annotationType().equals(RequiresLightweightComment.class))) {
            return commentRepository.findLightweightComment(postId, commentId);
        }

        return commentRepository.findComment(postId, commentId);
    }

    @Override
    protected void vetoAccessIfNecessary(Comment comment) {
        // Comment-specific checks...

        if (comment.isDeleted()) {
            throw new NotFoundException(String.format("Unknown comment with ID = %d.", comment.getId()));
        }
    }
}