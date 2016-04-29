package io.github.jacobwallace.play_spring_aop.authorization;

import lombok.val;
import play.libs.Json;
import play.mvc.Results;

import static play.mvc.Results.notFound;

public class NotFoundException extends RuntimeException implements StatusCodeAware {

    public NotFoundException(String message) {
        super(message);
    }

    @Override
    public Results.Status toStatus() {
        val json = Json.newObject().put("message", getMessage());
        return notFound(json);
    }
}
