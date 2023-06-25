package org.example.live.id.generate.provider.service;

public interface IdGenerateService {

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
