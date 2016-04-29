package io.github.jacobwallace.play_spring_aop.authorization;

public interface StatusCodeAware {

    play.mvc.Results.Status toStatus();

}