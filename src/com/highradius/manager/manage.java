package com.highradius.manager;

import com.highradius.DAO.DBConnectorInterface;
import com.highradius.model.json_return;


public class manage implements manageInterface {
	
	private DBConnectorInterface DB_obj;
	
	public DBConnectorInterface getDB_obj() {
		return DB_obj;
	}

	public void setDB_obj(DBConnectorInterface dB_obj) {
		DB_obj = dB_obj;
	}

	@Override
	public json_return get_rows_manager(int start, int limit, String title, Integer relyear, String director, Integer lang)
	{
		json_return json_obj = DB_obj.get_all_rows(start, limit, title, relyear, director, lang);
		return json_obj;
	}
	
	@Override
	public void delete_row_manager(String ids) 
	{
		DB_obj.delete_row(ids);
	}
	
	@Override
	public void add_row_manager(String title, Integer relyear, String spefeat, String rating, Integer lang, String director, String desc) 
	{
		DB_obj.add_row(title, relyear, spefeat, rating, lang, director, desc);
	}
	
	@Override
	public void edit_row_manager(Integer filmid, String title, Integer relyear, String spefeat, String rating, Integer lang, String director, String desc) 
	{
		DB_obj.edit_row(filmid, title, relyear, spefeat, rating, lang, director, desc);
	}
}
