package com.DAO;

import java.util.List;

import com.entity.Request;

public interface RequestDAO {
	public Request saveRequest(Request objRequest);
	//public Request getRequestbyName(String requestName);
	public Request fetchRequestById(Long id);
	public List<Request> fetchRequestList();
	public String deleteRequest(long id);
	public Long countRequest();
}
