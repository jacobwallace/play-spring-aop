package io.github.jacobwallace.play_spring_aop.mailbox;

import org.springframework.stereotype.Component;

@Component
public class MailboxRepository {

    private final long DELETED_MAILBOX_ID = 99L;

    public Mailbox findMailbox(Long mailboxId) {
        return new Mailbox() {{
            setId(mailboxId);
            setName("Mailbox " + mailboxId);
            setDeleted(mailboxId == DELETED_MAILBOX_ID);
        }};
    }
}
