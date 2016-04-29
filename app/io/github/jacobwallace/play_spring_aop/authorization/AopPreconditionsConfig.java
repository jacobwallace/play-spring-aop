package io.github.jacobwallace.play_spring_aop.authorization;

import io.github.jacobwallace.play_spring_aop.post.PostGuardian;
import io.github.jacobwallace.play_spring_aop.post.PostRepository;
import io.github.jacobwallace.play_spring_aop.post.comment.CommentGuardian;
import io.github.jacobwallace.play_spring_aop.post.comment.CommentRepository;
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
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Bean
    public CommentGuardian commentGuardian() {
        return new CommentGuardian(commentRepository);
    }

    @Bean
    public PostGuardian postGuardian() {
        return new PostGuardian(postRepository);
    }
}