<%--
  Created by IntelliJ IDEA.
  User: Nightingale
  Date: 23.07.2014
  Time: 14:32
  To change this template use File | Settings | File Templates.
--%>

<html>
<head>
    <meta charset="utf-8">

    <title>Sequences sunburst ^_^</title>
    <script src="http://d3js.org/d3.v3.min.js"></script>
    <link rel="stylesheet" type="text/css"
          href="https://fonts.googleapis.com/css?family=Open+Sans:400,600">
    <!-- Bootstrap core CSS -->
    <link href="../../css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="../../css/template.css" rel="stylesheet">

    <link rel="stylesheet" href="../../css/tasks-app.css">
    <link href="../../css/sequence.css" rel="stylesheet" type="text/css">
    <link rel="stylesheet" href="../../css/tasks-app.css">

</head>
<body>
<h2>Playing with sunburst</h2>


<div class="debug_area">

    <div class="alert alert-success alert-dismissable" id="alert-ok-block">
        <span id="ok-description"></span>
    </div>


</div>


<div id="main">
    <div id="sequence"></div>
    <div id="chart">
        <div id="explanation" style="visibility: hidden;">
            <span id="percentage"></span><br/>
            lalala
        </div>
    </div>
</div>

<div id="sidebar">
    <input type="checkbox" id="togglelegend"> Legend<br/>

    <div id="legend" style="visibility: hidden;"></div>
</div>


<script type="text/javascript" src="../../js/sequences.js"></script>

<script type="text/javascript">
    // Hack to make this example display correctly in an iframe on bl.ocks.org
    d3.select(self.frameElement).style("height", "700px");
</script>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
<script src="../../js/bootstrap.min.js"></script>
<script src="../../js/apps/cookies.js"></script>
<script src="../../js/apps/tasks.js"></script>


<script>

    //    $("#alert-ok-block").showPopup();
    //    $(".alert").find(".close").on("click", function (e) {
    //        // Find all elements with the "alert" class, get all descendant elements with the class "close", and bind a "click" event handler
    //        e.stopPropagation();    // Don't allow the click to bubble up the DOM
    //        e.preventDefault();    // Don't let any default functionality occur (in case it's a link)
    //        $(this).closest(".alert").slideUp(400);    // Hide this specific Alert
    //    });
    console.log("in circle.jsp script");

    $("#ok-description").html("<strong>Yehoooooo! </strong>" + "text terrr");
    $("#alert-ok-block").slideDown(400);

    //
    d3.text("/circle/get_csv")
            .header("Content-type", "application/json")
            .get(function (error, text) {

                var response = JSON.parse(text);
                var body = response.description;
                var csv = d3.csv.parseRows(body);
                var json = buildHierarchy_nightingale(csv);

                $("#ok-description")
                        .html("<strong>Yehoooooo! </strong>" + JSON.stringify(json));
                $("#alert-ok-block").slideDown(400);


            });
</script>
</body>
</html>