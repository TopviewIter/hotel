package com.xqx.www.po;

import java.util.Date;

/**
 * 订单模型
 * @author xqx
 *
 */
public class Order {

	/** 订单编号*/
	private String id;
	
	/** 用户编号*/
	private String userId;
	
	/** 房间编号*/
	private String roomId;
	
	/** 订单状态*/
	private String state;
	
	/** 订单创建时间*/
	private Date createTime;
	
	/** 预订房间开始时间*/
	private Date startTime;
	
	/** 退房时间*/
	private Date endTime;
	
	/** 是否已经支付*/
	private boolean isPay;
	
	/** 应收金额*/
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
