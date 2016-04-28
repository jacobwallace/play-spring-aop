package io.github.jacobwallace.play_spring_aop.authorization.mailbox.conversation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * A hint that an aspect should load a "lightweight" version of a conversation.
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequiresLightweightConversation {
}