package sgf.gateway.dao.database.install;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.exception.ValidationFailedException;
import liquibase.integration.spring.SpringLiquibase;
import liquibase.resource.ResourceAccessor;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import sgf.liquibase.extensions.PostgresDatabaseExt;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LiquibaseBootStrapperImpl implements BeanNameAware, ResourceLoaderAware {

    public static final String MAJOR_VERSION_FAILURE_MESSAGE = "Major Version Precondition Not Met";

    public class SpringResourceOpener implements ResourceAccessor {

        private String parentFile;

        public SpringResourceOpener(String parentFile) {

            this.parentFile = parentFile;
        }


        public InputStream getResourceAsStream(String file) throws IOException {

            Resource resource = getResource(file);

            return resource.getInputStream();
        }

        public Enumeration<URL> getResources(String packageName) throws IOException {

            Vector<URL> tmp = new Vector<URL>();

            tmp.add(getResource(packageName).getURL());

            return tmp.elements();
        }

        public Resource getResource(String file) {

            return getResourceLoader().getResource(adjustClasspath(file));
        }

        private String adjustClasspath(String file) {

            return isClasspathPrefixPresent(parentFile) && !isClasspathPrefixPresent(file)
                    ? ResourceLoader.CLASSPATH_URL_PREFIX + file
                    : file;
        }

        public boolean isClasspathPrefixPresent(String file) {

            return file.startsWith(ResourceLoader.CLASSPATH_URL_PREFIX);
        }

        public ClassLoader toClassLoader() {

            return getResourceLoader().getClassLoader();
        }
    }

    private String beanName;

    private ResourceLoader resourceLoader;

    private DataSource dataSource;

    private Logger log = Logger.getLogger(SpringLiquibase.class.getName());

    private String changeLog;

    private String contexts;

    private NewDatabaseDetectionStrategy newDatabaseDetectionStrategy = null;

    private String installChangeLog;

    private String roleAdmin;
    private String roleUser;
    private String roleReadOnly;

    public String getDatabaseProductName() throws LiquibaseException {

        Connection connection = null;

        String name = "unknown";

        try {

            connection = getDataSource().getConnection();
            JdbcConnection jdbcConnection = new JdbcConnection(connection);

            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(jdbcConnection);
            name = database.getDatabaseProductName();

        } catch (SQLException e) {

            try {
                connection.rollback();
            } catch (Exception exc) {
                log.log(Level.WARNING, "problem rolling back connection", exc);
            }

            throw new LiquibaseException(e);

        } finally {

            try {
                connection.close();
            } catch (Exception exc) {
                log.log(Level.WARNING, "problem closing connection", exc);
            }

        }

        return name;
    }

    /**
     * The DataSource that liquibase will use to perform the migration.
     *
     * @return
     */
    public DataSource getDataSource() {
        return dataSource;
    }

    /**
     * The DataSource that liquibase will use to perform the migration.
     */
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Returns a Resource that is able to resolve to a file or classpath resource.
     *
     * @return
     */
    public String getChangeLog() {
        return changeLog;
    }

    /**
     * Sets a Spring Resource that is able to resolve to a file or classpath resource.
     * An example might be <code>classpath:db-changelog.xml</code>.
     */
    public void setChangeLog(String dataModel) {

        this.changeLog = dataModel;
    }

    public String getContexts() {
        return contexts;
    }

    public void setContexts(String contexts) {
        this.contexts = contexts;
    }

    public void initializeDatabase() {

        String shouldRunProperty = System.getProperty(Liquibase.SHOULD_RUN_SYSTEM_PROPERTY);

        if (shouldRunProperty != null && !Boolean.valueOf(shouldRunProperty)) {

            System.out.println("LiquiBase did not run because '"
                    + Liquibase.SHOULD_RUN_SYSTEM_PROPERTY
                    + "' system property was set to false");

            return;
        }

        DatabaseFactory.getInstance().register(new PostgresDatabaseExt());

        this.installDatabase();
        this.updateDatabaseV1();
        this.updateDatabaseV2();
        this.updateDatabaseV3();
        this.updateDatabaseV4();
    }


    public boolean shouldRunInstallation() {
        return this.newDatabaseDetectionStrategy.isNewDatabase();
    }

    public void installDatabase() {

        if (this.shouldRunInstallation()) {

            Connection connection = null;

            try {

                connection = getDataSource().getConnection();
                JdbcConnection jdbcConnection = new JdbcConnection(connection);

                Liquibase liquibase = createLiquibase(installChangeLog, DatabaseFactory.getInstance().findCorrectDatabaseImplementation(jdbcConnection));
                liquibase.setChangeLogParameter("databse.role.admin", this.roleAdmin);
                liquibase.setChangeLogParameter("databse.role.user", this.roleUser);
                liquibase.setChangeLogParameter("databse.role.read_only", this.roleReadOnly);
                liquibase.update(getContexts());

            } catch (Exception e) {

                try {
                    connection.rollback();
                } catch (Exception exc) {
                    log.log(Level.WARNING, "problem rolling back connection", exc);
                }

                throw new RuntimeException(e);

            } finally {

                try {
                    connection.close();
                } catch (Exception exc) {
                    log.log(Level.WARNING, "problem closing connection", exc);
                }

            }
        }


    }

    public void updateDatabaseV1() {

        Connection connection = null;

        try {

            connection = getDataSource().getConnection();
            JdbcConnection jdbcConnection = new JdbcConnection(connection);

            Liquibase liquibase = createLiquibase("classpath:liquibase/update-v1/changelog.xml", DatabaseFactory.getInstance().findCorrectDatabaseImplementation(jdbcConnection));
            liquibase.setChangeLogParameter("database.role.admin", this.roleAdmin);
            liquibase.setChangeLogParameter("database.role.user", this.roleUser);
            liquibase.setChangeLogParameter("database.role.read_only", this.roleReadOnly);
            liquibase.update(getContexts());

        } catch (ValidationFailedException e) {

            String message = e.getMessage();

            if (!message.contains(MAJOR_VERSION_FAILURE_MESSAGE)) {
                throw new RuntimeException(e);
            }

        } catch (Exception e) {

            try {
                connection.rollback();
            } catch (Exception exc) {
                log.log(Level.WARNING, "problem rolling back connection", exc);
            }

            throw new RuntimeException(e);

        } finally {

            try {
                connection.close();
            } catch (Exception exc) {
                log.log(Level.WARNING, "problem closing connection", exc);
            }

        }
    }

    public void updateDatabaseV2() {

        Connection connection = null;

        try {

            connection = getDataSource().getConnection();
            JdbcConnection jdbcConnection = new JdbcConnection(connection);

            Liquibase liquibase = createLiquibase("classpath:liquibase/update-v2/changelog.xml", DatabaseFactory.getInstance().findCorrectDatabaseImplementation(jdbcConnection));
            liquibase.setChangeLogParameter("database.role.admin", this.roleAdmin);
            liquibase.setChangeLogParameter("database.role.user", this.roleUser);
            liquibase.setChangeLogParameter("database.role.read_only", this.roleReadOnly);
            liquibase.update(getContexts());

        } catch (ValidationFailedException e) {

            String message = e.getMessage();

            if (!message.contains(MAJOR_VERSION_FAILURE_MESSAGE)) {
                throw new RuntimeException(e);
            }

        } catch (Exception e) {

            try {
                connection.rollback();
            } catch (Exception exc) {
                log.log(Level.WARNING, "problem rolling back connection", exc);
            }

            throw new RuntimeException(e);

        } finally {

            try {
                connection.close();
            } catch (Exception exc) {
                log.log(Level.WARNING, "problem closing connection", exc);
            }

        }
    }

    public void updateDatabaseV3() {

        Connection connection = null;

        try {

            connection = getDataSource().getConnection();
            JdbcConnection jdbcConnection = new JdbcConnection(connection);

            Liquibase liquibase = createLiquibase("classpath:liquibase/update-v3/changelog.xml", DatabaseFactory.getInstance().findCorrectDatabaseImplementation(jdbcConnection));
            liquibase.setChangeLogParameter("database.role.admin", this.roleAdmin);
            liquibase.setChangeLogParameter("database.role.user", this.roleUser);
            liquibase.setChangeLogParameter("database.role.read_only", this.roleReadOnly);
            liquibase.update(getContexts());

        } catch (ValidationFailedException e) {

            String message = e.getMessage();

            if (!message.contains(MAJOR_VERSION_FAILURE_MESSAGE)) {
                throw new RuntimeException(e);
            }

        } catch (Exception e) {

            try {
                connection.rollback();
            } catch (Exception exc) {
                log.log(Level.WARNING, "problem rolling back connection", exc);
            }

            throw new RuntimeException(e);

        } finally {

            try {
                connection.close();
            } catch (Exception exc) {
                log.log(Level.WARNING, "problem closing connection", exc);
            }

        }
    }

    public void updateDatabaseV4() {

        Connection connection = null;

        try {

            connection = getDataSource().getConnection();
            JdbcConnection jdbcConnection = new JdbcConnection(connection);

            Liquibase liquibase = createLiquibase("classpath:liquibase/update-v4/changelog.xml", DatabaseFactory.getInstance().findCorrectDatabaseImplementation(jdbcConnection));
            liquibase.setChangeLogParameter("database.role.admin", this.roleAdmin);
            liquibase.setChangeLogParameter("database.role.user", this.roleUser);
            liquibase.setChangeLogParameter("database.role.read_only", this.roleReadOnly);
            liquibase.update(getContexts());

        } catch (ValidationFailedException e) {

            String message = e.getMessage();

            if (!message.contains(MAJOR_VERSION_FAILURE_MESSAGE)) {
                throw new RuntimeException(e);
            }

        } catch (Exception e) {

            try {
                connection.rollback();
            } catch (Exception exc) {
                log.log(Level.WARNING, "problem rolling back connection", exc);
            }

            throw new RuntimeException(e);

        } finally {

            try {
                connection.close();
            } catch (Exception exc) {
                log.log(Level.WARNING, "problem closing connection", exc);
            }

        }
    }

    protected Liquibase createLiquibase(String changeLog, Database database) throws LiquibaseException {

        return new Liquibase(changeLog, new SpringResourceOpener(changeLog), database);
    }

    /**
     * Spring sets this automatically to the instance's configured bean name.
     */
    public void setBeanName(String name) {

        this.beanName = name;
    }

    /**
     * Gets the Spring-name of this instance.
     *
     * @return
     */
    public String getBeanName() {

        return beanName;
    }

    public void setResourceLoader(ResourceLoader resourceLoader) {

        this.resourceLoader = resourceLoader;
    }

    public ResourceLoader getResourceLoader() {

        return resourceLoader;
    }

    public void setNewDatabaseDetectionStrategy(NewDatabaseDetectionStrategy newDatabaseDetectionStrategy) {

        this.newDatabaseDetectionStrategy = newDatabaseDetectionStrategy;
    }

    public void setInstallChangeLog(String installChangeLog) {

        this.installChangeLog = installChangeLog;
    }

    public void setRoleAdmin(String roleAdmin) {
        this.roleAdmin = roleAdmin;
    }

    public void setRoleUser(String roleUser) {
        this.roleUser = roleUser;
    }

    public void setRoleReadOnly(String roleReadOnly) {
        this.roleReadOnly = roleReadOnly;
    }
}
