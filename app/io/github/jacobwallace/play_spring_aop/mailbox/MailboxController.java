package io.github.jacobwallace.play_spring_aop.mailbox;

import lombok.val;
import org.springframework.stereotype.Component;
import play.libs.F.Promise;
import play.mvc.Controller;
import play.mvc.Result;

import static play.libs.F.Promise.pure;
import static play.libs.Json.toJson;

@Component
public class MailboxController extends Controller {

    public Promise<Result> getMailbox(Long mailboxId) { // TODO: Not used, but document why.
        val mailbox = (Mailbox) ctx().args.get("mailbox");
        return pure(ok(toJson(mailbox)));
    }
}