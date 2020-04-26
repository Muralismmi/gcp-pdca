package com.entity;


import java.io.Serializable;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@SuppressWarnings("serial")
@PersistenceCapable
public class IdJdo implements Serializable {
	
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;
	
	int next_id ;
	String id_name ;


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getNext_id() {
		return next_id;
	}

	public void setNext_id(int next_id) {
		this.next_id = next_id;
	}

	public String getId_name() {
		return id_name;
	}

	public void setId_name(String id_name) {
		this.id_name = id_name;
	}

}
