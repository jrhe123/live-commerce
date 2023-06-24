package org.example.live.id.generate.interfaces;

public interface IdGenerateRpc {

    /**
     * get sequential id
     *
     * @param id
     * @return
     */
    Long getSeqId(Integer id);

    /**
     * get non-sequential id
     *
     * @param id
     * @return
     */
    Long getUnSeqId(Integer id);
}
