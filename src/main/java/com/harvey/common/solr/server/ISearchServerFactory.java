package com.harvey.common.solr.server;

import com.harvey.module.core.domain.code.SysIndexObjectTypeCodeEnum;
import org.apache.solr.client.solrj.SolrClient;

/**
 * Created by Administrator on 2017/8/25 0025.
 */
public interface ISearchServerFactory {
     SolrClient getSearchServer(SysIndexObjectTypeCodeEnum searchName);
}
