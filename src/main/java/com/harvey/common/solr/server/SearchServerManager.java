package com.harvey.common.solr.server;

import com.harvey.common.web.WebContextFactory;
import com.harvey.module.core.domain.code.SysIndexObjectTypeCodeEnum;
import org.apache.solr.client.solrj.SolrClient;
import java.io.File;
import java.net.URL;

/**
 * 搜索服务器
 */
public class SearchServerManager {
    private static ISearchServerFactory serverFactory = null;
    private static final String LOCAL_PATH =  "solr";

    private static String baseUrl;//默认是本地

    private SearchServerManager() {}

    public static synchronized void initSearchServerFactory(String baseUrl){
        SearchServerManager.baseUrl = baseUrl;
    }

    public static synchronized SolrClient getSearchServer(SysIndexObjectTypeCodeEnum sysIndexObjectTypeCodeEnum){
        if(null == serverFactory) {
            if (null != baseUrl && !"".equals(baseUrl)) {
                serverFactory = new RemoteSolrSearchServerFactory(baseUrl);
            } else {
                String webInfPath = WebContextFactory.getWebRealPath();
                if (webInfPath != null) {
                    webInfPath += File.separatorChar+"WEB-INF";
                } else {
                    URL url = WebContextFactory.class.getClassLoader().getResource("");
                    webInfPath = new File(url.getFile()).getParentFile().getPath();
                }
                serverFactory = new LocalSolrSearchServerFactory(webInfPath+ File.separatorChar +  LOCAL_PATH);
            }
        }
        return serverFactory.getSearchServer(sysIndexObjectTypeCodeEnum);
    }
}
