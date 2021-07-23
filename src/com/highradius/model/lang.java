package com.highradius.model;

public class lang {
	Integer language_id;
	String name;
	
	@Override
	public String toString() {
		return "lang [language_id=" + language_id + ", name=" + name + "]";
	}
	
	public Integer getLanguage_id() {
		return language_id;
	}
	public void setLanguage_id(Integer language_id) {
		this.language_id = language_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
