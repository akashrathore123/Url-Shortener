package jdbc;

public interface SQLConstants {
    String INSERT_LONG_URL = "Insert into urls(long_url) VALUES(?);";
    String FIND_URL = "Select * from urls where long_url=?;";
    String FIND_ID = "Select id from urls where long_url=?;";
    String DELETE_DATA = "Delete from urls where long_url=?;";
    String INSERT_SHORT_URL = "Update urls set short_url=? where long_url=?;";
    String CHECK_SHORT = "Select * from urls where BINARY short_url=?;";
    String INCREASE_CLICK = "Update urls set clicks = clicks+1 where BINARY short_url = ?;";
    
    
}
