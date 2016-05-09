package servlets;

import com.google.gson.Gson;

import dto.ClicksDTO;
import dto.GeoDTO;

import dto.UrlDTO;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.*;
import javax.servlet.http.*;

import jdbc.DAO;
import jdbc.Factory;
import jdbc.SQLConstants;

public class ClicksAnalytics extends HttpServlet {
    private static final String CONTENT_TYPE = "text/html; charset=windows-1252";


    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
      this.doPost(request, response);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws IOException {
        StringBuffer url = request.getRequestURL();
        String[] urlArray = url.toString().split("/");
        String shortUrl = urlArray[urlArray.length-1];
        
        PrintWriter out = response.getWriter();
        DAO dao = Factory.getDAOObject();
        UrlDTO dto = null;
        try {
            dto = dao.checkShort(SQLConstants.CHECK_SHORT, shortUrl);
        
        if(dto == null){
            response.setStatus(404);
            return;
            }
       List<ClicksDTO> clicksData = new ArrayList<ClicksDTO>();
      
       String sql = "Select month((time)) AS month,year(time) AS year,COUNT(*) AS counts from a"+dto.getId()+" group by day((time)) order by year;";
       clicksData = dao.getClickData(sql);
            Gson gson = new Gson();
            out.print(gson.toJson(clicksData));
            return;
        
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
            response.setStatus(500);
        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(500);
        }
    }
}
