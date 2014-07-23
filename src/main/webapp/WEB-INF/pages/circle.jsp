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
    <link href="../../css/sequence.css" rel="stylesheet" type="text/css">

    <%--<script src="../../js/sequences.js"></script>--%>

</head>
<body>
<h2>Playing with sunburst</h2>
<div id="main">
    <div id="sequence"></div>
    <div id="chart">
        <div id="explanation" style="visibility: hidden;">
            <span id="percentage"></span><br/>
            of visits begin with this sequence of pages
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
</body>
</html>