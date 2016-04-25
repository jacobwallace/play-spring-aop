package io.github.jacobwallace.play_spring_aop.mailbox.conversation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Conversation {

    private Long id;
    private String text;
}