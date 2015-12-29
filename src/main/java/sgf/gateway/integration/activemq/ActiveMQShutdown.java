package sgf.gateway.integration.activemq;

import org.apache.activemq.thread.DefaultThreadPools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;

public class ActiveMQShutdown implements DisposableBean {

    private static final Logger LOG = LoggerFactory.getLogger(ActiveMQShutdown.class);

    @Override
    public void destroy() throws Exception {

        // this class shuts down active mq thredds when the spring application context disposes of its resources on shutdown

        LOG.info("Doing amq static shutdown");
        try {
            DefaultThreadPools.getDefaultTaskRunnerFactory().shutdown();
        } finally {
            DefaultThreadPools.shutdown();
        }
    }
} 