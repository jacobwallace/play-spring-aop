package io.github.jacobwallace.play_spring_aop.conversation;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.val;
import org.junit.Test;
import play.libs.Json;
import play.libs.ws.WS;
import play.test.WithServer;

import static org.fest.assertions.Assertions.assertThat;
import static play.mvc.Http.Status.OK;

public class ConversationControllerTest extends WithServer {

    private final int PORT = 9090;

    @Override
    protected int providePort() {
        return PORT;
    }

    @Test
    public void shouldGetConversation() {
        JsonNode response = get("mailboxes/1/conversations/2");

        assertThat(response.get("id").asLong()).isEqualTo(2);
        assertThat(response.get("text").asText()).isEqualTo("Sample conversation (ID = 2, mailbox ID = 1)");
    }

    @Test
    public void shouldGetLightweightConversation() {
        JsonNode response = get("mailboxes/1/conversations/2/lightweight");

        assertThat(response.get("id").asLong()).isEqualTo(2);
        assertThat(response.get("text").asText()).isEqualTo("Sample lightweight conversation (ID = 2, mailbox ID = 1)");
    }

    private JsonNode get(String endpoint) {
        long timeout = 1000;
        val response = WS.url(String.format("http://localhost:%d/%s", PORT, endpoint)).get().get(timeout);
        assertThat(response.getStatus()).isEqualTo(OK);

        return Json.parse(response.getBody());
    }
}