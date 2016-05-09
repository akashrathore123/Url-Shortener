package filter;

import dto.UrlDTO;

import java.io.IOException;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Enumeration;

import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;

import jdbc.DAO;
import jdbc.DBConstants;
import jdbc.Factory;
import jdbc.PreparedStatementDTO;
import jdbc.SQLConstants;

//import net.sf.uadetector.service.UADetectorServiceFactory;


import org.apache.turbine.util.BrowserDetector;


public class Redirect implements Filter {
    public Redirect() {
        super();
    }

    public void init(FilterConfig filterConfig) throws ServletException {
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,FilterChain filterChain) throws IOException,ServletException {
     
       
        String url = ((HttpServletRequest)servletRequest).getRequestURL().toString(); 
        String[] urlSplitted = url.split("/");
        String key = urlSplitted[urlSplitted.length-1];
        StringBuffer rest = new StringBuffer();
        
        
        for(int i=0;i<urlSplitted.length-1;i++){
            rest.append(urlSplitted[i]+"/");
            }
      
        if((key.matches("check")||key.matches("home.jsp")||key.matches("error500.jsp")||key.matches("error404.jsp")||rest.toString().matches("http://localhost:7101/Shorty/angular/") || rest.toString().matches("http://localhost:7101/Shorty/css/")|| key.matches("analytics.jsp") || rest.toString().matches("http://localhost:7101/Shorty/images/")) || rest.toString().matches("http://localhost:7101/Shorty/analytics/") || rest.toString().matches("http://localhost:7101/Shorty/clicksAnalytics/")) {
                
                filterChain.doFilter(servletRequest, servletResponse);
            }
        else{
       DAO dao = Factory.getDAOObject();
           
            try{
                UrlDTO urlDto = null;
            
                 urlDto = dao.checkShort(SQLConstants.CHECK_SHORT,key);
          
                if(urlDto == null){
              
                    ((HttpServletResponse)servletResponse).sendRedirect("error404.jsp");
                    }
                else{
                       
                    String userAgent = ((HttpServletRequest)servletRequest).getHeader("User-Agent");
                    String browser = null;
                    String ipAddress = null;
                    String platform = null;
                    String region = "India";
                   
                    BrowserDetector bd =new BrowserDetector(userAgent);
                   
                    if(userAgent.contains("Chrome")){
                        browser = "Chrome";
                        }
                    else{
                        if(userAgent.contains("MSIE")){
                            browser = "IE";
                        }else{
                            browser = bd.getBrowserName();
                            }
                        }
                    platform = bd.getBrowserPlatform();
                   
                    ipAddress = ((HttpServletRequest)servletRequest).getHeader("X-FORWARDED-FOR");  
                         if (ipAddress == null) {  
                                 ipAddress = servletRequest.getRemoteAddr();  
                         }
                       /* GeoLocation gl = new GeoLocation();
                        gl.GetGeoLocationByIP(ipAddress);
                        String country = gl.Country;
                    */
                    String sql = "Insert into a"+urlDto.getId()+"(browser,region,platform) Values(?,?,?);";
                 
                    List<PreparedStatementDTO> psList = new ArrayList<PreparedStatementDTO>();
                    PreparedStatementDTO ps = new PreparedStatementDTO();
                    ps.setDatatype(DBConstants.STRING);
                    ps.setPosition(1);
                    ps.setValue(browser);
                    psList.add(ps);
                    
                    ps = new PreparedStatementDTO();
                        ps.setDatatype(DBConstants.STRING);
                        ps.setPosition(2);
                        ps.setValue(region);
                        psList.add(ps);
                        
                    ps = new PreparedStatementDTO();
                        ps.setDatatype(DBConstants.STRING);
                        ps.setPosition(3);
                        ps.setValue(platform);
                        psList.add(ps);
                    try{
                        
                        dao.cud(sql,psList);
                        
                        psList = new ArrayList<PreparedStatementDTO>();
                        ps = new PreparedStatementDTO();
                        ps.setDatatype(DBConstants.STRING);
                        ps.setPosition(1);
                        ps.setValue(key);
                        psList.add(ps);
                        dao.cud(SQLConstants.INCREASE_CLICK,psList);
                    }
                    catch(SQLException ex){
                         ex.printStackTrace();
                         ((HttpServletResponse)servletResponse).setStatus(500);
                         return;
                             }
                    catch(ClassNotFoundException ex){
                        ex.printStackTrace();
                            ((HttpServletResponse)servletResponse).setStatus(500);
                            return;
                        }
                    ((HttpServletResponse)servletResponse).sendRedirect(urlDto.getLongUrl());
                    }
        }
            catch(SQLException e){
                e.printStackTrace();
                ((HttpServletResponse)servletResponse).sendError(500);
                }
            catch(ClassNotFoundException e){
                
                    e.printStackTrace();
                    ((HttpServletResponse)servletResponse).sendError(500);
                }
        }
    }

    public void destroy() {
    }
}
