package model;

import java.util.List;
import java.util.Map;
import util.DBUtil;

public class FoodService {

	private DBUtil db;
	
	public FoodService(){
		db = new DBUtil();
	}
	
	public List<Map<String,String>> getHotFoods(){
		String sql = "select id,foodname,price,picture from food order by hits desc limit 0,3";
		return db.getList(sql);
	}
	
	public List<Map<String,String>> getSpecialFoods(){
		String sql = "select id,foodname,picture,comment from food where comment>0 order by hits desc limit 0,3";
		return db.getList(sql);
	}
	
	public List<Map<String,String>> getRecommFoods(){
		String sql = "select id,foodname,price,picture from food where comment=0 order by hits desc limit 0,3";
		return db.getList(sql);
	}
	
	public Map<String,String> getFood(String id){
		String sql = "select f.*,ft.typename from food f join foodtype ft on f.type = ft.id where f.id=?";
		return db.getMap(sql, new String[]{id});
	}
	
	public List<Map<String,String>> getFoods(String s_fn,String s_type){
		String sql = "select f.*,ft.typename from food f join foodtype ft on f.type = ft.id where 1=1";
		String[] params = null;
		if(s_fn !=null && s_type !=null){
			sql = sql + " and foodname like ? and type like ?";
			params = new String[]{"%"+s_fn+"%","%"+s_type+"%"};
		}else if(s_fn == null && s_type !=null){
			sql = sql + " and type like ?";
			params = new String[]{"%"+s_type+"%"};
		}else if(s_fn !=null && s_type == null){
			sql = sql + " and foodname like ?";
			params = new String[]{"%"+s_fn+"%"};
		}
		sql = sql + " order by hits desc, type asc, f.id asc";
		return db.getList(sql, params);
	}
	
	public Map<String,Object> getFoods(String s_fn,String s_type,String curPage){
		String sql = "select f.*,ft.typename from food f join foodtype ft on f.type = ft.id where 1=1";
		String[] params = null;
		if(s_fn !=null && s_type !=null){
			sql = sql + " and foodname like ? and type like ?";
			params = new String[]{"%"+s_fn+"%","%"+s_type+"%"};
		}else if(s_fn == null && s_type !=null){
			sql = sql + " and type like ?";
			params = new String[]{"%"+s_type+"%"};
		}else if(s_fn !=null && s_type == null){
			sql = sql + " and foodname like ?";
			params = new String[]{"%"+s_fn+"%"};
		}
		sql = sql + " order by hits desc, type asc, f.id asc";
		if(curPage==null){
			curPage = "1";
		}
		return db.getPage(sql, params, curPage);
	}
	
	public int editFood(String foodname,String feature,String material,
			String price,String type,String picture,String comment,String id){
		String sql="update food set foodName=?,feature=?,material=?,price=?,type=?";
		if(picture != null){
			sql=sql+",picture=?";
		}
		sql=sql+",comment=? where id=?";
		if(picture != null){
			return db.update(sql, new String[]{foodname,feature,
					material,price,type,picture,comment,id});
		}else{
			return db.update(sql, new String[]{foodname,feature,
					material,price,type,comment,id});
		}
	}
	
}
