package io.github.jacobwallace.play_spring_aop.authorization;

import lombok.SneakyThrows;
import lombok.val;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static play.mvc.Controller.ctx;

/**
 * Vetoes access to resources based on URL parameters.
 */
@Aspect
public abstract class UrlParameterGuardian<T> {

    @Before("execution(public * play.mvc.Controller+.*(..))")
    public void checkAccess(JoinPoint joinPoint) {
        val parameters = parameters(joinPoint);
        val annotations = methodAnnotations(joinPoint);

        if (Arrays.stream(getTargetedParameters()).allMatch(parameters::containsKey)) {
            T object = findObject(parameters, annotations);
            vetoAccessIfNecessary(object);

            ctx().args.put(shortName(), object);
        }
    }

    protected abstract String[] getTargetedParameters();

    protected abstract String shortName();

    protected abstract T findObject(Map<String, Object> parameters, List<Annotation> annotations);

    protected abstract void vetoAccessIfNecessary(T object);

    private Map<String, Object> parameters(JoinPoint jp) {
        String[] paramNames = parameterNames(jp);
        Object[] args = jp.getArgs();

        Map<String, Object> parameters = new HashMap<>();

        for (int i = 0; i < paramNames.length; i++) {
            parameters.put(paramNames[i], args[i]);
        }

        return parameters;
    }

    @SneakyThrows
    private List<Annotation> methodAnnotations(JoinPoint jp) {
        MethodSignature signature = (MethodSignature) jp.getSignature();
        String methodName = signature.getMethod().getName();
        Class<?>[] parameterTypes = signature.getMethod().getParameterTypes();
        Annotation[] annotations = jp.getTarget().getClass().getMethod(methodName, parameterTypes).getDeclaredAnnotations();
        return Arrays.asList(annotations);
    }

    private String[] parameterNames(JoinPoint jp) {
        MethodSignature signature = (MethodSignature) jp.getSignature();
        return signature.getParameterNames();
    }
}