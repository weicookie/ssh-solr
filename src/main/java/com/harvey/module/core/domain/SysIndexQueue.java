package com.harvey.module.core.domain;

import com.harvey.common.base.BaseEntity;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.annotations.Index;

import javax.persistence.*;

/**
 * solr索引数据存储表
 */
@Entity
@Table(name = "sys_index_queue")
public class SysIndexQueue extends BaseEntity {

    public static final String SYS_INDEX_QUEUE_ID = "sysIndexQueueId";
    public static final String SYS_INDEX_OBJECT_TYPE_CODE = "sysIndexObjectTypeCode";
    public static final String SYS_OBJECT_ID = "sysObjectId";

    /**
     * sysIndexQueueId	   db_column: SYS_INDEX_QUEUE_ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "SYS_INDEX_QUEUE_ID", unique = false, nullable = false, insertable = true, updatable = true, length = 10)
    /**
     * sysIndexQueueId	   db_column: SYS_INDEX_QUEUE_ID
     */
    private Integer sysIndexQueueId;
    /**
     * sysIndexObjectTypeCode	   db_column: SYS_INDEX_OBJECT_TYPE_CODE
     */
    @Index(name = "SIQ_OBJECT_TYPE_CODE")
    @Column(name = "SYS_INDEX_OBJECT_TYPE_CODE", unique = false, nullable = false, insertable = true, updatable = true, length = 2)
    private String sysIndexObjectTypeCode;
    /**
     * sysObjectId	   db_column: SYS_OBJECT_ID
     */
    @Column(name = "SYS_OBJECT_ID", unique = false, nullable = false, insertable = true, updatable = true, length = 10)
    private Integer sysObjectId;
    //columns END

    public SysIndexQueue(){
    }

    public SysIndexQueue(Integer sysIndexQueueId){
        this.sysIndexQueueId = sysIndexQueueId;
    }

    public void setSysIndexQueueId(Integer value) {
        this.sysIndexQueueId = value;
    }

    public Integer getSysIndexQueueId() {
        return this.sysIndexQueueId;
    }
    public void setSysIndexObjectTypeCode(String value) {
        this.sysIndexObjectTypeCode = value;
    }

    public String getSysIndexObjectTypeCode() {
        return this.sysIndexObjectTypeCode;
    }
    public void setSysObjectId(Integer value) {
        this.sysObjectId = value;
    }

    public Integer getSysObjectId() {
        return this.sysObjectId;
    }

    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("SYS_INDEX_QUEUE_ID",getSysIndexQueueId())
                .append("SYS_INDEX_OBJECT_TYPE_CODE",getSysIndexObjectTypeCode())
                .append("SYS_OBJECT_ID",getSysObjectId())
                .toString();
    }

    public int hashCode() {
        return new HashCodeBuilder()
                .append(getSysIndexQueueId())
                .toHashCode();
    }

    public boolean equals(Object obj) {
        if(this.getSysIndexQueueId() == null){
            return false;
        }
        if(!(obj instanceof SysIndexQueue)){
            return false;
        }
        if(this == obj) {
            return true;
        }
        SysIndexQueue other = (SysIndexQueue)obj;
        return new EqualsBuilder()
                .append(getSysIndexQueueId(),other.getSysIndexQueueId())
                .isEquals();
    }

}
