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
    <script>
        vex.defaultOptions.className = 'vex-theme-os';
    </script>
    <link rel="stylesheet" href="http://dcs.gla.ac.uk/~richardm/assets/stylesheets/vex.css" />
    <link rel="stylesheet" href="http://dcs.gla.ac.uk/~richardm/assets/stylesheets/vex-theme-os.css" />
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">

    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
</head>

<body onload="initalize()">
    <!-- Call the initalize method when the page loads -->

    <div class="container">
        <!-- Add your HTML Here -->
        <div class="Header">
        <style> 
            body{background: grey}
            h1, h2{text-align: center; color: white}</style>
            <h1> Glasgow Bars Top Trumps</h1>
            <h2> Statistics</h2>
            <style>
                p {
                    text-align: right;
                    color: white;
                }
                
                #total_games, #user_wins, #computer_wins, #avg_draws, #max_rounds {
                    text-align: left;
                    color: white;
                }
                
            </style>
            <br>
        </div>
		<div class="row">
		<div class="col"><p>Total Number of Games:</p></div>
		<div class="col"><p id="total_games">Result</p></div>
		    <div class="w-100"></div>
		<div class="col"><p>Number of User Wins:</p></div>
		<div class="col"><p id="user_wins">Result</p></div>
		    <div class="w-100"></div>
		<div class="col"><p>Number of Computer Wins:</p></div>
		<div class="col"><p id="computer_wins">Result</p></div>
		    <div class="w-100"></div>
		<div class="col"><p>Average Number of Draws:</p></div>
		<div class="col"><p id="avg_draws">Result</p></div>
		    <div class="w-100"></div>
		<div class="col"><p>Maximum Number of Rounds Played:</p></div>
		<div class="col"><p id="max_rounds">Result</p></div>
		    <div class="w-100"></div>
		<div class="col"></div>
		<div class ="col"><button id="home" value="Redirect" type="button" onclick="homeScreen()" class="btn btn-success btn-block" >Home</button></div>
		<div class="col"></div>
		    <div class="w-100"></div>
		<div class="col"></div>
		<div class="col"></div>
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
        function database() {
            //create cors request to send GET request
            var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/database");

            //if cors not supported creates alert message
            if (!xhr) {
                alert("CORS not supported");
            }


            //need to determine what to do with returned info before //sending request
            xhr.onload = function(e) {
                var responseText = xhr.response;
                var json = JSON.parse(responseText);
                $("#total_games").html(json["totalGamesPlayed"]);
                $("#user_wins").html(json["numberOfUserWins"]);
                $("#computer_wins").html(json["numberOfComputerWins"]);
                $("#avg_draws").html(json["avgNumberOfDraws"]);
                $("#max_rounds").html(json["maxNumberOfRounds"]);
            };

            //send request
            xhr.send();

        }
        function homeScreen(){
                window.location.href = 'http://localhost:7777/toptrumps';
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
            var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/helloWord?Word=" + word); // Request type and URL+parameters

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