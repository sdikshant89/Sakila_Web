package com.highradius.model;

import java.util.List;

public class json_return {
	@Override
	public String toString() {
		return "json_return [total_count=" + total_count + ", pojo=" + pojo + "]";
	}
	private int total_count;
	private List<pojo_ex> pojo;
	
	public int getTotal_count() {
		return total_count;
	}
	public void setTotal_count(int total_count) {
		this.total_count = total_count;
	}
	public List<pojo_ex> getPojo() {
		return pojo;
	}
	public void setPojo(List<pojo_ex> pojo) {
		this.pojo = pojo;
	}
}
