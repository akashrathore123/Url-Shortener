<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
     <link rel="shortcut icon" href="images/burg.png" type="image/x-icon" />
     
    <title>Shorty || Analytics</title>
           <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
           <script type="text/javascript" src="https://www.google.com/jsapi"></script>
           <script src="angular/angular.js" type="text/javascript"></script>
           <script src="angular/analytics.js"></script>
           <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
           <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
           <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
           <link rel="stylesheet" href="css/home.css">
  </head>
  <body ng-app="loadAnalytics">
  
  <div ng-controller="showAnalytics" ><br>
   <div class="page-header" style="margin-left:10%;" >
  <h1>Shorty - Short Yours </h1>
</div>
  <h3>Countries</h3>
  <div id="regions_div" style="width:850px; height:250px;"></div>
  </div>
   <h3>Clicks</h3>
  <div id="click_chart" style="width:850px; height:250px;"></div>
  </body>
</html>