package com.highradius.action;

import com.highradius.manager.manageInterface;
import com.highradius.model.json_return;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class get_data{
	
	private int page;
	private int limit;
	private String id_s;
	private String title;
	private Integer relyear = 0;
	private String spefeat;
	private String rating;
	private Integer lang = 0;
	private String director;
	private String desc;
	
	private json_return jsonData;
	
	public String getId_s() {
		return id_s;
	}
	public void setId_s(String id_s) {
		this.id_s = id_s;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Integer getRelyear() {
		return relyear;
	}
	public void setRelyear(Integer relyear) {
		this.relyear = relyear;
	}
	public String getSpefeat() {
		return spefeat;
	}
	public void setSpefeat(String spefeat) {
		this.spefeat = spefeat;
	}
	public String getRating() {
		return rating;
	}
	public void setRating(String rating) {
		this.rating = rating;
	}
	public Integer getLang() {
		return lang;
	}
	public void setLang(Integer lang) {
		this.lang = lang;
	}
	public String getDirector() {
		return director;
	}
	public void setDirector(String director) {
		this.director = director;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public json_return getJsonData() {
		return jsonData;
	}
	public void setJsonData(json_return jsonData) {
		this.jsonData = jsonData;
	}
	
	public String execute() {
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		manageInterface manager_obj = (manageInterface) context.getBean("Manager");
		int first_data=((page-1)*limit);
		json_return json_obj = manager_obj.get_rows_manager(first_data, limit, title, relyear, director, lang);
		setJsonData(json_obj);
		((AbstractApplicationContext) context).close();
		return "success";
	}
	
	public void delete_data() {
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		manageInterface manager_obj = (manageInterface) context.getBean("Manager");
		manager_obj.delete_row_manager(id_s);
		((AbstractApplicationContext) context).close();
	}
	
	public void add_data() {
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		manageInterface manager_obj = (manageInterface) context.getBean("Manager");
		manager_obj.add_row_manager(title, relyear, spefeat, rating, lang, director, desc);
		((AbstractApplicationContext) context).close();
	}
	
	public void edit_data() {
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		manageInterface manager_obj = (manageInterface) context.getBean("Manager");
		int filmid = Integer.parseInt(id_s);  
		manager_obj.edit_row_manager(filmid ,title, relyear, spefeat, rating, lang, director, desc);
		((AbstractApplicationContext) context).close();
	}
}



//package com.highradius.action;
//
//import java.sql.*; 
//import java.util.ArrayList;
//import com.highradius.model.lang;
//import com.highradius.model.pojo_ex;
//
//public class get_lang {
//	
//	private lang langlist;
//
//	public lang getLanglist() {
//		return langlist;
//	}
//
//	public void setLanglist(lang langlist) {
//		this.langlist = langlist;
//	}
//
//	public String execute() {
//		try {
//			String DB_URL = "jdbc:mysql://localhost/sakila";
//			String USER = "root";
//			String PASS = "root";
//			Class.forName("com.mysql.jdbc.Driver");
//			lang lanlist = new lang();
//			Connection con = DriverManager.getConnection(DB_URL,USER,PASS);
//			PreparedStatement stmt = con.prepareStatement("SELECT DISTINCT NAME FROM LANGUAGE");     
//			ResultSet rs = stmt.executeQuery();
//			ArrayList<pojo_ex> inwList = new ArrayList<>();
//			
//			while (rs.next()) 
//            {  
//				pojo_ex data_item = new pojo_ex();
//				data_item.setLang(rs.getString("name"));
//				inwList.add(data_item);
//            }
//			lanlist.setLang(inwList);
//			setLanglist(lanlist);
//			con.close();
//			return "success";
//		}
//		catch (Exception e) 
//        {  
//         System.out.println(e);
//         return "faliure";
//     }
//	}
//}
//
