package io.github.jacobwallace.play_spring_aop.mailbox;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class Mailbox {

    private Long id;

    private String name;

    @JsonIgnore
    private boolean deleted;
}