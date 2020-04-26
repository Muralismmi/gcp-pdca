package com.DAO;

import java.util.List;

import com.entity.Pillar;
import com.entity.Plant;

public interface PillarDAO {
	public void savePillar(Pillar adminJdo);

	public Pillar getPillarbyName(String pillarName);

	public Pillar fetchPillarById(Long id);

	public List<Pillar> fetchPillarList();
	public String deletePillar(long id);
	
	public Long countPillar();
}
