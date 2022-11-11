package it.ictgroup.service.elastic;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import it.ictgroup.utils.json.JacksonMapper;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.jboss.logging.Logger;

import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

import java.io.IOException;

@ApplicationScoped
public class ElasticService {

    @ConfigProperty(name = "asset.es.address")
    String esAddress;

    @ConfigProperty(name = "asset.es.user")
    String esUser;

    @ConfigProperty(name = "asset.es.pwd")
    String esPwd;

    @ConfigProperty(name = "asset.es.schema")
    String esSchema;

    @ConfigProperty(name = "asset.es.conn.timeout")
    String esConnTimeout;

    @ConfigProperty(name = "asset.es.socket.timeout")
    String esSocketTimeout;

    final Logger LOG = Logger.getLogger(getClass());

    ElasticsearchClient client;

    RestClient restClient;

    @Produces
    public ElasticsearchClient buildClient() {
        LOG.infof("ElasticService esAddress is : %s: ", esAddress);
        String address = esAddress;
        int port = 443;
        if (esAddress.contains(":")) {
            address = esAddress.split(":")[0];
            port = Integer.parseInt(esAddress.split(":")[1]);
        }
        LOG.infof("ElasticService address : %s", address);
        LOG.infof("ElasticService port :  %d", port);

        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials(esUser, esPwd));

        RestClientBuilder restClientBuilder = RestClient.builder(
                        new HttpHost(address, port, esSchema))
                .setHttpClientConfigCallback(httpClientBuilder -> httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider))
                .setRequestConfigCallback(
                        requestConfigBuilder -> requestConfigBuilder
                                .setConnectTimeout(Integer.parseInt(esConnTimeout))
                                .setSocketTimeout(Integer.parseInt(esSocketTimeout)));
        this.restClient = restClientBuilder.build();
        ElasticsearchTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper(JacksonMapper.MAPPER));
        this.client = new ElasticsearchClient(transport);
        return this.client;
    }


    @PreDestroy
    public void cleanup() {
        try {
            LOG.info("Closing the ES REST client");
            this.restClient.close();
        } catch (IOException ioe) {
            LOG.error("Problem occurred when closing the ES REST client", ioe);
        }
    }

}
