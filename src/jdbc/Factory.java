package jdbc;

import java.util.ResourceBundle;
import jdbc.MYSQLDAO;
public class Factory {
	public static DAO getDAOObject(){
	DAO dao=null;	
	ResourceBundle rb=ResourceBundle.getBundle("db");
	String database=rb.getString("dbtype");
	if(database.equals("mysql")){
		dao = new MYSQLDAO();
	}
	return dao;
	}
}
