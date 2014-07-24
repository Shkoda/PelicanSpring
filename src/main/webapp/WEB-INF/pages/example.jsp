<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="shortcut icon" href="../../assets/ico/favicon.ico">

    <title>Starter Template for Bootstrap</title>

    <!-- Bootstrap core CSS -->
    <link href="../../css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="../../css/template.css" rel="stylesheet">

    <link rel="stylesheet" href="../../css/tasks-app.css">

    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <script src="../../js/bootstrap.js"></script>
    <script src="../../js/bootstrap.min.js"></script>

    <![endif]-->
    <script src="http://d3js.org/d3.v3.min.js"></script>
</head>


<body>
<h2>Test me!</h2>

<div class="debug_area">

    <div class="alert alert-success alert-dismissable" id="alert-ok-block">
        <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
        <span id="ok-description"></span>

    </div>

    <div class="alert alert-warning alert-dismissable" id="alert-error-block">
        <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
        <span id="description"></span>

    </div>



    <button type="button" class="btn btn-default" onclick="alloha()">Alloha!</button>
</div>


<script type="text/javascript" src="../../js/apps/bullet.js"></script>
<!-- Bootstrap core JavaScript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
<script src="../../js/bootstrap.min.js"></script>
<script src="../../js/apps/cookies.js"></script>
<script src="../../js/apps/tasks.js"></script>


<%--<script src="http://code.jquery.com/jquery-1.9.1.js"></script>--%>
<script>

    $("#alert-error-block").hide();
    $("#alert-ok-block").hide();
    $('.btn-default').popover();
    $(".alert").find(".close").on("click", function (e) {
        // Find all elements with the "alert" class, get all descendant elements with the class "close", and bind a "click" event handler
        e.stopPropagation();    // Don't allow the click to bubble up the DOM
        e.preventDefault();    // Don't let any default functionality occur (in case it's a link)
        $(this).closest(".alert").slideUp(400);    // Hide this specific Alert
    });



    function alloha(){
        d3.text("/example/alloha")
                .header("Content-type", "application/json")
                .get(function (error, text) {

                    var response = JSON.parse(text);
                    console.log(response);
                    if (response.status != "OK") {
                        $("#description")
                                .html("<strong>Error: </strong><br/> " + response.description);
                        $("#alert-error-block").slideDown(400);
                        $("#alert-ok-block").slideUp(400);
                    } else {
                        $("#ok-description")
                                .html("<strong>Yehoooooo! </strong>" + response.description);
                        $("#alert-ok-block").slideDown(400);
                        $("#alert-error-block").slideUp(400);
                    }
//                    ------------
                    console.log(text+" from JSP ^^^");

                });
    }



</script>


</body>
</html>






















