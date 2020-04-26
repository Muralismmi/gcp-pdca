package com.DAO;

import java.util.List;

import com.entity.LossType;
import com.entity.Tool;


public interface LossTypeDAO {
	public void saveLossType(LossType objLossType);
	public List<LossType> fetchAll();
	public LossType getLossTypeByName(String  name);
	public LossType getLossTypeByNameandPlant(String name);
	public LossType fetchLossTypeById(Long id);
	public String deleteLossType(long id);
	public Long countLossType();
}
