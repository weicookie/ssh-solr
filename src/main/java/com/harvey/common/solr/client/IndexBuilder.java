package com.harvey.common.solr.client;

import com.harvey.common.solr.entity.ISearchEntity;
import com.harvey.common.solr.server.SearchServerManager;
import com.harvey.module.core.domain.code.SysIndexObjectTypeCodeEnum;
import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
/**
 * 创建索引工具类
 */
public class IndexBuilder {
    private static Logger logger = Logger.getLogger(IndexBuilder.class);

    private IndexBuilder() {
    }

    public static void commit(List<Integer> ids, IIndexProvider provider, SysIndexObjectTypeCodeEnum typeCode) {
        if (null == ids || ids.isEmpty()) {
            return;
        }
        SolrClient searchServer = SearchServerManager.getSearchServer(typeCode);
        List<ISearchEntity> beans = new ArrayList<ISearchEntity>();
        List<String> deleteIds = new ArrayList<String>();
        for (Integer id : ids) {
            ISearchEntity bean = null;
            try {
                bean = provider.getEntity(id);
            } catch (Throwable e) {
                //如果是数据问题导致这条数据不能建立则应该跳过保证其它的索引能正常建立
                logger.error(e);
                continue;
            }
            if (bean == null) {
                deleteIds.add(String.valueOf(id));
                continue;
            }

            if (bean.isSingleSearchEntity()) {
                beans.add(bean);
            }
        }

        try {

            if (beans.size() > 0) {
                SolrDocumentUtil.addBeans(searchServer, beans);
            }
            //删除无效的索引
            if (deleteIds.size() > 0) {
                searchServer.deleteById(deleteIds);
            }
            //提交索引
            synchronized (searchServer) {
                searchServer.commit();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SolrServerException e) {
            e.printStackTrace();
        }
    }

    public static void commit(Integer id, IIndexProvider provider, SysIndexObjectTypeCodeEnum typeCode) {
        if(null == id){
            return;
        }
        SolrClient searchServer = SearchServerManager.getSearchServer(typeCode);
        ISearchEntity bean = provider.getEntity(id);

        try {
            if(bean == null) {
                searchServer.deleteById(String.valueOf(id));
            }else{
                if(bean.isSingleSearchEntity()){
                    SolrDocumentUtil.addBean(searchServer, bean);
                }
            }
            synchronized (searchServer){
                searchServer.commit();
            }

        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
