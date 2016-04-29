package io.github.jacobwallace.play_spring_aop.post;

import io.github.jacobwallace.play_spring_aop.common.FunctionalTest;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;
import static play.mvc.Http.Status.NOT_FOUND;
import static play.mvc.Http.Status.OK;

/**
 * Performs spot checks of post permissions across a few endpoints (would not exhaustively check each endpoint).
 */
public class PostPermissionsTest extends FunctionalTest {

    @Test
    public void shouldEnforcePostChecks_OnPostsEndpoint() {
        assertThat(getting("posts/1")).isEqualTo(OK);
        assertThat(getting("posts/99")).isEqualTo(NOT_FOUND);
    }

    @Test
    public void shouldEnforcePostChecks_OnCommentsEndpoint() {
        assertThat(getting("posts/1/comments/1")).isEqualTo(OK);
        assertThat(getting("posts/99/comments/1")).isEqualTo(NOT_FOUND);
    }

    @Test
    public void shouldEnforcePostChecks_OnCommentsEndpoint_WithReversedArguments() {
        assertThat(getting("posts/1/comments/1/reversed")).isEqualTo(OK);
        assertThat(getting("posts/99/comments/1/reversed")).isEqualTo(NOT_FOUND);
    }
}