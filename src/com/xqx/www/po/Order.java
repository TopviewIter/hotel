package com.xqx.www.po;

import java.util.Date;

/**
 * ����ģ��
 * @author xqx
 *
 */
public class Order {

	/** �������*/
	private String id;
	
	/** �û����*/
	private String userId;
	
	/** ������*/
	private String roomId;
	
	/** ����״̬*/
	private String state;
	
	/** ��������ʱ��*/
	private Date createTime;
	
	/** Ԥ�����俪ʼʱ��*/
	private Date startTime;
	
	/** �˷�ʱ��*/
	private Date endTime;
	
	/** �Ƿ��Ѿ�֧��*/
	private boolean isPay;
	
	/** Ӧ�ս��*/
	private Double momey;

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return the roomId
	 */
	public String getRoomId() {
		return roomId;
	}

	/**
	 * @param roomId the roomId to set
	 */
	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the startTime
	 */
	public Date getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return the endTime
	 */
	public Date getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	/**
	 * @return the isPay
	 */
	public boolean isPay() {
		return isPay;
	}

	/**
	 * @param isPay the isPay to set
	 */
	public void setPay(boolean isPay) {
		this.isPay = isPay;
	}

	/**
	 * @return the momey
	 */
	public Double getMomey() {
		return momey;
	}

	/**
	 * @param momey the momey to set
	 */
	public void setMomey(Double momey) {
		this.momey = momey;
	}
	
}
