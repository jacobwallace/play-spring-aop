package io.github.jacobwallace.play_spring_aop.common.authorization;

import io.github.jacobwallace.play_spring_aop.authorization.mailbox.conversation.ConversationGuardian;
import io.github.jacobwallace.play_spring_aop.authorization.mailbox.MailboxGuardian;
import io.github.jacobwallace.play_spring_aop.mailbox.MailboxRepository;
import io.github.jacobwallace.play_spring_aop.mailbox.conversation.ConversationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan(basePackages = {"io.github.jacobwallace.play_spring_aop"})
@EnableAspectJAutoProxy
public class AopPreconditionsConfig {

    @Autowired
    private MailboxRepository mailboxRepository;

    @Autowired
    private ConversationRepository conversationRepository;

    @Bean
    public ConversationGuardian conversationAccessGuardian() {
        return new ConversationGuardian(conversationRepository);
    }

    @Bean
    public MailboxGuardian mailboxAccessGuardian() {
        return new MailboxGuardian(mailboxRepository);
    }
}