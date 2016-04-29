package io.github.jacobwallace.play_spring_aop.post;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class Post {

    private Long id;

    private String text;

    @JsonIgnore
    private boolean deleted;
}