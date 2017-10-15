package com.harvey.common.solr.client;

import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.beans.DocumentObjectBinder;
import org.apache.solr.common.SolrInputDocument;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * SolrInputDocument和JavaBean对象进行相互转换,关键API：DocumentObjectBinder
 *
 */
public class SolrDocumentUtil {

    private static Logger logger = Logger.getLogger(SolrDocumentUtil.class.getName());

    private SolrDocumentUtil() {
    }

    public static void addBeans(SolrClient server, List beans) throws IOException, SolrServerException{
        DocumentObjectBinder binder = server.getBinder();
        List<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
        for (Object bean : beans){
            docs.add(toSolrInputDocument(binder, bean));
        }
        server.add(docs);
    }

    public static void addBean(SolrClient server, Object bean) throws IOException, SolrServerException{
        DocumentObjectBinder binder = server.getBinder();
        SolrInputDocument document = toSolrInputDocument(binder, bean);
        server.add(document);
    }

    public static SolrInputDocument toSolrInputDocument(DocumentObjectBinder binder, Object bean){
        return binder.toSolrInputDocument(bean);
    }
}
