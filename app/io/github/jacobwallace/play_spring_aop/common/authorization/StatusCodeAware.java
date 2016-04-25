package io.github.jacobwallace.play_spring_aop.common.authorization;

public interface StatusCodeAware {

    play.mvc.Results.Status toStatus();

}