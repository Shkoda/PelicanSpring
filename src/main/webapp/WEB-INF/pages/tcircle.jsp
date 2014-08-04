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

    <title>Sequences sunburst</title>
    <script src="http://d3js.org/d3.v3.min.js"></script>
    <link rel="stylesheet" type="text/css"
          href="https://fonts.googleapis.com/css?family=Open+Sans:400,600">
    <!-- Bootstrap core CSS -->
    <link href="../../css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="../../css/template.css" rel="stylesheet">

    <%--<link rel="stylesheet" href="../../css/tasks-app.css">--%>
    <link href="../../css/sequence.css" rel="stylesheet" type="text/css">
    <%--<link rel="stylesheet" href="../../css/tasks-app.css">--%>

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
        <div id="sunburst_text_area" style="visibility: hidden;">
            <span id="element_name"></span><br/>
            <span id="element_description"></span><br/>
            <%--there can be static text--%>
        </div>
    </div>
</div>

<div id="sidebar">
    <input type="checkbox" id="togglelegend"> Legend<br/>

    <div id="legend" style="visibility: hidden;"></div>
</div>

<%--circle chart--%>
<script type="text/javascript" src="../../js/temp.js"></script>

<script type="text/javascript">
    // Hack to make this example display correctly in an iframe on bl.ocks.org
    d3.select(self.frameElement).style("height", "700px");
</script>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>


</body>
</html>