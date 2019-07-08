package com.wjx.cache.bean;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Department {
	
	private Integer id;
	private String departmentName;

	public Department() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Department(Integer id, String departmentName) {
		super();
		this.id = id;
		this.departmentName = departmentName;
	}
	@Override
	public String toString() {
		return "Department [id=" + id + ", departmentName=" + departmentName + "]";
	}
	
	
	
	

}
