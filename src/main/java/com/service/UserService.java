package com.service;

import java.sql.SQLException;

import com.dao.userdao;
import com.model.User;

public class UserService {
	public static Integer saveUser(User user) {
		try {
		if(userdao.isExist(user.getEmail())) {
			return 0;
		}else {
			return userdao.saveUser(user);
		}
		}catch(SQLException ex) {
			ex.printStackTrace();
	}
		return null;
	
	}
}
