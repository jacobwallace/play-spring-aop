package io.github.jacobwallace.play_spring_aop.post.comment;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.jacobwallace.play_spring_aop.common.FunctionalTest;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class CommentControllerTest extends FunctionalTest {

    @Test
    public void shouldGetComment() {
        JsonNode response = get("posts/1/comments/2");

        assertThat(response.get("id").asLong()).isEqualTo(2);
        assertThat(response.get("text").asText()).isEqualTo("Sample comment (ID = 2, post ID = 1)");
    }

    @Test
    public void shouldGetLightweightComment() {
        JsonNode response = get("posts/1/comments/2/lightweight");

        assertThat(response.get("id").asLong()).isEqualTo(2);
        assertThat(response.get("text").asText()).isEqualTo("Sample lightweight comment (ID = 2, post ID = 1)");
    }
}