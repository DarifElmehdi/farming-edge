package dao;

import java.util.List;

import beans.User;

public interface UserDao {
	public void addUser(User u);
	public void deleteUser(String email);
	public User getUserByEmail(String email);
	public List<User> getUsers();
	public User getUser(String email,String password);
}
