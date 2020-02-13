<html>

	<head>
		<!-- Web page title -->
    	<title>Top Trumps - Stats</title>
    	
    	<!-- Import JQuery, as it provides functions you will probably find useful (see https://jquery.com/) -->
    	<script src="https://code.jquery.com/jquery-2.1.1.js"></script>
    	<script src="https://code.jquery.com/ui/1.11.1/jquery-ui.js"></script>
    	<link rel="stylesheet" href="https://code.jquery.com/ui/1.11.1/themes/flick/jquery-ui.css">

		<!-- Optional Styling of the Website, for the demo I used Bootstrap (see https://getbootstrap.com/docs/4.0/getting-started/introduction/) -->
		<link rel="stylesheet" href="http://dcs.gla.ac.uk/~richardm/TREC_IS/bootstrap.min.css">
    	<script src="http://dcs.gla.ac.uk/~richardm/vex.combined.min.js"></script>
    	<script>vex.defaultOptions.className = 'vex-theme-os';</script>
    	<link rel="stylesheet" href="http://dcs.gla.ac.uk/~richardm/assets/stylesheets/vex.css"/>
    	<link rel="stylesheet"   href="http://dcs.gla.ac.uk/~richardm/assets/stylesheets/vex-theme-os.css"/>
    	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">

	</head>

    <body onload="initalize()"> <!-- Call the initalize method when the page loads -->
    	
    	<div class="container">
				<!-- Add your HTML Here -->
			<h1> Top Trumps Game Statistics</h1>
			<style> 
				h1 { text-align: center;
					font-family: 'Roboto', sans-serif; 
					font-size: 30px;}
				p { font-size: 20px;
					font-family: 'Roboto', sans-serif;
					text-align: right; }
				#totalGames, #userWins, #computerWins, #avgDraws, #maxRounds{
					text-align: center;
				}
				button {display: inline-block;
						padding: 0.3em 1.2em;
						margin: 0 0.3em 0.3em;
						border-radius: 2em;
						box-sizing: border-box;
						text-decoration: none;
						font-family: 'Roboto', sans-serif;
						font-weight: 400;
						color: #FFFFFF;
						background-color: #4eb5f1;
						text-align: center;
						transition: all 0.2s
					}
			</style>
			<br>
		</div>
		<div class="No of Games">
			<div class="col-sm-6">
				<p>Total Number of Games</p>
			</div>
			<div class="col-sm-6">
				<p id="totalGames">0</p>
			</div>
		</div> 
		<div class="No of user wins">
			<div class="col-sm-6">
				<p>Number of User Wins</p>
			</div>
			<div class="col-sm-6">
				<p id="userWins">0</p>
			</div>
		</div>
		<div class="No of computer wins">
			<div class="col-sm-6">
				<p>Number of Computer Wins</p>
			</div>
			<div class="col-sm-6">
				<p id="computerWins">0</p>
			</div>
		</div>
		<div class="Avg no of draws">
			<div class="col-sm-6">
				<p>Average Number of Draws</p>
			</div>
			<div class="col-sm-6">
				<p id="avgDraws">0</p>
			</div>
		</div>
		<div class="max games playeds">
			<div class="col-sm-6">
				<p>Maximum Number of Rounds Played</p>
			</div>
			<div class="col-sm-6">
				<p id="maxRounds">0</p>
			</div>
		</div>
		<div class ="homeButton">
			<div class="col-sm-12 text-center">
			<button>Home</button>
		</div>
		
		</div>
		
		<script type="text/javascript">
		
			// Method that is called on page load
			function initalize() {
				database();
			}
				
			
				// --------------------------------------------------------------------------
				// You can call other methods you want to run when the page first loads here
				// --------------------------------------------------------------------------
				
				// For example, lets call our sample methods
				
			
			// -----------------------------------------
			// Add your other Javascript methods Here
			// -----------------------------------------
		
			// This is a reusable method for creating a CORS request. Do not edit this.
			function createCORSRequest(method, url) {
  				var xhr = new XMLHttpRequest();
  				if ("withCredentials" in xhr) {

    				// Check if the XMLHttpRequest object has a "withCredentials" property.
    				// "withCredentials" only exists on XMLHTTPRequest2 objects.
    				xhr.open(method, url, true);

  				} else if (typeof XDomainRequest != "undefined") {

    				// Otherwise, check if XDomainRequest.
    				// XDomainRequest only exists in IE, and is IE's way of making CORS requests.
    				xhr = new XDomainRequest();
    				xhr.open(method, url);

 				 } else {

    				// Otherwise, CORS is not supported by the browser.
    				xhr = null;

  				 }
  				 return xhr;
			}
		
		</script>
		
		<!-- Here are examples of how to call REST API Methods -->
		<script type="text/javascript">

			 function database(){
	  //create cors request to send GET request
      		var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/database");

      //if cors not supported creates alert message
      		if(!xhr) {
        		alert("CORS not supported");
      		}


      //need to determine what to do with returned info before //sending request
			xhr.onload = function(e){
				var responseText = xhr.response;
				var json = JSON.parse(responseText);
				$("#totalGames").html(json["totalGamesPlayed"]);
				$("#userWins").html(json["numberOfUserWins"]);
				$("#computerWins").html(json["numberOfComputerWins"]);
				$("#avgDraws").html(json["avgNumberOFDraws"]);
				$("#maxRounds").html(json["maxNumberOfRounds"]);
				};
						
						// We have done everything we need to prepare the CORS request, so send it
					xhr.send();		
		}
		
			// This calls the helloJSONList REST method from TopTrumpsRESTAPI
			function helloJSONList() {
			
				// First create a CORS request, this is the message we are going to send (a get request in this case)
				var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/helloJSONList"); // Request type and URL
				
				// Message is not sent yet, but we can check that the browser supports CORS
				if (!xhr) {
  					alert("CORS not supported");
				}

				// CORS requests are Asynchronous, i.e. we do not wait for a response, instead we define an action
				// to do when the response arrives 
				xhr.onload = function(e) {
 					var responseText = xhr.response; // the text of the response
					alert(responseText); // lets produce an alert
				};
				
				// We have done everything we need to prepare the CORS request, so send it
				xhr.send();		
			}
			
			// This calls the helloJSONList REST method from TopTrumpsRESTAPI
			function helloWord(word) {
			
				// First create a CORS request, this is the message we are going to send (a get request in this case)
				var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/helloWord?Word="+word); // Request type and URL+parameters
				
				// Message is not sent yet, but we can check that the browser supports CORS
				if (!xhr) {
  					alert("CORS not supported");
				}

				// CORS requests are Asynchronous, i.e. we do not wait for a response, instead we define an action
				// to do when the response arrives 
				xhr.onload = function(e) {
 					var responseText = xhr.response; // the text of the response
					alert(responseText); // lets produce an alert
				};
				
				// We have done everything we need to prepare the CORS request, so send it
				xhr.send();		
			}

		</script>
		
		</body>
</html>