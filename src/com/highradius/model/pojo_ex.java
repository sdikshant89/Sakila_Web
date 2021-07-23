package com.highradius.model;

public class pojo_ex {
	
	private Integer film_id;
	
	private String title;
	
    private String desc;
	
    private Integer relyear;
	
    private Integer lang;
	
    private String rating;
	
    private String spefeat;
	
    private String director;
	
	private Integer isdel;
	
	private String langname;
	
	public Integer getFilm_id() {
		return film_id;
	}
	public void setFilm_id(Integer film_id) {
		this.film_id = film_id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public Integer getRelyear() {
		return relyear;
	}
	public void setRelyear(Integer relyear) {
		this.relyear = relyear;
	}
	public Integer getLang() {
		return lang;
	}
	public void setLang(Integer lang) {
		this.lang = lang;
	}
	public String getRating() {
		return rating;
	}
	public void setRating(String rating) {
		this.rating = rating;
	}
	public String getSpefeat() {
		return spefeat;
	}
	public void setSpefeat(String spefeat) {
		this.spefeat = spefeat;
	}
	public String getDirector() {
		return director;
	}
	public void setDirector(String director) {
		this.director = director;
	}
	public Integer getIsdel() {
		return isdel;
	}
	public void setIsdel(Integer isdel) {
		this.isdel = isdel;
	}
	public String getLangname() {
		return langname;
	}
	public void setLangname(String langname) {
		this.langname = langname;
	}
	@Override
	public String toString() {
		return "pojo_ex [film_id=" + film_id + ", title=" + title + ", desc=" + desc + ", relyear=" + relyear
				+ ", lang=" + lang + ", rating=" + rating + ", spefeat=" + spefeat + ", director=" + director
				+ ", isdel=" + isdel + ", langname=" + langname + "]";
	}
}

//
//public pojo_ex() {
//	super();
//}
//
//public pojo_ex(Integer filmid, String title, String desc, Integer relyear, String lang, String rating, String spefeat, String dir) {
//	super();
//	this.film_id = filmid;
//	this.title = title;
//	this.desc = desc;
//	this.relyear = relyear;
//	this.lang = lang;
//	this.rating = rating;
//	this.spefeat = spefeat;
//	this.director = dir;
//}
