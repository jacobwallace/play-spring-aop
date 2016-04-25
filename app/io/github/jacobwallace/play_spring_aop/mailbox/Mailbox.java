package io.github.jacobwallace.play_spring_aop.mailbox;

import lombok.Data;

@Data
public class Mailbox {

    private Long id;
    private String name;
    private boolean deleted;
}