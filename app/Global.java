import io.github.jacobwallace.play_spring_aop.common.authorization.AopPreconditionsConfig;
import io.github.jacobwallace.play_spring_aop.common.authorization.StatusCodeAware;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import play.Application;
import play.GlobalSettings;
import play.libs.F.Promise;
import play.mvc.Http;
import play.mvc.Result;

import static play.libs.F.Promise.promise;
import static play.mvc.Results.internalServerError;

public class Global extends GlobalSettings {

    protected final AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();

    @Override
    public void onStart(Application app) {
        super.onStart(app);

        ctx.register(AopPreconditionsConfig.class);
        ctx.refresh();
        ctx.start();
    }

    @Override
    public <A> A getControllerInstance(Class<A> controllerClass) throws Exception {
        return ctx.getBean(controllerClass);
    }

    @Override
    public void onStop(Application app) {
        ctx.close();
        super.onStop(app);
    }

    @Override
    public Promise<Result> onError(Http.RequestHeader request, Throwable t) {
        Throwable cause = t.getCause();

        if (cause instanceof StatusCodeAware) {
            StatusCodeAware exception = (StatusCodeAware) cause;
            return promise(exception::toStatus);
        }

        return promise(() -> internalServerError(t.getMessage()));
    }
}
