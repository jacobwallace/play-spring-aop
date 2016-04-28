package io.github.jacobwallace.play_spring_aop.authorization.mailbox.conversation;

import io.github.jacobwallace.play_spring_aop.common.authorization.NotFoundException;
import io.github.jacobwallace.play_spring_aop.common.authorization.UrlParameterGuardian;
import io.github.jacobwallace.play_spring_aop.mailbox.conversation.Conversation;
import io.github.jacobwallace.play_spring_aop.mailbox.conversation.ConversationRepository;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.core.annotation.Order;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;

/**
 * Vetoes access to conversations that have been soft deleted.
 */
@Order(2)
@AllArgsConstructor
public class ConversationGuardian extends UrlParameterGuardian<Conversation> {

    private final ConversationRepository conversationRepository;

    @Override
    protected String[] getTargetedParameters() {
        return new String[] { "mailboxId", "conversationId" };
    }

    @Override
    protected String shortName() {
        return "conversation";
    }

    @Override
    protected Conversation findObject(Map<String, Object> parameters, List<Annotation> annotations) {
        val mailboxId = (Long) parameters.get("mailboxId");
        val conversationId = (Long) parameters.get("conversationId");

        // TODO: In a real app this should not be hard-coded. Inject a strategy for fetching a conversation instead.
        if (annotations.stream().anyMatch(a -> a.annotationType().equals(RequiresLightweightConversation.class))) {
            return conversationRepository.findLightweightConversation(mailboxId, conversationId);
        }

        return conversationRepository.findConversation(mailboxId, conversationId);
    }

    @Override
    protected void vetoAccessIfNecessary(Conversation conversation) {
        // Conversation-specific checks...

        if (conversation.isDeleted()) {
            throw new NotFoundException(String.format("Unknown conversation with ID = %d.", conversation.getId()));
        }
    }
}