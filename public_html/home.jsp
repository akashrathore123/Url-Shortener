<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
  <script src="angular/angular.js" type="text/javascript"></script>
 <script src="angular/pickUrl.js"></script>
 <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
 <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
 <link rel="stylesheet" href="css/home.css">
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
     <link rel="shortcut icon" href="images/burg.png" type="image/x-icon" />
    <title>Shorty | URL Shortener</title>
  </head>
  
  <body ng-app="changeUrl">
  
  <div ng-controller="takeUrl" style="margin-left:10%;">
  <div class="page-header" >
  <h1>Shorty - Short Yours </h1>
</div>
 Paste your long URL here::<div class="input-group" style="width:40%;height:5%;">
 <input type="text" ng-model="long" class="form-control" aria-label="..." >
  <div class="input-group-btn">
    <button type="submit" ng-click="shortUrl()" class="btn btn-danger" value="Get" >Get</button>
  </div>
</div><br>
<h6> <strong>* All Shorty Urls and click analytics are public and can be accessed by anyone.</strong></h6>
 
 <div id="error" ng-show="showError" ng-class="{fade:doFade}" class="alert alert-danger">

        <div id="innerText">
            <strong>{{Error}}</strong>
        </div>
    </div>
 <br>
<div id="short" ng-class="{fade:showShort}"   >
 <div id="mess"  data-placement="top" data-toggle="tooltip"  data-original-title="Press Ctrl-C to copy"> Short URL:
  <div onclick="select('result')">
  <h4 id="result">{{short}}</h4>
  </div>
  </div>
  <div id="details">Created-{{time}}
  <span id="detailPosition"><a href="" id="detailLink">Analytics</a></span>
  <div>
  Clicks-{{clicks}}
  </div>
  </div>
 
  </div>
 
</div>
 <img src="images/homeImage.png" id="homeImage"></img>
</body>

</html>