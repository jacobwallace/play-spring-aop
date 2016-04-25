package io.github.jacobwallace.play_spring_aop.common.authorization;

import play.mvc.Results;

import static play.mvc.Results.notFound;

public class NotFoundException extends RuntimeException implements StatusCodeAware {

    public NotFoundException(String message) {
        super(message);
    }

    @Override
    public Results.Status toStatus() {
        return notFound(getMessage());
    }
}
