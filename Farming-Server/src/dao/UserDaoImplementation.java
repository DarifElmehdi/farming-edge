package dao;


import java.util.List;

import beans.User;

public class UserDaoImplementation implements UserDao{
	
	private DaoFactory daoFactory;
	
	public UserDaoImplementation(DaoFactory daoFactory) {
		this.setDaoFactory(daoFactory);
	}

	@Override
	public void addUser(User u) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteUser(String email) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public User getUserByEmail(String email) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> getUsers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User getUser(String email, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	public DaoFactory getDaoFactory() {
		return daoFactory;
	}

	public void setDaoFactory(DaoFactory daoFactory) {
		this.daoFactory = daoFactory;
	}	
}
