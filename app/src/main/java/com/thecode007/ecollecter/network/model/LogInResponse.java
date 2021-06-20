package com.thecode007.ecollecter.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LogInResponse {

@SerializedName("access_token")
@Expose
private String accessToken;
@SerializedName("token_type")
@Expose
private String tokenType;
@SerializedName("user_id")
@Expose
private Integer userId;
@SerializedName("role_id")
@Expose
private Integer roleId;
@SerializedName("role")
@Expose
private String role;

public String getAccessToken() {
return accessToken;
}

public void setAccessToken(String accessToken) {
this.accessToken = accessToken;
}

public String getTokenType() {
return tokenType;
}

public void setTokenType(String tokenType) {
this.tokenType = tokenType;
}

public Integer getUserId() {
return userId;
}

public void setUserId(Integer userId) {
this.userId = userId;
}

public Integer getRoleId() {
return roleId;
}

public void setRoleId(Integer roleId) {
this.roleId = roleId;
}

public String getRole() {
return role;
}

public void setRole(String role) {
this.role = role;
}

}