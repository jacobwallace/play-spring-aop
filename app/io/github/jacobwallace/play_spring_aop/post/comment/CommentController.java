package io.github.jacobwallace.play_spring_aop.post.comment;

import lombok.val;
import org.springframework.stereotype.Component;
import play.libs.F.Promise;
import play.mvc.Controller;
import play.mvc.Result;

import static play.libs.F.Promise.pure;
import static play.libs.Json.toJson;

@Component
public class CommentController extends Controller {

    public Promise<Result> getComment(Long postId, Long commentId) {
        return getCommentImpl(postId, commentId);
    }

    public Promise<Result> getCommentWithReversedArgs(Long commentId, Long postId) {
        return getCommentImpl(postId, commentId);
    }

    private Promise<Result> getCommentImpl(Long postId, Long commentId) {
        val comment = (Comment) ctx().args.get("comment");
        return pure(ok(toJson(comment)));
    }

    @RequiresLightweightComment
    public Promise<Result> getLightweightComment(Long postId, Long commentId) {
        val comment = (Comment) ctx().args.get("comment");
        return pure(ok(toJson(comment)));
    }
}