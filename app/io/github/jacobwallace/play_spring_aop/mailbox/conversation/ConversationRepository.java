package io.github.jacobwallace.play_spring_aop.mailbox.conversation;

import org.springframework.stereotype.Component;

@Component
public class ConversationRepository {

    private final long DELETED_CONVERSATION_ID = 99L;

    public Conversation findConversation(Long mailboxId, Long conversationId) {
        return new Conversation() {{
            setId(conversationId);
            setText(String.format("Sample conversation (ID = %d, mailbox ID = %d)", conversationId, mailboxId));
            setDeleted(conversationId == DELETED_CONVERSATION_ID);
        }};
    }

    public Conversation findLightweightConversation(Long mailboxId, Long conversationId) {
        return new Conversation() {{
            setId(conversationId);
            setText(String.format("Sample lightweight conversation (ID = %d, mailbox ID = %d)", conversationId, mailboxId));
            setDeleted(conversationId == DELETED_CONVERSATION_ID);
        }};
    }

}
