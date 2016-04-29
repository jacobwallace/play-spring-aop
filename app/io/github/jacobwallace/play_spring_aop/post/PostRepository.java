package io.github.jacobwallace.play_spring_aop.post;

import org.springframework.stereotype.Component;

@Component
public class PostRepository {

    private final long DELETED_POST_ID = 99L;

    public Post findPost(Long postId) {
        return new Post() {{
            setId(postId);
            setText("Post " + postId);
            setDeleted(postId == DELETED_POST_ID);
        }};
    }
}
