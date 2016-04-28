package io.github.jacobwallace.play_spring_aop.authorization;

import org.junit.Test;
import play.libs.ws.WS;
import play.test.WithServer;

import static org.fest.assertions.Assertions.assertThat;
import static play.mvc.Http.Status.NOT_FOUND;
import static play.mvc.Http.Status.OK;

/**
 * Performs spot checks of conversation permissions across a few endpoints (would not exhaustively check each endpoint).
 */
public class ConversationPermissionsTest extends WithServer {

    private final int PORT = 9090;

    @Override
    protected int providePort() {
        return PORT;
    }

    @Test
    public void shouldEnforceConversationChecks() {
        assertThat(getting("mailboxes/1/conversations/1")).isEqualTo(OK);
        assertThat(getting("mailboxes/1/conversations/99")).isEqualTo(NOT_FOUND);
    }

    @Test
    public void shouldEnforceConversationChecks_OnConversationEndpoint_WithReversedArguments() {
        assertThat(getting("mailboxes/1/conversations/1/reversed")).isEqualTo(OK);
        assertThat(getting("mailboxes/1/conversations/99/reversed")).isEqualTo(NOT_FOUND);
    }

    private int getting(String endpoint) {
        long timeout = 1000;
        return WS.url(String.format("http://localhost:%d/%s", PORT, endpoint)).get().get(timeout).getStatus();
    }
}