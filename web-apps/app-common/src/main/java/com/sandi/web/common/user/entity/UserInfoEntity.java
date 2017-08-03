package com.sandi.web.common.user.entity;

import com.sandi.web.common.persistence.entity.BaseEntity;
import com.sandi.web.common.persistence.annotation.Id;

import java.util.Date;

public class UserInfoEntity extends BaseEntity{
	private Integer state;//用户状态，1表示正常，0删除

	private String userSafeAnswer;//用户安全问题答案

	private String userSafeQuestion;//用户安全问题

	private String userOccupation;//用户职业

	private String userInterest;//用户兴趣爱好

	private String userWeight;//用户体重

	private String userHeight;//用户身高

	private String userImages;//用户头像

	private String userAddress;//用户地址

	private Integer userAge;//用户年龄

	private Integer userSex;//用户性别

	private Date userBirth;//用户生日

	private String userEmail;//用户邮箱

	private String phoneNumber;//电话号码

	private String userNick;//用户昵称

	private String userPass;//用户密码

	private String userName;//用户名称

	@Id
	private Long userId;//用户Id

	public Integer getState() {return state;}

	public void setState(Integer state) {this.state = state;}

	public String getUserSafeAnswer() {return userSafeAnswer;}

	public void setUserSafeAnswer(String userSafeAnswer) {this.userSafeAnswer = userSafeAnswer;}

	public String getUserSafeQuestion() {return userSafeQuestion;}

	public void setUserSafeQuestion(String userSafeQuestion) {this.userSafeQuestion = userSafeQuestion;}

	public String getUserOccupation() {return userOccupation;}

	public void setUserOccupation(String userOccupation) {this.userOccupation = userOccupation;}

	public String getUserInterest() {return userInterest;}

	public void setUserInterest(String userInterest) {this.userInterest = userInterest;}

	public String getUserWeight() {return userWeight;}

	public void setUserWeight(String userWeight) {this.userWeight = userWeight;}

	public String getUserHeight() {return userHeight;}

	public void setUserHeight(String userHeight) {this.userHeight = userHeight;}

	public String getUserImages() {return userImages;}

	public void setUserImages(String userImages) {this.userImages = userImages;}

	public String getUserAddress() {return userAddress;}

	public void setUserAddress(String userAddress) {this.userAddress = userAddress;}

	public Integer getUserAge() {return userAge;}

	public void setUserAge(Integer userAge) {this.userAge = userAge;}

	public Integer getUserSex() {return userSex;}

	public void setUserSex(Integer userSex) {this.userSex = userSex;}

	public Date getUserBirth() {return userBirth;}

	public void setUserBirth(Date userBirth) {this.userBirth = userBirth;}

	public String getUserEmail() {return userEmail;}

	public void setUserEmail(String userEmail) {this.userEmail = userEmail;}

	public String getPhoneNumber() {return phoneNumber;}

	public void setPhoneNumber(String phoneNumber) {this.phoneNumber = phoneNumber;}

	public String getUserNick() {return userNick;}

	public void setUserNick(String userNick) {this.userNick = userNick;}

	public String getUserPass() {return userPass;}

	public void setUserPass(String userPass) {this.userPass = userPass;}

	public String getUserName() {return userName;}

	public void setUserName(String userName) {this.userName = userName;}

	public Long getUserId() {return userId;}

	public void setUserId(Long userId) {this.userId = userId;}

}