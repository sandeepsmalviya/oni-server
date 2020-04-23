package com.oni.server.model;

import java.util.List;

public class Users {
	
	private List<User> usersNew;

	public Users() {
		super();
	}
	
	
	public Users(List<User> usersNew) {
		super();
		this.usersNew = usersNew;
	}


	public List<User> getUsers() {
		return usersNew;
	}

	public void setUsers(List<User> usersNew) {
		this.usersNew = usersNew;
	}
	
	
	

}
