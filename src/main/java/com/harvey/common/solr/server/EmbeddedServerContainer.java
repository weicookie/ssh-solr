package com.harvey.common.solr.server;

import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.embedded.EmbeddedSolrServer;
import org.apache.solr.core.CoreContainer;

import java.io.File;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Map;

/**
 *  创建solr core
 */
public class EmbeddedServerContainer {
    private static  Logger logger = Logger.getLogger(EmbeddedServerContainer.class.getName());
    private CoreContainer container;
    private String localHome;

    public EmbeddedServerContainer(String home) {
        this.localHome = home;
        start();
    }

    private void start(){
        Date start = new Date();
        container = CoreContainer.createAndLoad(Paths.get(localHome), Paths.get(localHome,"solr.xml"));
        logger.info("solr started,finish time=" + ((start.getTime() - (new Date().getTime())) / 1000) + "s");
        logger.info("#################################");
    }

    public SolrClient getSolrCore(String solrName) {
        if(null == container.getCore(solrName)){ //创建 solr core
            addCore(solrName, localHome + File.separator + solrName, null, false);
        }
        //根据container创建solrClient
        return new EmbeddedSolrServer(container, solrName);
    }

    public synchronized void addCore(String solrName, String instanceDir, Map<String, String> parameters, boolean newCollection){
        container.create(solrName, Paths.get(instanceDir), parameters, newCollection);
    }
}
