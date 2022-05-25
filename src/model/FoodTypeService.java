package model;

import java.util.List;
import java.util.Map;
import util.DBUtil;

public class FoodTypeService {

	private DBUtil db;
	
	public FoodTypeService(){
		db = new DBUtil();
	}
	
	public List<Map<String,String>> getAllTypes(){
		 String sql ="select * from foodtype";
		 return db.getList(sql);
	}
}
