package io.github.jacobwallace.play_spring_aop.mailbox;

import io.github.jacobwallace.play_spring_aop.common.authorization.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.val;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;

import java.util.HashMap;
import java.util.Map;

/**
 * Vetoes access to mailboxes that have been soft deleted.
 */
@Aspect
@AllArgsConstructor
public class MailboxGuardian {

    private final MailboxRepository mailboxRepository;

    @Before("execution(public * play.mvc.Controller+.*(..))")
    public void checkMailboxAccessIfMethodContainsMailboxId(JoinPoint joinPoint) {
        val parameters = parameters(joinPoint);

        if (parameters.containsKey("mailboxId")) {
            val mailboxId = parameters.get("mailboxId");

            if (mailboxId instanceof Long) {
                val mailbox = mailboxRepository.findMailbox((Long) mailboxId);
                vetoAccessIfNecessary(mailbox);
            }
        }
    }

    private void vetoAccessIfNecessary(Mailbox mailbox) {
        if (mailbox.isDeleted()) {
            throw new NotFoundException(String.format("Unknown mailbox with ID %d.", mailbox.getId()));
        }

        // add other checks here...
    }

    private Map<String, Object> parameters(JoinPoint jp) {
        String[] paramNames = parameterNames(jp);
        Object[] args = jp.getArgs();

        Map<String, Object> parameters = new HashMap<>();

        for (int i = 0; i < paramNames.length; i++) {
            parameters.put(paramNames[i], args[i]);
        }

        return parameters;
    }

    private String[] parameterNames(JoinPoint jp) {
        MethodSignature signature = (MethodSignature) jp.getSignature();
        return signature.getParameterNames();
    }
}