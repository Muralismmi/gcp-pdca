package com.DAO;

import java.util.List;

import com.entity.Benefit;
import com.entity.Line;


public interface BenefitDAO {
	public void saveBenefit(Benefit objBenefit);
	public List<Benefit> fetchAll();
	public Benefit getBenefitByName(String  name);
	public Benefit getBenefitByNameandPlant(String name);
	public Benefit fetchBenefitById(Long id);
	public String deleteBenefit(long id);
	public Long countBenefit();
}
