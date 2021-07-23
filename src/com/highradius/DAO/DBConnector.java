package com.highradius.DAO;

import java.util.List;
import java.util.ArrayList;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import com.google.gson.Gson;
import com.highradius.model.json_return;
import com.highradius.model.pojo_ex;

public class DBConnector implements DBConnectorInterface {
	
	private static SessionFactory factory;
	private static ServiceRegistry serviceRegistry;

	DBConnector() {
		initializeFactory();
//		getfact();
	}
//
//	public void getfact() {
//		System.out.println("DBConn working!!!\n\n");
//		SessionFactory factory1;
//		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
//		BeanSessionFactory session_obj = (BeanSessionFactory) context.getBean("BeanSessionFactory");
//		factory1 = session_obj.initializeFactory();
//		DBConnector.factory = factory1; 
//		((AbstractApplicationContext) context).close();
//	}
	
	public void initializeFactory() {
		try {
			if(factory==null) {
				Configuration configuration = new Configuration();
				configuration.configure();
				serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
				factory = configuration.buildSessionFactory(serviceRegistry);
			}
		} catch (Throwable ex) {
			System.err.println("Failed to create sessionFactory object." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}
	
	public long count_total(String title, Integer relyear, String director, int lang)
	{
		Session session = factory.openSession();
	    Transaction tx = null;
	    List<Long> list = new ArrayList<Long>();
	    try {
	         tx = session.beginTransaction(); 
	         StringBuilder my_query = new StringBuilder();
	         my_query.append("SELECT count(*) from pojo_ex where isdel=0 AND ");
	            
	         if(title != null && !title.equals("null") && !title.isEmpty()) 
	            	my_query.append("title='"+title+"' AND ");
	            
	         if(relyear != 0) my_query.append("release_year="+relyear+" AND "); 
	            
	         if(director != null && !director.equals("null") && !director.isEmpty()) 
	            	my_query.append("director='"+director+"' AND ");
	            
	         if(lang != 0) my_query.append("language_id='"+lang+"' AND ");
	                          
	         String final_query = my_query.toString(); 
	         final_query = final_query.substring(0,final_query.length() - 4);
	         
	         Query q = session.createQuery(final_query);
	         list = q.list();
	         tx.commit();
	      } catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      } finally {
	         session.close(); 
	      }
	    return list.get(0);
	}
	
	// Function to show the data onto the grid
	@Override
	public json_return get_all_rows(int start, int limit, String title, Integer relyear, String director, Integer lang) 
	{
		Session session = factory.openSession();
	    Transaction tx = null;
	    json_return obj = new json_return();
	    try 
	    {
            StringBuilder my_query = new StringBuilder();
            my_query.append("SELECT a, b.name from pojo_ex a, lang b where a.lang = b.language_id AND a.isdel=0");
            
            if(title != null && !title.equals("null") && !title.isEmpty()) 
            	my_query.append(" AND a.title='"+title+"'");
            
            if(relyear != 0) my_query.append(" AND a.relyear="+relyear); 
            
            if(director != null && !director.equals("null") && !director.isEmpty()) 
            	my_query.append(" AND a.director='"+director+"'");
            
            if(lang != 0) my_query.append(" AND a.lang='"+lang+"'");
            
            my_query.append(" order by a.film_id");
                          
            String final_query = my_query.toString(); 
            System.out.println(final_query);
            
	         tx = session.beginTransaction(); 
	         //String hql = "from pojo_ex where isdel=0";
	         Query q = session.createQuery(final_query);
	         q.setFirstResult(start); 
	         q.setMaxResults(limit); 
	         
	         @SuppressWarnings("unchecked")
	         List<Object[]> lst = q.list();
	         ArrayList<pojo_ex>rows = new ArrayList<pojo_ex>();
	         
	         for(Object[] o:lst) {
	        	 //System.out.println(o[0]);
	        	 //System.out.println(o[1]);
	        	 ((pojo_ex) o[0]).setLangname((String)(o[1]));
	        	 rows.add((pojo_ex)o[0]);
	         }
	         
	         obj.setPojo(rows);
	         
	         long tot = count_total(title, relyear, director, lang);
	         int tota = (int) tot;
	         obj.setTotal_count(tota);
	         
	         System.out.println(obj);
	         tx.commit();
	      } catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      } finally {
	         session.close(); 
	      }
		return obj;
	}
	
	//Function to delete the data
	@Override
	public void delete_row(String ids) 
	{
		Session session = factory.openSession();
	      Transaction tx = null;
	      try {
	         tx = session.beginTransaction(); 
	         String hql = "Update pojo_ex set isdel=1 where film_id in (" + ids + ")";
	         Query query = session.createQuery(hql);
	         query.executeUpdate();
	         tx.commit();
	      } catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      } finally {
	         session.close(); 
	      }
	}
	
	
	//function to add a row/data in database
	@Override
	public void add_row(String title, Integer relyear, String spefeat, String rating, Integer lang, String director, String desc)
	{
		int now_del = 0;
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			pojo_ex obj = new pojo_ex();
			obj.setTitle(title);
			obj.setRelyear(relyear);
			obj.setSpefeat(spefeat);
			obj.setRating(rating);
			obj.setLang(lang);
			obj.setDirector(director);
			obj.setDesc(desc);
			obj.setIsdel(now_del);
			session.save(obj);
			tx.commit();
		}
		catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}
	
	@Override
	public void edit_row(Integer filmid, String title, Integer relyear, String spefeat, String rating, Integer lang, String director, String desc)
	{
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			pojo_ex obj = (pojo_ex) session.get(pojo_ex.class, filmid);
			
			Gson gson = new Gson();
			String json_obj = gson.toJson(obj);
			System.out.println("Before update: " + json_obj);
			
			obj.setTitle(title);
			obj.setRelyear(relyear);
			obj.setSpefeat(spefeat);
			obj.setRating(rating);
			obj.setLang(lang);
			obj.setDirector(director);
			obj.setDesc(desc);
			
			json_obj = gson.toJson(obj);
			System.out.println("After update: " + obj);
			session.update(obj);
			tx.commit();
		}
		catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}
	
}

//try {
//	String DB_URL = "jdbc:mysql://localhost/sakila";
//	String USER = "root";
//	String PASS = "root";
//	Class.forName("com.mysql.jdbc.Driver");
//	Connection con = DriverManager.getConnection(DB_URL,USER,PASS);
//	String sql = "INSERT INTO film (title, DESCRIPTION, release_year, language_id, director, rating, special_features) VALUES (?, ?, ?, (SELECT language_id FROM LANGUAGE WHERE NAME = ?), ?, ?, ?)";
//	PreparedStatement stmt = con.prepareStatement(sql);
//	stmt.setString(1, title);
//    stmt.setString(2, desc);
//    stmt.setInt(3, relyear);
//    stmt.setString(4, lang);
//    stmt.setString(5, director);
//    stmt.setString(6, rating);
//    stmt.setString(7, spefeat);
//	stmt.execute();
//	con.close();
//}
//catch (Exception e) {  
// System.out.println(e);
//}


//try {
//	String DB_URL = "jdbc:mysql://localhost/sakila";
//	String USER = "root";
//	String PASS = "root";
//	Class.forName("com.mysql.jdbc.Driver");
//	Connection con = DriverManager.getConnection(DB_URL,USER,PASS);
//	PreparedStatement stmt = con.prepareStatement("UPDATE film SET isDeleted = 1 WHERE film_id IN ( "+ ids +" )");
//	stmt.execute();
//	con.close();
//}
//catch (Exception e) {  
// System.out.println(e);
//}




//String[] mult_id = ids.split(",");
//int id_count = mult_id.length;
//Transaction tx = null;
//
//for(int i=0;i<id_count;i++) 
//{
//	String id_to_del = mult_id[i];
//	int int_id = Integer.parseInt(id_to_del);
//	int now_del = 1;
//	try 
//	{
//		Session session = factory.openSession();
//		tx = session.beginTransaction();
//		pojo_ex obj = (pojo_ex) session.get(pojo_ex.class, int_id);
//		
//		obj.setIsdel(now_del);
//		
//		session.update(obj);
//		tx.commit();
//		session.close();
//	}
//	catch (HibernateException e) {
//		if (tx != null)
//			tx.rollback();
//		e.printStackTrace();
//	}
//}




//String DB_URL = "jdbc:mysql://localhost/sakila";
//String USER = "root";
//String PASS = "root";
//final String print_query = "SELECT film_id, title, description, release_year, language_id, "
//		+ "director, rating, special_features, isDeleted FROM film  "
//		+ "WHERE isDeleted = 0 LIMIT ?,?";
//
//final String count_query = "SELECT count(*) FROM film WHERE isDeleted = 0";
//json_return obj = new json_return();
//
//try {
//	Class.forName("com.mysql.jdbc.Driver");
//    Connection con = DriverManager.getConnection(DB_URL,USER,PASS);
//	
//	PreparedStatement stmt = con.prepareStatement(count_query);
//	ResultSet rs = stmt.executeQuery();
//	rs.next();
//	obj.setTotal_count(rs.getInt(1));
//	
//	stmt = con.prepareStatement(print_query);
//	stmt.setInt(1, start);
//	stmt.setInt(2, limit);
//	rs = stmt.executeQuery();
//	List<pojo_ex> inwList = new ArrayList<pojo_ex>();
//	while (rs.next()) 
//    {  
//		pojo_ex data_item = new pojo_ex();
//		data_item.setFilm_id(rs.getInt(1));
//		data_item.setTitle(rs.getString(2));
//		data_item.setDesc(rs.getString(3));
//		data_item.setRelyear(rs.getInt(4));
//		data_item.setLang(rs.getInt(5));
//		data_item.setDirector(rs.getString(6));
//		data_item.setRating(rs.getString(7));
//		data_item.setSpefeat(rs.getString(8));
//		data_item.setIsdel(rs.getInt(9));
//    	inwList.add(data_item);
//    }
//	obj.setPojo(inwList);
//	get_data();
//} catch(Exception e) {
//	System.out.println(e);
//}