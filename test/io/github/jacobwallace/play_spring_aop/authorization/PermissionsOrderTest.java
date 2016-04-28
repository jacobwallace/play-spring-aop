package io.github.jacobwallace.play_spring_aop.authorization;

import lombok.val;
import org.junit.Test;
import play.libs.ws.WS;
import play.test.WithServer;

import static org.fest.assertions.Assertions.assertThat;
import static play.mvc.Http.Status.NOT_FOUND;

public class PermissionsOrderTest extends WithServer {

    private final int PORT = 9090;

    @Override
    protected int providePort() {
        return PORT;
    }

    @Test
    public void shouldEnforceMailboxChecks_BeforeConversationChecks() {
        String result = get("mailboxes/99/conversations/99");
        assertThat(result).isEqualTo("Unknown mailbox with ID = 99.");
    }

    private String get(String endpoint) {
        long timeout = 1000;
        val response = WS.url(String.format("http://localhost:%d/%s", PORT, endpoint)).get().get(timeout);
        assertThat(response.getStatus()).isEqualTo(NOT_FOUND);

        return response.getBody();
    }
}