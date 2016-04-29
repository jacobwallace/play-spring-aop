package io.github.jacobwallace.play_spring_aop.post;

import lombok.val;
import org.springframework.stereotype.Component;
import play.libs.F.Promise;
import play.mvc.Controller;
import play.mvc.Result;

import static play.libs.F.Promise.pure;
import static play.libs.Json.toJson;

@Component
public class PostController extends Controller {

    public Promise<Result> getPost(Long postId) {
        val post = (Post) ctx().args.get("post");
        return pure(ok(toJson(post)));
    }
}