package it.ictgroup.config.service;

import it.ictgroup.config.annotation.ConfigParam;
import it.ictgroup.config.annotation.ConfigParamGetter;
import it.ictgroup.config.service.AbstractIndexConfig;
import it.ictgroup.service.matrix.entity.MatrixParAppl;
import it.ictgroup.service.matrix.service.EmmaMatrixParService;
import org.jboss.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
@SuppressWarnings("unused")
public class Config extends AbstractIndexConfig {

    final Logger LOG = Logger.getLogger(getClass());

    @Inject
    EmmaMatrixParService matrixParService;

    @ConfigParam
    protected Optional<Boolean> defaultRefreshConf = Optional.empty();
    @ConfigParam protected Optional<String> indexConf = Optional.empty();
    @ConfigParam protected Optional<String> camelEnabledConf = Optional.empty();
    @ConfigParam protected Optional<String> emmaEventJmsRouteConf = Optional.empty();
    @ConfigParam protected Optional<String> notificationIndexConf = Optional.empty();

    public static final String JMS_EMMA_QUEUE = "emmaeventQ";
    public static final String JMS_EMMA_TOPIC = "emmaeventT";
    public static final String EMMA_EVENT_JMS_ROUTE = "jms:topic:"+ JMS_EMMA_TOPIC + "?connectionFactory=jmsConnectionFactory";
    public static final String CAMEL_PUBLISH_EVENT = "direct:flotte_emmaevent_out";

    public static final String NOTIFICATION = "notification";

    @Override
    @ConfigParamGetter
    public boolean getRefreshDefault() {
        try {
            if (defaultRefreshConf.isEmpty()) {
                defaultRefreshConf =
                        Optional.of(Boolean.valueOf(matrixParService
                                .getValue("es.default.refresh")
                                .map(MatrixParAppl::getValue)
                                .orElse("true")));
            }
            return defaultRefreshConf.get();
        } catch (Exception e) {
            LOG.error(e.getLocalizedMessage());
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public String getIndex() {
        return null;
    }

    @Override
    public boolean isMultiFieldIndex(String indexName) {
        return false;
    }

    @Override
    public String getIndexValue(String matrixParKey) {
        return null;
    }

    @Override
    public String getIndexKey(String matrixParValue) {
        return null;
    }

    @Override
    public List<String> getAllIndexKeys() {
        return null;
    }

    @ConfigParamGetter
    public String getCamelEnabled() {
        try {
            if (camelEnabledConf.isEmpty()) {
                camelEnabledConf =
                        Optional.of(matrixParService
                                .getValue("camel.enabled")
                                .map(MatrixParAppl::getValue)
                                .orElse("Y"));
            }
            return camelEnabledConf.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @ConfigParamGetter
    public String getEmmaEventRoute() {
        try {
            if (emmaEventJmsRouteConf.isEmpty()) {
                emmaEventJmsRouteConf =
                        Optional.of(matrixParService
                                .getValue("camel.emmaevent.route")
                                .map(MatrixParAppl::getValue)
                                .orElse(EMMA_EVENT_JMS_ROUTE));
            }
            return emmaEventJmsRouteConf.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @ConfigParamGetter
    public String getNotificationIndex() {
        try {
            if (notificationIndexConf.isEmpty()) {
                notificationIndexConf = Optional.of(matrixParService.getValue("es." + NOTIFICATION + ".index").map(MatrixParAppl::getValue).orElse("six_notification"));
            }
            return notificationIndexConf.get();
        } catch (Exception e) {
            LOG.error(e.getLocalizedMessage());
            e.printStackTrace();
        }
        return null;
    }


}
