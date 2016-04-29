package io.github.jacobwallace.play_spring_aop.post;

import io.github.jacobwallace.play_spring_aop.authorization.NotFoundException;
import io.github.jacobwallace.play_spring_aop.authorization.UrlParameterGuardian;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.core.annotation.Order;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;

/**
 * Vetoes access to posts that have been soft deleted.
 */
@Order(1)
@AllArgsConstructor
public class PostGuardian extends UrlParameterGuardian<Post> {

    private final PostRepository postRepository;

    @Override
    protected String[] getTargetedParameters() {
        return new String[] { "postId" };
    }

    @Override
    protected String shortName() {
        return "post";
    }

    @Override
    protected Post findObject(Map<String, Object> parameters, List<Annotation> annotations) {
        val postId = (Long) parameters.get("postId");
        return postRepository.findPost(postId);
    }

    @Override
    protected void vetoAccessIfNecessary(Post post) {
        // Post-specific checks...

        if (post.isDeleted()) {
            throw new NotFoundException(String.format("Unknown post with ID = %d.", post.getId()));
        }
    }
}