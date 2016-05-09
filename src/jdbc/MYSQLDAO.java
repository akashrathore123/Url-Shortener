package jdbc;

import com.mysql.jdbc.Connection;

import com.mysql.jdbc.PreparedStatement;

import com.mysql.jdbc.ResultSet;

import dto.AnalyticsDTO;
import dto.ClicksDTO;
import dto.GeoDTO;
import dto.UrlDTO;

import java.sql.DriverManager;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

public  class MYSQLDAO implements DAO, DBConstants{
	public Connection getConnection() throws ClassNotFoundException, SQLException
	{
		ResourceBundle rb = ResourceBundle.getBundle("db");
		String driverName = rb.getString("drivername");
		Class.forName(driverName);
		
		String url = rb.getString("url");
		String userid = rb.getString("userid");
		String password = rb.getString("pwd");
		
		Connection con = (Connection)DriverManager.getConnection(url,userid,password);
               
                return con;
		
	}
	@Override
	public int cud(String sql, List<PreparedStatementDTO> psList) throws ClassNotFoundException, SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		int rowCount = 0;
		try
		{
			con = getConnection();
			pstmt = (PreparedStatement)con.prepareStatement(sql);
			if(psList!=null && psList.size()>0){
				for(PreparedStatementDTO psDTO : psList){
					if(psDTO.getDatatype()==STRING){
				
						pstmt.setString(psDTO.getPosition(), psDTO.getValue().toString());
					}
					else
					if(psDTO.getDatatype()==INTEGER){
						pstmt.setInt(psDTO.getPosition(), (Integer)psDTO.getValue());
						
					}
					else
						if(psDTO.getDatatype()==DOUBLE){
							
							pstmt.setDouble(psDTO.getPosition(), (Double)psDTO.getValue());
							
						}
				}
			}
			rowCount = pstmt.executeUpdate();
		}
		finally
		{
			if(pstmt!=null){
				pstmt.close();
			}
			if(con!=null){
				con.close();
			}
		}
		return rowCount;
	}

    public void insertUrl(String sql, String url) throws ClassNotFoundException, SQLException {
       
        Connection con =null;
        PreparedStatement pstmt = null;
       
        try{
         
            con = getConnection();
          
            pstmt = (PreparedStatement)con.prepareStatement(sql);
              
            pstmt.setString(1,url);
        
            pstmt.execute();
           
          
           
            }
        finally{
                     if(pstmt!=null){
                             pstmt.close();
                     }
                     if(con!=null){
                             con.close(); 
                 }
      
    }
}

    public UrlDTO findUrl(String sql, String url) throws ClassNotFoundException, SQLException {
        
        UrlDTO urlDTO = null;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try{
            
            con = getConnection();
            ps = (PreparedStatement)con.prepareStatement(sql);
                  ps.setString(1,url);
            rs = (ResultSet)ps.executeQuery();
            if(rs.next()){
                urlDTO = new UrlDTO();
                urlDTO.setShortUrl("localhost:7101/Shorty/"+rs.getString("short_url"));
                urlDTO.setClicks(rs.getInt("clicks"));
                urlDTO.setTime(rs.getTimestamp("time"));
                urlDTO.setId(rs.getInt("id"));
                return urlDTO;
                }
            }
        finally{
            if(ps!=null){
                ps.close();
                       }
            if(con!=null){
                con.close();
                    
                        }
               }
        return urlDTO;
    }

    public int findId(String sql, String url) throws ClassNotFoundException,SQLException {
                                                     
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs;
        int id = 0;
        
        try{
            con = getConnection();
            ps = (PreparedStatement)con.prepareStatement(sql);
            ps.setString(1, url);
            rs = (ResultSet)ps.executeQuery();
            if(rs.next()){
            id = rs.getInt("id");
            return id;
            }
        }
        finally{
            if(ps!=null){
                ps.close();
                }
            if(con!=null){
                con.close();
                }
            }
        return id;
    }

    public void deleteData(String sql, String url) throws SQLException,ClassNotFoundException {
                                                          
        Connection con = null;
        PreparedStatement ps = null;
        
        try{
            con = getConnection();
            ps = (PreparedStatement)con.prepareStatement(sql);
            ps.setString(1, url);
            ps.execute();
            
            }
        finally{
            if(con!=null){
                con.close();
                }
            if(ps!=null){
                ps.close();
                }
            }
        return;
    }

    public UrlDTO checkShort(String sql, String url) throws SQLException, ClassNotFoundException {
                                                           
        UrlDTO dto = null;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs;
        try{
            con = getConnection();
            ps = (PreparedStatement)con.prepareStatement(sql);
            ps.setString(1, url);
            rs = (ResultSet)ps.executeQuery();
            
            if(rs.next()){
                dto = new UrlDTO();
                dto.setId(rs.getInt("id"));
                dto.setLongUrl(rs.getString("long_url"));
                dto.setClicks(rs.getInt("clicks"));
                dto.setTime(rs.getTimestamp("time"));
                dto.setShortUrl(rs.getString("short_url"));
                return dto;
                }
        
            }
        finally{
            if(con!=null){
                con.close();
                }
            if(ps!=null){
                ps.close();
                }
            }
        return null;
    }

    public List<AnalyticsDTO> getAnalyticsData(String query) throws SQLException, ClassNotFoundException {
                                                                   
        List<AnalyticsDTO> dataSet = new ArrayList<AnalyticsDTO>();
        AnalyticsDTO data = null;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs;
        try{
            con = getConnection();
            ps = (PreparedStatement)con.prepareStatement(query);
            rs = (ResultSet)ps.executeQuery();
            while(rs.next()){
                data = new AnalyticsDTO();
                data.setBrowser(rs.getString("browser"));
                data.setPlatform(rs.getString("platform"));
                data.setRegion(rs.getString("region"));
                data.setTime(rs.getTimestamp("time"));
                dataSet.add(data);
                
                }
            }
        finally{
            if(ps!=null){
                ps.close();
                }
            if(con!=null){
                con.close();
                }
        }
        
        return dataSet;
    }

    public List<GeoDTO> getGeoData(String sql) throws ClassNotFoundException,
                                                      SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs;
        List<GeoDTO> geoDataList = new ArrayList<GeoDTO>();
        GeoDTO geoData = null;
        
        try{
            con = getConnection();
            ps = (PreparedStatement)con.prepareStatement(sql);
            rs = (ResultSet)ps.executeQuery();
            while(rs.next()){
                geoData = new GeoDTO();
                geoData.setCountry(rs.getString("region"));
                geoData.setCount(rs.getInt(2));
                geoDataList.add(geoData);
                }
            
            }
        finally{
            if(ps!=null){
                ps.close();
                }
            if(con!=null){
                con.close();
                }
        }
        return geoDataList;
    }
    public String monthName(int mon){
        String monthString = "";
            switch(mon) {
                       case 1:  monthString = "Jan";
                                break;
                       case 2:  monthString = "Feb";
                                break;
                       case 3:  monthString = "Mar";
                                break;
                       case 4:  monthString = "Apr";
                                break;
                       case 5:  monthString = "May";
                                break;
                       case 6:  monthString = "Jun";
                                break;
                       case 7:  monthString = "Jul";
                                break;
                       case 8:  monthString = "Aug";
                                break;
                       case 9:  monthString = "Sep";
                                break;
                       case 10: monthString = "Oct";
                                break;
                       case 11: monthString = "Nov";
                                break;
                       case 12: monthString = "Dec";
                                break;
        }
        return monthString;
        }
    
    public List<ClicksDTO> getClickData(String sql) throws ClassNotFoundException,
                                                        SQLException {
       
        
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs;
        List<ClicksDTO> clickDataList = new ArrayList<ClicksDTO>();
        ClicksDTO clickData = null;
        
        try{
            con = getConnection();
            ps = (PreparedStatement)con.prepareStatement(sql);
            rs = (ResultSet)ps.executeQuery();
            while(rs.next()){
                clickData = new ClicksDTO();
                int mon = rs.getInt("month");
                clickData.setYear(rs.getInt("year"));
              
                clickData.setCount(rs.getInt("counts"));
                clickData.setTime(monthName(mon)+","+clickData.getYear());
                clickDataList.add(clickData);
                }
            
            }
        finally{
            if(ps!=null){
                ps.close();
                }
            if(con!=null){
                con.close();
                }
        }
        return clickDataList;
    }

}
