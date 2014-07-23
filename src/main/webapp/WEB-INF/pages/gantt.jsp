<!DOCTYPE html>
<html lang="en">
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
    <link href="../../css/table.css" rel="stylesheet">

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

<div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">Web Planner</a>
        </div>
        <div class="collapse navbar-collapse">
            <ul class="nav navbar-nav">
                <li><a href="${pageContext.request.contextPath}/tasks">Tasks</a></li>
                <li><a href="${pageContext.request.contextPath}/system">System</a></li>
                <li class="active"><a href="${pageContext.request.contextPath}/gantt">Gantt</a></li>
                <li><a href="#contact">About</a></li>
            </ul>
        </div>
        <!--/.nav-collapse -->
    </div>
</div>

<div class="container">

    <div id="app-container" class="app-container">
        <div id="tasks" class="tasks-container">
            <p></p>
        </div>

        <div id="gantt"></div>
        <div id="img_tasks"></div>
        <div id="img_system"></div>

    </div>

    <div class="modal fade" id="generateModal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title">Save tasks</h4>
                </div>
                <div class="modal-body">
                    <label for="gen-nodes-number">Total nodes</label>
                    <input type="number" id="gen-nodes-number" class="form-control" placeholder="15"
                           autofocus="autofocus">
                    <br>
                    <label for="gen-node-min-weight">Node min weight</label>
                    <input class="form-control" type="number" id="gen-node-min-weight" placeholder="1"/>
                    <br>
                    <label for="gen-node-max-weight">Node max weight</label>
                    <input class="form-control" type="number" id="gen-node-max-weight" placeholder="30"/>
                    <br>
                    <label for="gen-connectivity">Graph connectivity</label>
                    <input class="form-control" type="number" id="gen-connectivity" placeholder="0.5"
                           onkeydown="if (event.keyCode == 13) $('#generate-btn').click()"/>
                </div>

                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                    <button id="generate-btn" type="button" class="btn btn-primary" onclick="generate()"
                            data-dismiss="modal">Generate
                    </button>
                </div>
            </div>
            <!-- /.modal-content -->
        </div>
        <!-- /.modal-dialog -->
    </div>


</div>
<!-- /.container -->


<script type="text/javascript" src="../../js/apps/bullet.js"></script>
<!-- Bootstrap core JavaScript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
<script src="../../js/bootstrap.min.js"></script>
<script src="../../js/apps/cookies.js"></script>


<%--<script src="http://code.jquery.com/jquery-1.9.1.js"></script>--%>
<script>
    $(document).ready(function () {
        $.getJSON("/gantt/last",
                function (data) {
                    console.log(data);
                    var table = $('<table></table>');
                    var timeRow = $('<tr></tr>');
                    var thn = $('<th></th>').attr('width', 40).text("  ");
                    timeRow.append(thn);
                    for (i = 0; i <= data.length; i++) {
                        var th = $('<th></th>').attr('width', 30).text(i);
                        timeRow.append(th);
                    }
                    table.append(timeRow);

                    for (i = 0; i < data.tasks.length; i++) {
                        var row = $('<tr></tr>');
                        var queue = data.tasks[i];


                        var tdn = $('<td></td>').attr('width', 40).text(data.headers[i]);
                        if (data.headers[i].charAt(0) == 'P'){
                            tdn.addClass("highlight3");
                        }

                        row.append(tdn);
                        for (j = 0; j < queue.length; j++) {
                            var claz;
                            var text = queue[j].text;
                            if (text == '-') {
                                claz = "empty";
                            } else if (text.charAt(0) == 'T') {
                                claz = "highlight";
                            } else {
                                claz = "highlight2";
                            }
                            var td = $('<td></td>').addClass(claz)
                                    .attr('width', 30)
                                    .attr('colspan', queue[j].length)
                                    .text(queue[j].text);
                            row.append(td);
                        }
                        table.append(row);
                    }

                    $('#gantt').append(table);
                });
        d3.text("/file/last/tasks/load/image")
                .header("Content-type", "application/json")
                .get(function (error, text) {
                    var r = /\\u([\d\w]{4})/gi;
                    text = text.replace(r, function (match, grp) {
                        return String.fromCharCode(parseInt(grp, 16));
                    });
                    text = replaceAllBackSlash(text);
                    text = text.substring(1, text.length - 1);
                    $('#img_tasks').append($(text));
                });
        d3.text("/file/last/systems/load/image")
                .header("Content-type", "application/json")
                .get(function (error, text) {
                    var r = /\\u([\d\w]{4})/gi;
                    text = text.replace(r, function (match, grp) {
                        return String.fromCharCode(parseInt(grp, 16));
                    });
                    text = replaceAllBackSlash(text);
                    text = text.substring(1, text.length - 1);
                    $('#img_system').append($(text));
                });
    });

    function replaceAllBackSlash(targetStr) {
        var index = targetStr.indexOf("\\");
        while (index >= 0) {
            targetStr = targetStr.replace("\\", "");
            index = targetStr.indexOf("\\");
        }
        return targetStr;
    }
</script>
</body>
</html>
