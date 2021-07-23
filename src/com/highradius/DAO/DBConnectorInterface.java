package com.highradius.DAO;

import com.highradius.model.json_return;

public interface DBConnectorInterface {

	// Function to show the data onto the grid
	json_return get_all_rows(int start, int limit, String title, Integer relyear, String director, Integer lang);

	//Function to delete the data
	void delete_row(String ids);

	//function to add a row/data in database
	void add_row(String title, Integer relyear, String spefeat, String rating, Integer lang, String director,
			String desc);

	void edit_row(Integer filmid, String title, Integer relyear, String spefeat, String rating, Integer lang,
			String director, String desc);

}