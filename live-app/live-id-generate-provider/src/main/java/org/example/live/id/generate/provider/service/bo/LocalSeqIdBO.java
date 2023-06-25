package org.example.live.id.generate.provider.service.bo;

import java.util.concurrent.atomic.AtomicLong;

public class LocalSeqIdBO {

	private int id;

	/**
	 * RAM current sequential id value
	 */
	private AtomicLong currentNum;
	
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

	public AtomicLong getCurrentNum() {
		return currentNum;
	}

	public void setCurrentNum(AtomicLong currentNum) {
		this.currentNum = currentNum;
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
		return "LocalSeqIdBO [id=" + id + ", currentNum=" + currentNum + ", currentStart=" + currentStart
				+ ", nextThreshold=" + nextThreshold + "]";
	}


	
}
