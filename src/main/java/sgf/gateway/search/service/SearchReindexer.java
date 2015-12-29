package sgf.gateway.search.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate4.SessionHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;

public class SearchReindexer {

    private static final Log LOG = LogFactory.getLog(SearchReindexer.class);

    private final SearchService searchService;

    private SessionFactory sessionFactory;

    public SearchReindexer(SearchService searchService) {

        this.searchService = searchService;
    }

    private class SearchReindexerRunnable implements Runnable {

        @Override
        public void run() {

            LOG.warn("Search reindex commencing...");

            Session session = null;

            try {
                session = sessionFactory.openSession();

                SessionHolder sessionHolder = new SessionHolder(session);
                TransactionSynchronizationManager.bindResource(sessionFactory, sessionHolder);

                searchService.reindex();

            } finally {
                if (null != session) {

                    SessionHolder sessionHolder = (SessionHolder) TransactionSynchronizationManager.unbindResource(sessionFactory);
                    session.close();
                }
            }

            LOG.warn("Search reindex complete.");
        }
    }

    public void reindex() {

        SearchReindexerRunnable runnable = new SearchReindexerRunnable();
        Thread thread = new Thread(runnable);

        thread.start();
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

}
