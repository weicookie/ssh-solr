package com.harvey.common.solr.server;

import com.harvey.module.core.domain.code.SysIndexObjectTypeCodeEnum;
import org.apache.solr.client.solrj.SolrClient;

import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Administrator on 2017/8/25 0025.
 */
public class LocalSolrSearchServerFactory implements ISearchServerFactory{
    private static Map<SysIndexObjectTypeCodeEnum, SolrClient> searchServerMap = new ConcurrentHashMap<SysIndexObjectTypeCodeEnum, SolrClient>();
    private EmbeddedServerContainer embeddedServerContainer;

    public LocalSolrSearchServerFactory(String home){
        embeddedServerContainer = new EmbeddedServerContainer(home);
    }

    @Override
    public SolrClient getSearchServer(SysIndexObjectTypeCodeEnum searchName) {
        if (!searchServerMap.containsKey(searchName)) {
            SolrClient solrServer = embeddedServerContainer.getSolrCore(searchName.name().toLowerCase(Locale.ENGLISH));
            if (solrServer != null) {
                searchServerMap.put(searchName, solrServer);
            }
        }
        return searchServerMap.get(searchName);
    }
}
