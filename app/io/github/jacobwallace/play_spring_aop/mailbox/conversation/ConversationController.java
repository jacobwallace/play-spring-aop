package io.github.jacobwallace.play_spring_aop.mailbox.conversation;

import io.github.jacobwallace.play_spring_aop.authorization.mailbox.conversation.RequiresLightweightConversation;
import lombok.val;
import org.springframework.stereotype.Component;
import play.libs.F.Promise;
import play.mvc.Controller;
import play.mvc.Result;

import static play.libs.F.Promise.pure;
import static play.libs.Json.toJson;

@Component
public class ConversationController extends Controller {

    public Promise<Result> getConversation(Long mailboxId, Long conversationId) {
        return getConversationImpl(mailboxId, conversationId);
    }

    public Promise<Result> getConversationWithReversedArgs(Long conversationId, Long mailboxId) {
        return getConversationImpl(mailboxId, conversationId);
    }

    private Promise<Result> getConversationImpl(Long mailboxId, Long conversationId) {
        val conversation = (Conversation) ctx().args.get("conversation");
        return pure(ok(toJson(conversation)));
    }

    @RequiresLightweightConversation
    public Promise<Result> getLightweightConversation(Long mailboxId, Long conversationId) {
        val conversation = (Conversation) ctx().args.get("conversation");
        return pure(ok(toJson(conversation)));
    }
}