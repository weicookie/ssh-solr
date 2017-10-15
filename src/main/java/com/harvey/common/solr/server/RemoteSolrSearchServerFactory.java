package com.harvey.common.solr.server;

import com.harvey.module.core.domain.code.SysIndexObjectTypeCodeEnum;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;

import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Administrator on 2017/8/25 0025.
 */
public class RemoteSolrSearchServerFactory implements ISearchServerFactory{
    private String baseUrl;
    private static Map<SysIndexObjectTypeCodeEnum, SolrClient> searchServerMap = new ConcurrentHashMap<SysIndexObjectTypeCodeEnum, SolrClient>();

    public RemoteSolrSearchServerFactory(String baseUrl){
        this.baseUrl = baseUrl;
    }

    @Override
    public SolrClient getSearchServer(SysIndexObjectTypeCodeEnum searchName) {
        //最终的solr路径： http://192.168.1.1:8080/solr/sysuser
        String codeUrl = baseUrl;
        if (codeUrl.endsWith("/")) {
            codeUrl = codeUrl + searchName.name().toLowerCase(Locale.ENGLISH);
        } else {
            codeUrl = codeUrl + "/" + searchName.name().toLowerCase(Locale.ENGLISH);
        }
        if(!searchServerMap.containsKey(searchName)){
            HttpSolrClient solrClient = new HttpSolrClient.Builder(codeUrl).build();
            solrClient.setSoTimeout(60000);
            solrClient.setMaxTotalConnections(100);
            solrClient.setDefaultMaxConnectionsPerHost(100);
            searchServerMap.put(searchName, solrClient);
        }
        return searchServerMap.get(searchName);
    }
}
