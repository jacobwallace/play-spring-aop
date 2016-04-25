package io.github.jacobwallace.play_spring_aop.mailbox;

import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import play.libs.F.Promise;
import play.mvc.Controller;
import play.mvc.Result;

import static play.libs.F.Promise.pure;
import static play.libs.Json.toJson;

@Component
public class MailboxController extends Controller {

    private final MailboxRepository mailboxRepository;

    @Autowired
    public MailboxController(MailboxRepository mailboxRepository) {
        this.mailboxRepository = mailboxRepository;
    }

    public Promise<Result> getMailbox(Long mailboxId) {
        val mailbox = mailboxRepository.findMailbox(mailboxId);
        return pure(ok(toJson(mailbox)));
    }
}