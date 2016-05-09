package dto;

import java.sql.Timestamp;

public class UrlDTO {
   
   private int id;
   private String longUrl;
   private String shortUrl;
   private Timestamp time;
   private int clicks;
   
    public int getId() {
            return id;
    }
    public void setId(int id) {
            this.id = id;
    }
    public String getLongUrl() {
            return longUrl;
    }
    public void setLongUrl(String longUrl) {
            this.longUrl = longUrl;
    }
    public String getShortUrl() {
            return shortUrl;
    }
    public void setShortUrl(String shortUrl) {
            this.shortUrl = shortUrl;
    }
    public Timestamp getTime() {
            return time;
    }
    public void setTime(Timestamp time) {
            this.time = time;
    }
    public int getClicks() {
            return clicks;
    }
    public void setClicks(int clicks) {
            this.clicks = clicks;
    }
       
   
}
