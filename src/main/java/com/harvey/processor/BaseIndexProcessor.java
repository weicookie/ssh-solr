package com.harvey.processor;

import com.harvey.common.helper.ServiceManager;
import com.harvey.common.helper.SysInitBeanHelper;
import com.harvey.common.solr.client.IIndexProvider;
import com.harvey.common.solr.client.IndexBuilder;
import com.harvey.common.solr.entity.ISearchEntity;
import com.harvey.module.core.domain.SysIndexQueue;
import com.harvey.module.core.domain.code.SysIndexObjectTypeCodeEnum;
import com.harvey.service.ISysIndexQueueService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import java.util.ArrayList;
import java.util.List;

/**
 * 索引进程基类
 */
public abstract class BaseIndexProcessor implements IProcessor{
    private Logger logger = Logger.getLogger(this.getClass().getName());
    private boolean available;
    private boolean isRunning = false;

    @Value("${app.search.solr.server.buildingIndexs.available:false}")
    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Scheduled(cron="${app.search.solr.server.indexUpdateFrequency:1 1 1 1 JAN */2099}")
    @Override
    public void process() {
        if (!ServiceManager.isInited() || !SysInitBeanHelper.isSolrInited() || !available) {
            return;
        }
        synchronized(this){
            if(isRunning){
                return;
            }
            isRunning = true;
            try{
                buildIndex();
            }catch (Throwable tr) {
                logger.error(tr.getMessage(), tr);
            }finally {
                isRunning = false;
            }
        }
    }

    /**
     * todo 修改成多线程处理索引
     */
    private void buildIndex(){
        List<SysIndexQueue> queList = getSysIndexQueueService().findByObjectType(getObjectTypeCode(), 100);
        if(null == queList || queList.size()<=0){
            return;
        }
        List<Integer> ids = new ArrayList<Integer>();
        for (SysIndexQueue que : queList) {
            ids.add(que.getSysObjectId());
        }
        IndexBuilder.commit(ids, getProvider(),getObjectTypeCode());
        if(!queList.isEmpty()){
            getSysIndexQueueService().deleteList(queList);
        }
        if (ids.size() > 0) {
            logger.info("成功建立"+getObjectTypeCode()+"索引数据，size=" + ids.size());
        }
    }

    public abstract ISysIndexQueueService getSysIndexQueueService();

    public abstract void setSysIndexQueueService(ISysIndexQueueService sysIndexQueueService);

    public abstract IIndexProvider<? extends ISearchEntity> getProvider();

    public abstract SysIndexObjectTypeCodeEnum getObjectTypeCode();
}
