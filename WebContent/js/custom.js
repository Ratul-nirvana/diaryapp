/**
 * http://usejsdoc.org/
 */
var app = angular.module("diaryApp", []);
app.controller("diaryAppController", function($scope, $http){
	$scope.qanswers = [{
						'question' : 'Sample question',
						'answer'   : 'This is a sample answer'
					}
				   ,{
					   'question'  : 'Sample question 2',
					   'answer'    : 'This is the sample answer to sample question 2'
				    }];
	$scope.appTodayQuestion = '';
	$scope.getQuestion = function(){
		$http.get("http://localhost:8080/MyDiary/RequestHandler?type=question")
		.then(function(response){
			$scope.appTodayQuestion = response.data;
		});
	};
	$scope.postQuestion = function(){
		var _question = $scope.appQuestion;
		var payload = {
				"postType" : "question",
				"postQuestion" : _question
				};
		
		$http({
		    method: 'POST',
		    url: 'http://localhost:8080/MyDiary/RequestHandler',
		    headers: {'Content-Type': 'application/x-www-form-urlencoded'},
		    transformRequest: function(obj) {
		        var str = [];
		        for(var p in obj)
		        str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
		        return str.join("&");
		    },
		    data: payload
		}).success(function (response) {
			console.log(response);
			$scope.appQuestion = '';
		});
	};
	$scope.postQuestion = function(){
		console.log("posting the answer");
		var _question = $scope.appTodayQuestion;
		var _answer = $scope.appTodayAnswer;
		var payload = {
				"postType" : "answer",
				"postQuestion" : _question,
				"postAnswer" : _answer
				};
		
		$http({
		    method: 'POST',
		    url: 'http://localhost:8080/MyDiary/RequestHandler',
		    headers: {'Content-Type': 'application/x-www-form-urlencoded'},
		    transformRequest: function(obj) {
		        var str = [];
		        for(var p in obj)
		        str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
		        return str.join("&");
		    },
		    data: payload
		}).success(function (response) {
			console.log(response);
			$scope.appTodayQuestion = '';
			$scope.appTodayAnswer = '';
			
		});
	};
	window.getAnswers($scope,$http);
	$scope.getQuestion();
	
});
function getAnswers($scope,$http){
	$http.get("http://localhost:8080/MyDiary/RequestHandler?type=answers")
	.then(function(response){
		var responseStr = response.data.split('%');
		console.log("RECIEVED:"+responseStr);
		var result = [];
		if(responseStr.length > 0){
			for(var index in responseStr){
				if(responseStr[index].length > 0){
					var responseItems =  responseStr[index].split('~');
					console.log("PARSED ESPONSE::"+responseItems);
					var ques = responseItems[0];
					var ans = responseItems[1];
					var qmodule = {};
					qmodule.question = ques;
					qmodule.answer   = ans;
					result.push(qmodule);
				}
				
			}
		}
		console.log(JSON.stringify(result));
		$scope.qanswers = result;
	});
}