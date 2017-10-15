package com.harvey.module.systools.service;

import com.harvey.common.solr.server.SearchServerManager;
import com.harvey.module.core.domain.code.SysIndexObjectTypeCodeEnum;
import com.harvey.service.IIndexService;
import com.harvey.service.ISysIndexQueueService;
import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;

/**
 * 索引操作service
 */
@Service
public class IndexService implements IIndexService {
    private Logger logger = Logger.getLogger(this.getClass().getName());

    private ISysIndexQueueService sysIndexQueueService;

    @Autowired
    public void setSysIndexQueueService(ISysIndexQueueService sysIndexQueueService) {
        this.sysIndexQueueService = sysIndexQueueService;
    }

    private final static String insertSysUserIndexSQL = "insert into sys_index_queue(sys_object_id,sys_index_object_type_code) select sys_user_id,"+ SysIndexObjectTypeCodeEnum.SYSUSER.toCode()+" from sys_user";

    /**
     * 重建索引
     * @param types
     */
    @Override
    public void reBuildIndex(String[] types) {
        for (String type : types){
            SysIndexObjectTypeCodeEnum typeCode = SysIndexObjectTypeCodeEnum.fromCode(type);
            //这里如果先删除索引，会导致页面空白
            switch (typeCode){
                case SYSUSER:
                    sysIndexQueueService.execIndexSQL(insertSysUserIndexSQL);
                default:
                    break;
            }
        }
    }

    /**
     * 删除索引
     * @param types
     */
    @Override
    public void deleteIndex(String[] types) {
        for (String type : types){
            SysIndexObjectTypeCodeEnum typeCode = SysIndexObjectTypeCodeEnum.fromCode(type);
            SolrClient solrClient = SearchServerManager.getSearchServer(typeCode);
            try {
                solrClient.deleteByQuery("*:*");
            } catch (SolrServerException e) {
                logger.info("删除索引文件" + typeCode + "失败");
            } catch (IOException e) {
                logger.info("删除索引文件" + typeCode + "失败");
            }
        }
    }
}
