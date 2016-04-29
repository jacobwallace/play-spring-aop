package io.github.jacobwallace.play_spring_aop.post.comment;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * A hint that an aspect should load a "lightweight" version of a comment.
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequiresLightweightComment {
}