package io.github.jacobwallace.play_spring_aop.mailbox.conversation;

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
        val conversation = new Conversation();
        conversation.setId(conversationId);
        conversation.setText(String.format("This is a sample conversation in mailbox %d.", mailboxId));

        return pure(ok(toJson(conversation)));
    }
}