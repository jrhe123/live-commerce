package org.example.live.id.generate.provider.service.bo;

import java.util.concurrent.ConcurrentLinkedQueue;

public class LocalUnSeqIdBO {

	private int id;

	/**
	 * pre-defined un-seq ids
	 */
	private ConcurrentLinkedQueue<Long> idQueue;
	
	/**
	 * current id start value
	 */
	private Long currentStart;
	
	/**
	 * current id end value
	 */
	private Long nextThreshold;
	
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ConcurrentLinkedQueue<Long> getIdQueue() {
		return idQueue;
	}

	public void setIdQueue(ConcurrentLinkedQueue<Long> idQueue) {
		this.idQueue = idQueue;
	}

	public Long getCurrentStart() {
		return currentStart;
	}

	public void setCurrentStart(Long currentStart) {
		this.currentStart = currentStart;
	}

	public Long getNextThreshold() {
		return nextThreshold;
	}

	public void setNextThreshold(Long nextThreshold) {
		this.nextThreshold = nextThreshold;
	}

	@Override
	public String toString() {
		return "LocalUnSeqIdBO [id=" + id + ", idQueue=" + idQueue + ", currentStart=" + currentStart
				+ ", nextThreshold=" + nextThreshold + "]";
	}
	
	


	
}
