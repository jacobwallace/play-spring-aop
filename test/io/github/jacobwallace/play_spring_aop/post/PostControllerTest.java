package io.github.jacobwallace.play_spring_aop.post;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.jacobwallace.play_spring_aop.common.FunctionalTest;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class PostControllerTest extends FunctionalTest {

    @Test
    public void shouldGetPost() {
        JsonNode response = get("posts/1");

        assertThat(response.get("id").asLong()).isEqualTo(1);
        assertThat(response.get("text").asText()).isEqualTo("Post 1");
    }
}