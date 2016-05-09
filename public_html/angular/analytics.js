

var app = angular.module('loadAnalytics',[]);
app.controller('showAnalytics',function($scope,$http,$window){
  
    
 $scope.short = getUrlParameter('key');
 if($scope.short=="" || $scope.short==true || $scope.short===undefined){
    $window.location = "error404.jsp"
 }else{
 $http({
      method:"post",
      url:"analytics/"+$scope.short
      
 }).
 success(function(data,status,headers,config){
 
      geographicalData(data);
      clickTimeData();
 })
.error(function(data,status,headers,config){
   if(status==404){
    $window.location = "error404.jsp";
   }
   $window.location = "error500.jsp"
});
 }
 
  
 
function getUrlParameter(sParam) {
    var sPageURL = decodeURIComponent(window.location.search.substring(1)),
        sURLVariables = sPageURL.split('&'),
        sParameterName,
        i;

    for (i = 0; i < sURLVariables.length; i++) {
        sParameterName = sURLVariables[i].split('=');

        if (sParameterName[0] === sParam) {
            return sParameterName[1] === undefined ? true : sParameterName[1];
        }
    }
}

function geographicalData(data){

 var geoData = [];
      geoData.push(['Country','Clicks']);
      for(var i=0;i<data.length;i++){
      
         geoData.push([data[i].country,data[i].count]);
       
      }

      google.charts.load('current', {'packages':['geochart','line']});
      google.charts.setOnLoadCallback(drawRegionsMap);
     
      function drawRegionsMap() {
  
      dataTable = new google.visualization.DataTable();
      var noOfColumn = geoData[0].length;
      var noOfRows = geoData.length;
      
          dataTable.addColumn('string','Country' );
         
            dataTable.addColumn('number', 'Clicks');           

          for (var i = 1; i <= noOfRows; i++)
            dataTable.addRow(geoData[i]);            


        var options = { colorAxis: {colors: ['#ffffff', '#3399ff']} };
         var chart = new google.visualization.GeoChart(document.getElementById('regions_div'));
        chart.draw(dataTable, options);
        }
}

function clickTimeData(){
$http({
        method:"post",
        url:"clicksAnalytics/"+$scope.short 
 })
 .success(function(data,status,headers,config){
 alert(data);
      var clicksData = [];
  clicksData.push(['Time','Clicks']);
  
   for(var i=0;i<data.length;i++){
      
         clicksData.push([data[i].time,data[i].count]);
       
      }
  
    //  google.charts.load('current', {'packages':['line']});
      google.charts.setOnLoadCallback(drawChart);

    function drawChart() {

      var dataTable = new google.visualization.DataTable();
     dataTable.addColumn('string', 'Time');
     dataTable.addColumn('number', 'Clicks');

      for (var i = 1; i <= clicksData.length; i++)
            dataTable.addRow(clicksData[i]);   

    console.log(clicksData);
      var options = {
        chart: {
          title: 'Clicks over time:',
          subtitle: ''
        },
        width: 850,
        height: 400
      };

      var chart = new google.charts.Line(document.getElementById('click_chart'));

      chart.draw(dataTable, options);
    }
 })
  .error(function(data,status,headers,config){
        if(status==404){
     $window.location = "error404.jsp";
        }
     $window.location = "error500.jsp"
  });
}
});