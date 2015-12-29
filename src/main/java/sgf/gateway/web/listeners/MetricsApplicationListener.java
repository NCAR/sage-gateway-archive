package sgf.gateway.web.listeners;

import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import sgf.gateway.model.metrics.UserLoginType;
import sgf.gateway.model.security.User;
import sgf.gateway.service.messaging.ExceptionHandlingService;
import sgf.gateway.service.messaging.UnhandledException;
import sgf.gateway.service.metrics.UserMetricsService;
import sgf.gateway.service.security.RuntimeUserService;

/**
 * This ApplicationListener is made specificly for listening to Spring Security's AuthenticationSuccessEvent and is used for recording a User's login for
 * Metrics.
 * <p/>
 * To be registered to listen to Spring's ApplicationEvents this class only needs to be defined in a spring context xml file.
 *
 */
public class MetricsApplicationListener implements ApplicationListener<AuthenticationSuccessEvent> {

    /**
     * The user metrics service.
     */
    private UserMetricsService userMetricsService;

    private ExceptionHandlingService errorReportingService;

    private RuntimeUserService runtimeUserService;

    /**
     * Instantiates a new metrics application listener.
     *
     * @param userMetricsService the user metrics service
     */
    public MetricsApplicationListener(UserMetricsService userMetricsService, RuntimeUserService runtimeUserService, ExceptionHandlingService errorReportingService) {

        this.userMetricsService = userMetricsService;
        this.errorReportingService = errorReportingService;
        this.runtimeUserService = runtimeUserService;
    }

    /**
     * {@inheritDoc}
     */
    public void onApplicationEvent(AuthenticationSuccessEvent event) {

        try {

            AuthenticationSuccessEvent authenticationSuccessEvent = event;

            Authentication authentication = authenticationSuccessEvent.getAuthentication();

            User user = runtimeUserService.getUser(authentication);

            this.userMetricsService.storeUserLogin(user, UserLoginType.WEB_APPLICATION_LOGIN);

        } catch (Exception e) {

            errorReportingService.handledException(new UnhandledException(e));
        }
    }
}
