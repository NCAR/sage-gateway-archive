package sgf.gateway.validation.factory;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.validation.beanvalidation.SpringConstraintValidatorFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorFactory;

public class SpringAwareConstraintValidatorFactoryImpl implements ConstraintValidatorFactory, ApplicationContextAware, InitializingBean {

    private SpringConstraintValidatorFactory springConstraintValidatorFacotry;

    private ApplicationContext applicationContext;

    public final <T extends ConstraintValidator<?, ?>> T getInstance(Class<T> key) {

        T constraintValidator;

        try {

            constraintValidator = this.applicationContext.getBean(key);

        } catch (BeansException e) {

            constraintValidator = this.springConstraintValidatorFacotry.getInstance(key);
        }

        return constraintValidator;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

        this.applicationContext = applicationContext;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

        this.springConstraintValidatorFacotry = new SpringConstraintValidatorFactory(this.applicationContext.getAutowireCapableBeanFactory());
    }

    @Override
    public void releaseInstance(ConstraintValidator<?, ?> arg0) {
        // TODO Auto-generated method stub

    }
}
