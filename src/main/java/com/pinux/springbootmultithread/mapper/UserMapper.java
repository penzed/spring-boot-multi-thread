package com.pinux.springbootmultithread.mapper;


import com.pinux.springbootmultithread.model.User;

import java.util.List;

public interface UserMapper {
	
	List<User> getAll();
	
	User getOne(Long id);

	void insert(User user);

	void update(User user);

	void delete(Long id);

	void insertBatch(List<User> list);

}