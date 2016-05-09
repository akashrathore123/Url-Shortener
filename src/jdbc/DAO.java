package jdbc;

import dto.AnalyticsDTO;
import dto.ClicksDTO;
import dto.GeoDTO;
import dto.UrlDTO;

import java.sql.SQLException;
import java.util.List;

public interface DAO {
    public int cud(String sql, List<PreparedStatementDTO> psList) throws ClassNotFoundException, SQLException;
    public void insertUrl(String sql, String url) throws ClassNotFoundException, SQLException;
    public UrlDTO findUrl(String sql, String url) throws ClassNotFoundException, SQLException;
    public int findId(String sql,String url)  throws ClassNotFoundException, SQLException;
    public void deleteData(String sql,String url) throws ClassNotFoundException, SQLException;
    public UrlDTO checkShort(String sql, String url) throws ClassNotFoundException, SQLException;
    public List<AnalyticsDTO> getAnalyticsData(String query) throws ClassNotFoundException, SQLException;
    public List<GeoDTO> getGeoData(String sql) throws ClassNotFoundException, SQLException;
    public List<ClicksDTO> getClickData(String sql) throws ClassNotFoundException, SQLException;
}
