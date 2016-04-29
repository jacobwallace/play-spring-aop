package io.github.jacobwallace.play_spring_aop.common;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.val;
import play.libs.Json;
import play.libs.ws.WS;
import play.test.WithServer;

import static org.fest.assertions.Assertions.assertThat;
import static play.mvc.Http.Status.OK;

public abstract class FunctionalTest extends WithServer {

    protected final int PORT = 9090;

    @Override
    protected int providePort() {
        return PORT;
    }

    protected int getting(String endpoint) {
        long timeout = 1000;
        return WS.url(String.format("http://localhost:%d/%s", PORT, endpoint)).get().get(timeout).getStatus();
    }

    protected JsonNode get(String endpoint) {
        return get(endpoint, OK);
    }

    protected JsonNode get(String endpoint, int expectedStatus) {
        long timeout = 1000;
        val response = WS.url(String.format("http://localhost:%d/%s", PORT, endpoint)).get().get(timeout);
        assertThat(response.getStatus()).isEqualTo(expectedStatus);

        return Json.parse(response.getBody());
    }

}
