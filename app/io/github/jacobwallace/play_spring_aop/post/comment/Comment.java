package io.github.jacobwallace.play_spring_aop.post.comment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

    private Long id;

    private String text;

    @JsonIgnore
    private boolean deleted;
}