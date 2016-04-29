package io.github.jacobwallace.play_spring_aop.authorization;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.jacobwallace.play_spring_aop.common.FunctionalTest;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;
import static play.mvc.Http.Status.NOT_FOUND;

public class PermissionsOrderTest extends FunctionalTest {

    @Test
    public void shouldEnforcePostChecks_BeforeCommentChecks() {
        JsonNode result = get("posts/99/comments/99", NOT_FOUND);
        assertThat(result.get("message").asText()).isEqualTo("Unknown post with ID = 99.");
    }
}