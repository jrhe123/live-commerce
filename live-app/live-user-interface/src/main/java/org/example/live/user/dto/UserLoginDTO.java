package org.example.live.user.dto;

import java.io.Serial;
import java.io.Serializable;

public class UserLoginDTO implements Serializable {

	@Serial
	private static final long serialVersionUID = -2391384279725579138L;

	private boolean isLoginSuccess;
    private String desc;
    private Long userId;
    private String token;
    
    
    public static UserLoginDTO loginError(String desc) {
        UserLoginDTO userLoginDTO = new UserLoginDTO();
        userLoginDTO.setLoginSuccess(false);
        userLoginDTO.setDesc(desc);
        return userLoginDTO;
    }

    public static UserLoginDTO loginSuccess(Long userId, String token) {
        UserLoginDTO userLoginDTO = new UserLoginDTO();
        userLoginDTO.setLoginSuccess(true);
        userLoginDTO.setUserId(userId);
        userLoginDTO.setToken(token);
        return userLoginDTO;
    }
    
    
	public boolean isLoginSuccess() {
		return isLoginSuccess;
	}
	public void setLoginSuccess(boolean isLoginSuccess) {
		this.isLoginSuccess = isLoginSuccess;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
	
	@Override
	public String toString() {
		return "UserLoginDTO [isLoginSuccess=" + isLoginSuccess + ", desc=" + desc + ", userId=" + userId + ", token="
				+ token + "]";
	}
    
    
    
}
