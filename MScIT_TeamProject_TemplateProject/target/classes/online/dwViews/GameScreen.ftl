<html>

	<head>
		<!-- Web page title -->
    	<title>Top Trumps - Game</title>
    	
    	<!-- Import JQuery, as it provides functions you will probably find useful (see https://jquery.com/) -->
    	<script src="https://code.jquery.com/jquery-2.1.1.js"></script>
    	<script src="https://code.jquery.com/ui/1.11.1/jquery-ui.js"></script>
    	<link rel="stylesheet" href="https://code.jquery.com/ui/1.11.1/themes/flick/jquery-ui.css">

		<!-- Optional Styling of the Website, for the demo I used Bootstrap (see https://getbootstrap.com/docs/4.0/getting-started/introduction/) -->
		<link rel="stylesheet" href="http://dcs.gla.ac.uk/~richardm/TREC_IS/bootstrap.min.css">
    	<script src="http://dcs.gla.ac.uk/~richardm/vex.combined.min.js"></script>
    	<script>vex.defaultOptions.className = 'vex-theme-os';</script>
    	<link rel="stylesheet" href="http://dcs.gla.ac.uk/~richardm/assets/stylesheets/vex.css"/>
    	<link rel="stylesheet" href="http://dcs.gla.ac.uk/~richardm/assets/stylesheets/vex-theme-os.css"/>
    	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">

	</head>

    <body onload="initalize()"> <!-- Call the initalize method when the page loads -->
    	
    	<div class="container">
        <!-- Stylising the cards with css-->
        <meta name="viewport" content="width=device-width, initial-scale=1">
					<style>
                    body {
                        background-image: url(https://cdn.pixabay.com/photo/2014/09/27/13/38/bar-463476_1280.jpg);
                        background-repeat: no-repeat;
                        background-position: 0 0;
                        background-size: cover;
                    }
					.card {
					box-shadow: 0 4px 8px 0 rgba(0,0,0,0.2);
					transition: 0.3s;
					width: 40%;
					border-radius: 5px;
					}

					.card:hover {
					box-shadow: 0 8px 16px 0 rgba(0,0,0,0.2);
					}

					img {
					border-radius: 5px 5px 0 0;
					}

					.container {
					padding: 2px 16px;
					}
					</style>
							<!-- importing bootstrap stylesheet -->
							<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
						<title>
							Glasgow Bars Top Trumps
						</title>
						<!-- required meta tags -->
						<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
							<!-- creates rectangles to represent cards - not sure if this is the correct way to do this -->
					<style>
						
					.rectangle {
					height: 200px;
					width: 220px;
					}
					</style>
						</head>

						<body>
							<div class="container-fluid">
								<div class ="row">
									<div class ="col">
										<h1 class="blockquote text-center">
											Glasgow Bars Top Trumps
										</h1>
									</div>
								</div>
							</div>
                            <style>
                                h1{color: white;
                                    font-size: 40px;}
                            
                                #info {
                                    font-size: 24px;
                                    font-weight: 700;
                                    color: white;
                                }
                                #infoOne {
                                    color: white;
                                    font-size: 18px;
                                    font-weight: 700;
                                }
                                #roundNumber, #activePlayer, #chosenCategory, #comDeck{
                                    color: white;
                                    font-size: 18px;
                                    font-weight: 300;
                                    align: left;
                                }

                                #att {  color: black;}
                            </style>

							<div class="container">
								<div class="row">
								<div class="col-sm" id="info">
									Information
									<div class="rectangle" id="infoOne">
                                    <br>
										<div>
                                        <div>
											<p style="float: left;">Round Number:&nbsp</p><p id="roundNumber"></p>
                                        </div>
                                        <div>
											<p style="float: left;">Active Player:&nbsp</p><p id="activePlayer"></p>
                                        </div>
                                        <div>
											<p style="float: left;">Category Chosen:&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp</p>
                                        <p id="chosenCategory"></p>
                                        </div>
                                        <div>
											<p style="float: left;">Communal Deck Size:&nbsp</p><p id="comDeck"></p>
                                        </div>
                                        </div>
									</div>
									<button onclick="playRound();" type="button" id="mainButton" class="btn btn-success">Start Game</button>
                                    <style>
                                    #mainButton {
                                        background-color: #005c99;
                                        border: black;
                                        font-weight: 600;
                                    }
                                    </style>
									</div> 

                <!-- human player card -->
                <style> #usercard {color:white;}
                        #UserCardName{color:black;}
                        #UserFloorSticky, #UserPintPrice, #UserPubQuiz, #UserAtmosphere, #UserPlaylist{color:black;}
                </style>
              <div class="col-sm" id="usercard">
                Human Player <span class="badge badge-pill badge-dark" id="UserCardNo">XX</span>
                <div class="card", style="width:80%" style="height:100%;">
                    <div class ="container">
                        <div class ="card-header" id="UserCardName">Card Name </div>
                            <div>
                            <div>
								<p id="att" style="float: left;">Floor Stickiness:&nbsp</p><p id="UserFloorSticky">&nbsp</p>
                            </div>
                            <div>
							    <p id="att" style="float: left;">Pint Price:&nbsp</p><p id="UserPintPrice">&nbsp</p>
                            </div>
                            <div>
							    <p id="att" style="float: left;">Pub Quiz:&nbsp</p><p id="UserPubQuiz">&nbsp</p>
                            </div>
                            <div>
							    <p id="att" style="float: left;">Atmosphere:&nbsp</p><p id="UserAtmosphere">&nbsp</p>
                            </div>
                            <div>
							    <p id="att" style="float: left;">Playlist:&nbsp</p><p id="UserPlaylist">&nbsp</p>
                            </div>
                            </div>
                        </div>
                      
    
                    </div>
              </div>
                <style> #ai1card {color:white;} 
                        #ai1CardName {color:black;}
                        #ai1FloorSticky, #ai1PintPrice, #ai1PubQuiz, #ai1Atmosphere, #ai1Playlist{color:black;}
                </style>
              <div class="col-sm" id="ai1card" style="visibility: hidden">
                AI Player 1 <span class="badge badge-pill badge-dark" id="ai1CardNo">XX</span>
                <div class="card", style="width:80%" style="height:100%;">
                    <div class ="container">
                        <div class ="card-header" id="ai1CardName" >Card Name </div>
                            <div>
                            <div>
								<p id="att" style="float: left;">Floor Stickiness:&nbsp</p><p id="ai1FloorSticky"></p>
                            </div>
                            <div>
							    <p id="att" style="float: left;">Pint Price:&nbsp</p><p id="ai1PintPrice"></p>
                            </div>
                            <div>
							    <p id="att" style="float: left;">Pub Quiz:&nbsp</p><p id="ai1PubQuiz"></p>
                            </div>
                            <div>
							    <p id="att" style="float: left;">Atmosphere:&nbsp</p><p id="ai1Atmosphere"></p>
                            </div>
                            <div>
							    <p id="att" style="float: left;">Playlist:&nbsp</p><p id="ai1Playlist"></p>
                            </div>
                            </div>
                        </div>
                      
    
                    </div>
              </div>
                <style> #ai2card {color:white;} 
                        #ai2CardName {color:black;}
                        #ai2FloorSticky, #ai2PintPrice, #ai2PubQuiz, #ai2Atmosphere, #ai2Playlist{color:black;}
                </style>
              <div class="col-sm" id="ai2card" style="visibility: hidden">
                  AI Player 2 <span class="badge badge-pill badge-dark" id="ai2CardNo">XX</span>
                  <div class="card", style="width:80%" style="height:100%;">
                    <div class ="container">
                        <div class ="card-header" id="ai2CardName">Card Name </div>
                            <div>
                            <div>
								<p id="att" style="float: left;">Floor Stickiness:&nbsp</p><p id="ai2FloorSticky"></p>
                            </div>
                            <div>
							    <p id="att" style="float: left;">Pint Price:&nbsp</p><p id="ai2PintPrice"></p>
                            </div>
                            <div>
							    <p id="att" style="float: left;">Pub Quiz:&nbsp</p><p id="ai2PubQuiz"></p>
                            </div>
                            <div>
							    <p id="att" style="float: left;">Atmosphere:&nbsp</p><p id="ai2Atmosphere"></p>
                            </div>
                            <div>
							    <p id="att" style="float: left;">Playlist:&nbsp</p><p id="ai2Playlist"></p>
                            </div>
                            </div>
                        </div>
                      
    
                    </div>
              </div>
            </div>
        </div>

        <div class = "container">
            <div class="row">

                <div class="col-sm">
                  <!-- next button, and select category drop down menu-->
                  <div class="dropdown show">
                    <button class="btn btn-secondary dropdown-toggle" role="button" id="dropdownMenuLink" data-toggle="dropdown" disabled>
                      Choose a category
                    <span class="caret"></span></button>
                    <ul id="dropdown" class="dropdown-menu">
                    <li><a data-value="1" href="#">Floor Stickiness</a></li>
					<li><a data-value="2" href="#">Pint Price</a></li>
					<li><a data-value="3" href="#">Pub Quiz</a></li>
					<li><a data-value="4" href="#">Atmosphere</a></li>
					<li><a data-value="5" href="#">Playlist</a></li>
					</ul>
                  </div>
                </div>
                  </div>
                  <div class ="container-fluid">
                        <div class ="row">

                        <div class ="col-md"></div>
               <style> #ai3card {color:white;} 
                        #ai3CardName {color:black;}
                        #ai3FloorSticky, #ai3PintPrice, #ai3PubQuiz, #ai3Atmosphere, #ai3Playlist{color:black;}
                </style>
                <div class="col-sm" id="ai3card" style="visibility: hidden">
                  AI Player 3 <span class="badge badge-pill badge-dark" id="ai3CardNo">XX</span>
                  <div class="card", style="width:60%" style="height:100%;">
                    <div class ="container">
                        <div class ="card-header" id="ai3CardName">Card Name </div>
                            <div>
                            <div>
								<p id="att" style="float: left;">Floor Stickiness:&nbsp</p><p id="ai3FloorSticky"></p>
                            </div>
                            <div>
							    <p id="att" style="float: left;">Pint Price:&nbsp</p><p id="ai3PintPrice"></p>
                            </div>
                            <div>
							    <p id="att" style="float: left;">Pub Quiz:&nbsp</p><p id="ai3PubQuiz"></p>
                            </div>
                            <div>
							    <p id="att" style="float: left;">Atmosphere:&nbsp</p><p id="ai3Atmosphere"></p>
                            </div>
                            <div>
							    <p id="att" style="float: left;">Playlist:&nbsp</p><p id="ai3Playlist"></p>
                            </div>
                            </div>
                        </div>
                      
    
                    </div>
                </div>
                    <style> #ai4card {color:white;} 
                            #ai4CardName {color:black;}
                            #ai4FloorSticky, #ai4PintPrice, #ai4PubQuiz, #ai4Atmosphere, #ai4Playlist{color:black;}
                </style>
                <div class="col-sm" id="ai4card" style="visibility: hidden">
                  AI Player 4 <span class="badge badge-pill badge-dark" id="ai4CardNo">XX</span>
                  <div class="card", style="width:60%" style="height:100%;">
                    <div class ="container">
                        <div class ="card-header" id="ai4CardName">Card Name </div>
                           <div>
                            <div>
								<p id="att" style="float: left;">Floor Stickiness:&nbsp</p><p id="ai4FloorSticky"></p>
                            </div>
                            <div>
							    <p id="att" style="float: left;">Pint Price:&nbsp</p><p id="ai4PintPrice"></p>
                            </div>
                            <div>
							    <p id="att" style="float: left;">Pub Quiz:&nbsp</p><p id="ai4PubQuiz"></p>
                            </div>
                            <div>
							    <p id="att" style="float: left;">Atmosphere:&nbsp</p><p id="ai4Atmosphere"></p>
                            </div>
                            <div>
							    <p id="att" style="float: left;">Playlist:&nbsp</p><p id="ai4Playlist"></p>
                            </div>
                            </div>
                        </div>
                      
    
                        </div>
                     </div>
                  </div>
                </div>

                <div class="col-sm">
                  

                </div>

                
            </div>
        

          <script type = "text/javascript">
            function MyFunction(){
                var url = "https://www.guu.co.uk/";
                window.location.replace(url);
            }
          </script>
        
        <!-- JavaScript if function from google
           if (condition) {   }
            //  block of code to be executed if the condition is true -->

        <script type = "text/javascript">
            function PintPriceFunction(){
            var url = "https://www.reddit.com/";
            window.location.replace(url);
         }
         </script>
         <script type = "text/javascript">
          function AtmosphereFunction(){
             var url = "https://www.reddit.com/";
             window.location.replace(url);
          }
          </script>
          <script type = "text/javascript">
            function PubQuizFunction(){
               var url = "https://www.reddit.com/";
               window.location.replace(url);
            }
            </script>
            <script type = "text/javascript">
              function MusicQualityFunction(){
                 var url = "https://www.reddit.com/";
                 window.location.replace(url);
              }
              </script>
              <script type = "text/javascript">
                function StickyFunction(){
                   var url = "https://www.reddit.com/";
                   window.location.replace(url);
                }
                </script>
        
          <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
          <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
          <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>  

			</body>
		</div>

</html>	
		
		<script type="text/javascript">
		
			// Method that is called on page load
			function initalize() {
                gameStart();
			
				// --------------------------------------------------------------------------
				// You can call other methods you want to run when the page first loads here
				// --------------------------------------------------------------------------
				
				// For example, lets call our sample methods
				
			}
			
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
            function gameStart(){
                /**
                * initiates the start of the game 
                */
            // First create a CORS request, this is the message we are going to send (a get request in this case)
            var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/gamestart"); // Request type and URL

            // Message is not sent yet, but we can check that the browser supports CORS
            if (!xhr) {
                alert("CORS not supported");
            };
            xhr.send();

            }
            function allAiCardInfo(){
                /**
                * calls all the ai card info methods which gets all the card data for those cards 
                */
                ai1CardInfo();
                ai2CardInfo();
                ai3CardInfo();
                ai4CardInfo();
            }

            function userCardInfo(){
                /**
                * gets the users card data
                * and should say whether the player has been eliminated
                */
            // First create a CORS request, this is the message we are going to send (a get request in this case)
            var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/humancard"); // Request type and URL

            // Message is not sent yet, but we can check that the browser supports CORS
            if (!xhr) {
                alert("CORS not supported");
            }
            //need to determine what to do with returned info before //sending request
            xhr.onload = function(e) {
                var responseText = xhr.response;
                var json = JSON.parse(responseText);
                if(json["code"] === 500){
                $("#UserCardName").html("Eliminated");
                $("#UserFloorSticky").html("&nbsp&nbsp");
                $("#UserPintPrice").html("&nbsp&nbsp&nbsp");
                $("#UserPubQuiz").html("&nbsp&nbsp");
                $("#UserAtmosphere").html("&nbsp&nbsp");
                $("#UserPlaylist").html("&nbsp&nbsp");
                $("#UserCardNo").html(0);
                }else{
                $("#UserCardName").html(json["name"]);
                $("#UserFloorSticky").html(json["floorSticky"]);
                $("#UserPintPrice").html(json["pintPrice"]);
                $("#UserPubQuiz").html(json["pubQuiz"]);
                $("#UserAtmosphere").html(json["atmosphere"]);
                $("#UserPlaylist").html(json["playlist"]);
                $("#UserCardNo").html(json["deckSize"]);
                }
            };

            //send request
            xhr.send();

        }
        function chosenCategory(){
            /**
            * just gets the chosen category info
            * needs to do this seperate to the round info because the round is finished by the time
            * the category info is there so leaves the other info for the previous round
             */
            // First create a CORS request, this is the message we are going to send (a get request in this case)
            var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/chosencategory"); // Request type and URL

            // Message is not sent yet, but we can check that the browser supports CORS
            if (!xhr) {
                alert("CORS not supported");
            }
            //need to determine what to do with returned info before //sending request
            xhr.onload = function(e) {
                var responseText = xhr.response;
                var json = JSON.parse(responseText);
                $("#chosenCategory").html(json["chosenCategory"]);
            };

            //send request
            xhr.send();

        }

        
            function roundInfo(){
                /**
                * updates all the game round info in the top left for each round
                * 
                */
            // First create a CORS request, this is the message we are going to send (a get request in this case)
            var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/roundinfo"); // Request type and URL

            // Message is not sent yet, but we can check that the browser supports CORS
            if (!xhr) {
                alert("CORS not supported");
            }
            //need to determine what to do with returned info before //sending request
            xhr.onload = function(e) {
                var responseText = xhr.response;
                var json = JSON.parse(responseText);
                $("#roundNumber").html(json["roundCounter"]);
                $("#activePlayer").html(json["activePlayer"]);
                $("#comDeck").html(json["communalDeckSize"]);
            };

            //send request
            xhr.send();

        }

        function playRound(){
            /**
             * this is the main pay round method this handles each game
             * it works out who the active player is so it can call either the ai or the user method
             * it then redirects to the nescesarry method
             */
            userCardInfo();
            allAiCardInfo();
            roundInfo();
            // First create a CORS request, this is the message we are going to send (a get request in this case)
            var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/roundinfo"); // Request type and URL

            // Message is not sent yet, but we can check that the browser supports CORS
            if (!xhr) {
                alert("CORS not supported");
            }
            //need to determine what to do with returned info before //sending request
            xhr.onload = function(e) {
                var responseText = xhr.response;
                var json = JSON.parse(responseText);
                var player = json["activePlayer"];
                var round =  json["roundCounter"];
                if(player === "You"){
                    if(round === 0){alert("You to Start! Select a Category!");}
                    else{alert("You won!! Select a category!");}
                    $('#dropdownMenuLink').prop('disabled', false);
                    $('#mainButton').prop('disabled', true);
                }else{
                    if(round === 0){alert(player + " to start!");;}
                    else{alert(player + " won!! they're turn!");}
                    aiPlayerRound();
                    chosenCategory();
                    document.getElementById("mainButton").innerHTML="Show Cards";
                    document.getElementById("mainButton").onclick = function(){
                    showCards();
                    };

                }
                
            };

            //send request
            xhr.send();
        }

        $(function(){
            /**
              * takes the input from the dropdown category choice 
              * then passes this to the api to strat the human round 
              * controls disabling buttons and drowpdown
              */
				$(".dropdown-menu li a").click(function () {
                    // First create a CORS request, this is the message we are going to send (a get request in this case)
            var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/userround?category=" + $(this).data('value')); // Request type and URL

            // Message is not sent yet, but we can check that the browser supports CORS
            if (!xhr) {
                alert("CORS not supported");
            };
            xhr.send();
            document.getElementById("mainButton").innerHTML="Show Cards";
            document.getElementById("mainButton").onclick = function(){
            showCards();
            }
            setTimeout(function(){chosenCategory();}, 1);
            $('#mainButton').prop('disabled', false);
            $('#dropdownMenuLink').prop('disabled', true);       
				});
			});


        function aiPlayerRound(){
            //calls the ai player round

            // First create a CORS request, this is the message we are going to send (a get request in this case)
            var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/computerround"); // Request type and URL

            // Message is not sent yet, but we can check that the browser supports CORS
            if (!xhr) {
                alert("CORS not supported");
            };
            xhr.send();

        }
        function showCards(){
            /*
             * makes all the ai player cards visable when the show cards button is pressed
             */
            var elem = document.getElementById('ai1card');
            elem.style.visibility="visible";
            var elem2 = document.getElementById('ai2card');
            elem2.style.visibility="visible";
            var elem3 = document.getElementById('ai3card');
            elem3.style.visibility="visible";
            var elem4 = document.getElementById('ai4card');
            elem4.style.visibility="visible";
            document.getElementById("mainButton").innerHTML="Next Round";
            document.getElementById("mainButton").onclick = function(){
            nextRound();
            };
        }
        function hideCards(){
            /*
             * makes all the ai player cards hidden when the next round button 
             */

            var elem = document.getElementById('ai1card');
            elem.style.visibility="hidden";
            var elem2 = document.getElementById('ai2card');
            elem2.style.visibility="hidden";
            var elem3 = document.getElementById('ai3card');
            elem3.style.visibility="hidden";
            var elem4 = document.getElementById('ai4card');
            elem4.style.visibility="hidden";
        }
        function nextRound(){
            hideCards();
            setTimeout(function(){ playRound(); }, 500);
        }

        function ai1CardInfo(){
            /*
             * this method and all the ones after are just for retrieving the ai card data
            */
            // First create a CORS request, this is the message we are going to send (a get request in this case)
            var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/ai1topcard"); // Request type and URL

            // Message is not sent yet, but we can check that the browser supports CORS
            if (!xhr) {
                alert("CORS not supported");
            }
            //need to determine what to do with returned info before //sending request
            xhr.onload = function(e) {
                var responseText = xhr.response;
                var json = JSON.parse(responseText);
                if(json["code"] === 500){
                $("#ai1CardName").html("Eliminated");
                $("#ai1FloorSticky").html("&nbsp&nbsp");
                $("#ai1PintPrice").html("&nbsp&nbsp&nbsp");
                $("#ai1PubQuiz").html("&nbsp&nbsp");
                $("#ai1Atmosphere").html("&nbsp&nbsp");
                $("#ai1Playlist").html("&nbsp&nbsp");
                $("#ai1CardNo").html(0);
                }else{
                $("#ai1CardName").html(json["name"]);
                $("#ai1FloorSticky").html(json["floorSticky"]);
                $("#ai1PintPrice").html(json["pintPrice"]);
                $("#ai1PubQuiz").html(json["pubQuiz"]);
                $("#ai1Atmosphere").html(json["atmosphere"]);
                $("#ai1Playlist").html(json["playlist"]);
                $("#ai1CardNo").html(json["deckSize"]);
                }
            };

            //send request
            xhr.send();

        }
        function ai2CardInfo(){
            // First create a CORS request, this is the message we are going to send (a get request in this case)
            var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/ai2topcard"); // Request type and URL

            // Message is not sent yet, but we can check that the browser supports CORS
            if (!xhr) {
                alert("CORS not supported");
            }
            //need to determine what to do with returned info before //sending request
            xhr.onload = function(e) {
                var responseText = xhr.response;
                var json = JSON.parse(responseText);
                if(json["code"] === 500){
                $("#ai2CardName").html("Eliminated");
                $("#ai2FloorSticky").html("&nbsp&nbsp");
                $("#ai2PintPrice").html("&nbsp&nbsp&nbsp");
                $("#ai2PubQuiz").html("&nbsp&nbsp");
                $("#ai2Atmosphere").html("&nbsp&nbsp");
                $("#ai2Playlist").html("&nbsp&nbsp");
                $("#ai2CardNo").html(0);
                }else{
                $("#ai2CardName").html(json["name"]);
                $("#ai2FloorSticky").html(json["floorSticky"]);
                $("#ai2PintPrice").html(json["pintPrice"]);
                $("#ai2PubQuiz").html(json["pubQuiz"]);
                $("#ai2Atmosphere").html(json["atmosphere"]);
                $("#ai2Playlist").html(json["playlist"]);
                $("#ai2CardNo").html(json["deckSize"]);
                }
            };

            //send request
            xhr.send();

        }
        function ai3CardInfo(){
            // First create a CORS request, this is the message we are going to send (a get request in this case)
            var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/ai3topcard"); // Request type and URL

            // Message is not sent yet, but we can check that the browser supports CORS
            if (!xhr) {
                alert("CORS not supported");
            }
            //need to determine what to do with returned info before //sending request
            xhr.onload = function(e) {
                var responseText = xhr.response;
                var json = JSON.parse(responseText);
                if(json["code"] === 500){
                $("#ai3CardName").html("Eliminated");
                $("#ai3FloorSticky").html("&nbsp&nbsp");
                $("#ai3PintPrice").html("&nbsp&nbsp&nbsp");
                $("#ai3PubQuiz").html("&nbsp&nbsp");
                $("#UserAtmosphere").html("&nbsp&nbsp");
                $("#ai3Playlist").html("&nbsp&nbsp");
                $("#ai3CardNo").html(0);
                }else{
                $("#ai3CardName").html(json["name"]);
                $("#ai3FloorSticky").html(json["floorSticky"]);
                $("#ai3PintPrice").html(json["pintPrice"]);
                $("#ai3PubQuiz").html(json["pubQuiz"]);
                $("#ai3Atmosphere").html(json["atmosphere"]);
                $("#ai3Playlist").html(json["playlist"]);
                $("#ai3CardNo").html(json["deckSize"]);
                }
            };

            //send request
            xhr.send();

        }
        function ai4CardInfo(){
            // First create a CORS request, this is the message we are going to send (a get request in this case)
            var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/ai4topcard"); // Request type and URL

            // Message is not sent yet, but we can check that the browser supports CORS
            if (!xhr) {
                alert("CORS not supported");
            }
            //need to determine what to do with returned info before //sending request
            xhr.onload = function(e) {
                var responseText = xhr.response;
                var json = JSON.parse(responseText);
                if(json["code"] === 500){
                $("#ai4CardName").html("Eliminated");
                $("#ai4FloorSticky").html("&nbsp&nbsp");
                $("#ai4PintPrice").html("&nbsp&nbsp&nbsp");
                $("#ai4PubQuiz").html("&nbsp&nbsp");
                $("#ai4Atmosphere").html("&nbsp&nbsp");
                $("#ai4Playlist").html("&nbsp&nbsp");
                $("#ai4CardNo").html(0);
                }else{
                $("#ai4CardName").html(json["name"]);
                $("#ai4FloorSticky").html(json["floorSticky"]);
                $("#ai4PintPrice").html(json["pintPrice"]);
                $("#ai4PubQuiz").html(json["pubQuiz"]);
                $("#ai4Atmosphere").html(json["atmosphere"]);
                $("#ai4Playlist").html(json["playlist"]);
                $("#ai4CardNo").html(json["deckSize"]);
                }
            };

            //send request
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