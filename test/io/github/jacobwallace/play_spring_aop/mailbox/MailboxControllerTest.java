package io.github.jacobwallace.play_spring_aop.mailbox;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.val;
import org.junit.Test;
import play.libs.Json;
import play.libs.ws.WS;
import play.test.WithServer;

import static org.fest.assertions.Assertions.assertThat;
import static play.mvc.Http.Status.OK;

public class MailboxControllerTest extends WithServer {

    private final int PORT = 9090;

    @Override
    protected int providePort() {
        return PORT;
    }

    @Test
    public void shouldGetMailbox() {
        JsonNode response = get("mailboxes/1");

        assertThat(response.get("id").asLong()).isEqualTo(1);
        assertThat(response.get("name").asText()).isEqualTo("Mailbox 1");
    }

    private JsonNode get(String endpoint) {
        long timeout = 1000;
        val response = WS.url(String.format("http://localhost:%d/%s", PORT, endpoint)).get().get(timeout);
        assertThat(response.getStatus()).isEqualTo(OK);

        return Json.parse(response.getBody());
    }
}