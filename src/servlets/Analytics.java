package servlets;

import com.google.gson.Gson;

import dto.AnalyticsDTO;

import dto.GeoDTO;
import dto.UrlDTO;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.SQLException;

import java.util.ArrayList;

import java.util.List;

import java.util.Map;

import javax.servlet.*;
import javax.servlet.http.*;

import jdbc.DAO;
import jdbc.Factory;
import jdbc.SQLConstants;

public class Analytics extends HttpServlet {
    private static final String CONTENT_TYPE = "text/html; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       this.doPost(request,response);
       
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      
        PrintWriter out = response.getWriter();
        DAO dao = Factory.getDAOObject();
        StringBuffer url = request.getRequestURL();
       
        String[] urlArray = url.toString().split("/");
        String shortUrl = urlArray[urlArray.length-1];

        try {
            UrlDTO dto = null;
            dto = dao.checkShort(SQLConstants.CHECK_SHORT, shortUrl);
            if(dto == null){
                response.setStatus(404);
                return;
                }
           List<GeoDTO> geoDto = new ArrayList<GeoDTO>();
            String query = "Select region,count(*) from a"+dto.getId()+" group by region";
            geoDto = dao.getGeoData(query);
            Gson gson = new Gson();
            
            out.println(gson.toJson(geoDto));
            return;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            response.setStatus(500);
            
        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(500);
            
        }
    }
}
