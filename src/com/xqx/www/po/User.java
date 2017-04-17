package com.xqx.www.po;

/**
 * 用户模型
 * @author xqx
 *
 */
public class User {

	/** 编号*/
	private String id;
	
	/** 姓名*/
	private String name;
	
	/** 密码*/
	private String password;
	
	/** 身份证号*/
	private String identify;
	
	/** 联系电话*/
	private String phone;
	
	/** 性别*/
	private String sex;
	
	/** 是否为vip*/
	private Boolean isVip;
	
	/** 账号等级*/
	private String rank;

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
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the identify
	 */
	public String getIdentify() {
		return identify;
	}

	/**
	 * @param identify the identify to set
	 */
	public void setIdentify(String identify) {
		this.identify = identify;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @return the sex
	 */
	public String getSex() {
		return sex;
	}

	/**
	 * @param sex the sex to set
	 */
	public void setSex(String sex) {
		this.sex = sex;
	}

	/**
	 * @return the isVip
	 */
	public Boolean isVip() {
		return isVip;
	}

	/**
	 * @param isVip the isVip to set
	 */
	public void setVip(Boolean isVip) {
		this.isVip = isVip;
	}

	/**
	 * @return the rank
	 */
	public String getRank() {
		return rank;
	}

	/**
	 * @param rank the rank to set
	 */
	public void setRank(String rank) {
		this.rank = rank;
	}
	
}
