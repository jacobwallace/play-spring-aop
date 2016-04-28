package io.github.jacobwallace.play_spring_aop.authorization.mailbox;

import io.github.jacobwallace.play_spring_aop.common.authorization.NotFoundException;
import io.github.jacobwallace.play_spring_aop.common.authorization.UrlParameterGuardian;
import io.github.jacobwallace.play_spring_aop.mailbox.Mailbox;
import io.github.jacobwallace.play_spring_aop.mailbox.MailboxRepository;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.core.annotation.Order;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;

/**
 * Vetoes access to mailboxes that have been soft deleted.
 */
@Order(1)
@AllArgsConstructor
public class MailboxGuardian extends UrlParameterGuardian<Mailbox> {

    private final MailboxRepository mailboxRepository;

    @Override
    protected String[] getTargetedParameters() {
        return new String[] { "mailboxId" };
    }

    @Override
    protected String shortName() {
        return "mailbox";
    }

    @Override
    protected Mailbox findObject(Map<String, Object> parameters, List<Annotation> annotations) {
        val mailboxId = (Long) parameters.get("mailboxId");
        return mailboxRepository.findMailbox(mailboxId);
    }

    @Override
    protected void vetoAccessIfNecessary(Mailbox mailbox) {
        // Mailbox-specific checks...

        if (mailbox.isDeleted()) {
            throw new NotFoundException(String.format("Unknown mailbox with ID = %d.", mailbox.getId()));
        }
    }
}