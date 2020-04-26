package com.DAO;

import java.util.HashMap;
import java.util.List;

import com.entity.User;

public interface UserDAO {
	public void saveUser(User adminJdo);

	public User getUserByEmail(String userMail);
	public User getPillarLeadForPlant(String pillarName,String PlantName);
	public User fetchUserById(Long id);
	public List<String> getuserWithRoleandPlant(String roleName,String plantName);
	public List<User> fetchUserList();
	public String deleteUser(long id);
	
	public Long countUsers();

}
