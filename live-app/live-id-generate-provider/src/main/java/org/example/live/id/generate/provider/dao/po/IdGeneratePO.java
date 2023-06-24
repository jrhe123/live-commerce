package org.example.live.id.generate.provider.dao.po;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("t_id_generate_config")
public class IdGeneratePO {

	@TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * id generator remark
     */
    private String remark;

    /**
     * init value
     */
    private long initNum;

    /**
     * step value
     */
    private int step;

    /**
     * is sequential?
     */
    private int isSeq;

    /**
     * current id starting value
     */
    private long currentStart;

    /**
     * current id threshold value
     */
    private long nextThreshold;

    /**
     * business logic prefix, could be null
     */
    private String idPrefix;

    /**
     * optimistic lock version
     */
    private int version;

    private Date createTime;

    private Date updateTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public long getInitNum() {
        return initNum;
    }

    public void setInitNum(long initNum) {
        this.initNum = initNum;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public long getCurrentStart() {
        return currentStart;
    }

    public void setCurrentStart(long currentStart) {
        this.currentStart = currentStart;
    }

    public long getNextThreshold() {
        return nextThreshold;
    }

    public void setNextThreshold(long nextThreshold) {
        this.nextThreshold = nextThreshold;
    }

    public String getIdPrefix() {
        return idPrefix;
    }

    public void setIdPrefix(String idPrefix) {
        this.idPrefix = idPrefix;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public int getIsSeq() {
        return isSeq;
    }

    public void setIsSeq(int isSeq) {
        this.isSeq = isSeq;
    }

    @Override
    public String toString() {
        return "IdGeneratePO{" +
                "id=" + id +
                ", remark='" + remark + '\'' +
                ", initNum=" + initNum +
                ", step=" + step +
                ", isSeq=" + isSeq +
                ", currentStart=" + currentStart +
                ", nextThreshold=" + nextThreshold +
                ", idPrefix='" + idPrefix + '\'' +
                ", version=" + version +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
