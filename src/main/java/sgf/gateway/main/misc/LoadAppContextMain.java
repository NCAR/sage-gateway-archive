package sgf.gateway.main.misc;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author jcunning
 */
public class LoadAppContextMain {

    private final ApplicationContext applicationContext;

    public static void main(String[] args) throws Exception {
        //LoadAppContextMain main = new LoadAppContextMain();
    }

    public LoadAppContextMain() {
        this.applicationContext = new ClassPathXmlApplicationContext("classpath:sgf/gateway/application-context-main.xml");
    }
}
