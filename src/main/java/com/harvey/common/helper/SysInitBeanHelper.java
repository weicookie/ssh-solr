package com.harvey.common.helper;

import com.harvey.common.solr.server.SearchServerManager;
import com.harvey.module.core.domain.code.SysIndexObjectTypeCodeEnum;
import com.harvey.service.ISysIndexQueueService;
import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrClient;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2017/8/26 0026.
 */
@Component
public class SysInitBeanHelper implements InitializingBean {
    private Logger log = Logger.getLogger(this.getClass());
    //是否索引初始化
    private static boolean solrInited = false;
    //solr服务地址
    private String baseUrl;
    //是否重建所有索引
    private  boolean initAllIndexs;

    private ISysIndexQueueService sysIndexQueueService;

    @Autowired
    public void setSysIndexQueueService(ISysIndexQueueService sysIndexQueueService) {
        this.sysIndexQueueService = sysIndexQueueService;
    }

    public static boolean isSolrInited() {
        return solrInited;
    }

    @Value("${app.search.solr.server.baseUrl:}")
    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Value("${app.search.solr.server.initAllIndexs:false}")
    public void setInitAllIndexs(boolean initAllIndexs) {
        this.initAllIndexs = initAllIndexs;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("系统开始启动...");
        initSearchServerManager();

    }

    private void initSearchServerManager(){
        log.info("索引服务启动中...");
        SearchServerManager.initSearchServerFactory(this.baseUrl);
        log.info("索引服务启动完毕...");
        if (initAllIndexs) {
            for (SysIndexObjectTypeCodeEnum core : SysIndexObjectTypeCodeEnum.values()) {
                try {
                    SolrClient server = SearchServerManager.getSearchServer(core);
                    if (server != null) {
                        server.deleteByQuery("*:*");
                        server.commit();
                    }
                } catch (Throwable e) {
                    log.error("clear index failed.", e);
                }
            }
            try {
                sysIndexQueueService.sysInitAllIndexs();
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
        solrInited = true;
    }

}
