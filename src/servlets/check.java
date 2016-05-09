package servlets;

import com.google.gson.Gson;

import dto.UrlDTO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.SQLClientInfoException;

import org.apache.commons.validator.routines.UrlValidator;
import java.sql.SQLException;

import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.*;
import javax.servlet.http.*;

import jdbc.DAO;
import jdbc.DBConstants;
import jdbc.Factory;
import jdbc.MYSQLDAO;
import jdbc.PreparedStatementDTO;
import jdbc.SQLConstants;

public class check extends HttpServlet {
    static char map[]={'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','0','1','2','3','4','5','6','7','8','9'};
    private static final String CONTENT_TYPE = "text/html; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      this.doPost(request, response);
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            
    
        PrintWriter out = response.getWriter();   
        StringBuffer sb = new StringBuffer();
        try 
                          {
                            BufferedReader reader = request.getReader();
                            String line = null;
                            while ((line = reader.readLine()) != null)
                            {
                              sb.append(line);
                            }
                          } catch (Exception e) {
                              e.printStackTrace(); 
                              response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                              out.println("*Service not available.");
                              return;
                             
                          }
        String longUrl = sb.toString();
        String shortUrl = null; 
        if(longUrl.isEmpty()){
              response.sendError(400,"* Enter URL");
              return;
            }
        UrlValidator validator = new UrlValidator();
        if(validator.isValid(longUrl)){
          
          DAO dao =Factory.getDAOObject();
         
           
            try {
                UrlDTO urlDTO = new UrlDTO();
                urlDTO = dao.findUrl(SQLConstants.FIND_URL,longUrl);
                
                if(urlDTO!=null){
                       Gson gson = new Gson();
                       out.print(gson.toJson(urlDTO));    
                       return;
                    }
                else{
                
                int id = 0;
                dao.insertUrl(SQLConstants.INSERT_LONG_URL,longUrl);
                id = dao.findId(SQLConstants.FIND_ID,longUrl);
                int tableName = id;
                    int count = 0;
                    try{
                        Integer arr[] = new Integer[6];
                        for(int i=0;id>0;i++){
                            arr[i]=id%62;
                            id/=62;
                           count++;
                            }
                    
                       int i = 0;
                       StringBuffer short_url = new StringBuffer();
                        while(i<count){
                             short_url.append(map[arr[i]]);
                                i++;
                            }
                        shortUrl = short_url.toString();
                      
                        List<PreparedStatementDTO> psList = new ArrayList<PreparedStatementDTO>();
                       
                        PreparedStatementDTO ps = new PreparedStatementDTO();
                        ps.setDatatype(DBConstants.STRING);
                        ps.setPosition(1);
                        ps.setValue(shortUrl);
                        psList.add(ps);
                       
                        ps = new PreparedStatementDTO();
                        ps.setDatatype(DBConstants.STRING);
                        ps.setPosition(2);
                        ps.setValue(longUrl);
                        psList.add(ps);
                        
                        dao.cud(SQLConstants.INSERT_SHORT_URL, psList);
                        urlDTO = new UrlDTO();
                        urlDTO.setClicks(0);
                        urlDTO.setShortUrl("localhost:7101/Shorty/"+shortUrl);
                        Date date = new Date();
                        urlDTO.setTime(new Timestamp(date.getTime()));
                       
                        psList = null;
                        
                        String CREATE_TABLE = "CREATE  TABLE a"+tableName+"(`id` INT NOT NULL AUTO_INCREMENT, browser varchar(25),region varchar(40),"
                                              +" platform varchar(30),time Timestamp,PRIMARY KEY (`id`) ,  UNIQUE INDEX `id_UNIQUE` "
                                              +" (`id` ASC) ) ENGINE = InnoDB DEFAULT CHARACTER SET = big5;";
                          
                        dao.cud(CREATE_TABLE, psList);
                            Gson gson = new Gson();
                            out.print(gson.toJson(urlDTO)); 
                            return;
                        }
                        catch(Exception e){
                        e.printStackTrace();
                        dao.deleteData(SQLConstants.DELETE_DATA,longUrl);
                        response.sendError(500);
                        }
                }
            } catch (ClassNotFoundException ex){
                ex.printStackTrace();
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                
                return;
            }catch (SQLException ex){
                ex.printStackTrace();
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
               
                return;
            }
            finally{
             out.close();
             }
        }else{
            response.sendError(400,"* This URL cannot be shortened.");
            out.close();
            return;
       
            }
}
}