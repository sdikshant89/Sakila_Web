package com.highradius.manager;

import com.highradius.model.json_return;

public interface manageInterface {

	json_return get_rows_manager(int start, int limit, String title, Integer relyear, String director, Integer lang);

	void delete_row_manager(String ids);

	void add_row_manager(String title, Integer relyear, String spefeat, String rating, Integer lang, String director,
			String desc);

	void edit_row_manager(Integer filmid, String title, Integer relyear, String spefeat, String rating, Integer lang,
			String director, String desc);

}