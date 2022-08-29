package com.dollop.exam101.main.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SessionData {

@SerializedName("id")
@Expose
public String id;
@SerializedName("email")
@Expose
public String email;
@SerializedName("role")
@Expose
public String role;
@SerializedName("display_role")
@Expose
public String displayRole;

}