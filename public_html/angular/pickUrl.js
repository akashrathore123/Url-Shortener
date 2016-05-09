

var app = angular.module('changeUrl',[]);
app.controller('takeUrl',function($scope,$http,$window,$timeout){
 $scope.showError = false;
 $scope.showShort = true;
 $scope.fakeError = function(){
    
     $scope.showError = true;
     $scope.doFade = false;
    
   
    
    $timeout(function(){
      $scope.doFade = true;
    }, 3500);
  };

$scope.shortUrl = function(){
    $scope.showError = false;

    var url = $scope.long;

    $http({
        method:"post",
        url:'check',
       
        data : url
	        }).
	        success(function(data,status,headers,config){
                        console.log(data);
	        	$scope.short = data.shortUrl;
                        $scope.time = data.time;
                        $scope.clicks = data.clicks;
                        
                        
                       
                        url =  $scope.short.split('/');
                       
                        document.getElementById("detailLink").href="http://localhost:7101/Shorty/analytics.jsp?key="+url[url.length-1];
                       
                        $scope.showShort = false;
                        $('[data-toggle="tooltip"]').tooltip('show'); 
                        
                        setTimeout(function(){  var range = document.createRange();
                           range.selectNode(document.getElementById("result"));
                           window.getSelection().addRange(range); }, 100);
                     
	        })
		 .error(function(data,status,headers,config){
                 
			if(status==500){
                       
                      $window.location="error500.jsp";
                        }
                        
			 $scope.Error=data;
			 $scope.fakeError(); 
                         
		 });
}


});

function select(String){
          $('[data-toggle="tooltip"]').tooltip('show'); 
            var range = document.createRange();
            range.selectNode(document.getElementById(String));
            window.getSelection().addRange(range);
}
