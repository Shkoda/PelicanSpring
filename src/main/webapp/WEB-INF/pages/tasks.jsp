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
    <div class="debug_area">
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
                <li class="active"><a href="#">Tasks</a></li>
                <li><a href="#" onclick="saveAs('last', '/system')">System</a></li>
                <li><a href="#" onclick="saveAs('last', '/gantt')">Gantt</a></li>
                <li><a href="#contact">About</a></li>
            </ul>
        </div>
        <!--/.nav-collapse -->
    </div>
</div>

<div class="debug_area">

    <!-- <div class="starter-template">
      <h1>Bootstrap starter template</h1>

        <p class="lead">Use this document as a way to quickly start any new project.<br> All you get is this text and a
            mostly barebones HTML document.</p>


    </div>                         -->
    <!--<p><a class="btn btn-primary btn-lg" role="button" onclick="check()">Learn more &raquo;</a>
        <a class="btn btn-primary btn-lg" role="button" >Save</a></p> -->

    <div id="app-container" class="app-container">
        <div id="tasks" class="tasks-container">
            <p></p>

            <div class="alert alert-success alert-dismissable" id="alert-ok-block">
                <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                <span id="ok-description"></span>

            </div>

            <div class="alert alert-warning alert-dismissable" id="alert-error-block">
                <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                <span id="description"></span>

            </div>
            <div class="btn-group">
                <button type="button" class="btn btn-default" onclick="openSaveModal()">Save</button>
                <button type="button" class="btn btn-default" onclick="openLoadModel()">load</button>
                <button type="button" class="btn btn-default" onclick="loadByName('last')">load last</button>
                <button type="button" class="btn btn-default" onclick="check()">Check</button>
                <div class="btn-group">
                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                        Queue
                        <span class="caret"></span>
                    </button>
                    <ul class="dropdown-menu">
                        <li><a onclick="queue(1)" href="#">#1</a></li>
                        <li><a onclick="queue(3)" href="#">#3</a></li>
                        <li><a onclick="queue(16)" href="#">#16</a></li>
                    </ul>
                </div>
                <button type="button" class="btn btn-default" onclick="openGenerateModal()">Generate</button>
                <button type="button" class="btn btn-default" data-container="body" data-toggle="popover"
                        data-placement="bottom"
                        data-content="'Ctrl+click' for move nodes, 'delete' for remove nodes and links, double click on weight cell in table for changing it">
                    Help
                </button>
            </div>
          <div id="svg-container"></div>
        </div>

        <div id="node-weights" class="node-weight-container"></div>
        <div id="link-weights" class="link-weight-container"></div>
    </div>

    <div class="modal fade" id="loadModal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title">Load tasks</h4>
                </div>
                <div class="modal-body">
                    <select id="filenames" class="form-control">
                    </select>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    <button type="button" class="btn btn-primary" onclick="load()" data-dismiss="modal">Load</button>
                </div>
            </div>
            <!-- /.modal-content -->
        </div>
        <!-- /.modal-dialog -->
    </div>

    <div class="modal fade" id="saveModal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title">Save tasks</h4>
                </div>
                <div class="modal-body">
                    <input type="text" id="save-filename" class="form-control" placeholder="Filename"
                           autofocus="autofocus"
                           onkeydown="if (event.keyCode == 13) $('#save-file-btn').click()">
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                    <button id="save-file-btn" type="button" class="btn btn-primary" onclick="save()"
                            data-dismiss="modal">Save
                    </button>
                </div>
            </div>
            <!-- /.modal-content -->
        </div>
        <!-- /.modal-dialog -->
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

    function queue(n) {
        d3.text("/path/method/" + n)
                .header("Content-type", "application/json")
                .post(JSON.stringify({nodes: nodes, links: links}), function (error, text) {
                    var response = JSON.parse(text);
                    console.log(response);
                    if (response.status != "OK") {
                        $("#description")
                                .html("<strong>Error: </strong>cycles found in next node groups:<br> " + response.description);
                        $("#alert-error-block").slideDown(400);
                        $("#alert-ok-block").slideUp(400);
                    } else {
                        $("#ok-description")
                                .html("<strong>Queue: </strong>" + response.description);
                        $("#alert-ok-block").slideDown(400);
                        $("#alert-error-block").slideUp(400);
                    }
                });
    }

    function generate(){
        var n = $('#gen-nodes-number').val();
        var c = $('#gen-connectivity').val();
        var min = $('#gen-node-min-weight').val();
        var max = $('#gen-node-max-weight').val();
        d3.text("/graph/generate?n="+n+"&connectivity="+c+"&min="+min+"&max="+max)
                .header("Content-type", "application/json")
                .get(function (error, text) {
                    console.log(text);
                    loadObject(text);
                });
    }

    function check() {
        d3.text("/graph/cycles")
                .header("Content-type", "application/json")
                .post(JSON.stringify({nodes: nodes, links: links}), function (error, text) {
                    var response = JSON.parse(text);
                    console.log(response);
                    if (response.status != "OK") {
                        $("#description")
                                .html("<strong>Error: </strong>cycles found in next node groups:<br> " + response.description);
                        $("#alert-error-block").slideDown(400);
                        $("#alert-ok-block").slideUp(400);
                    } else {
                        $("#ok-description")
                                .html("<strong>No errors found</strong>");
                        $("#alert-ok-block").slideDown(400);
                        $("#alert-error-block").slideUp(400);
                    }
                });
    }

    function openLoadModel() {
        $.getJSON("/file/tasks/list",
                function (data) {
                    console.log(data);
                    var select = $("#filenames");
                    select.empty();
                    for (var i = 0; i < data.length; i++) {
                        select.append("<option>" + data[i] + "</option>")
                    }
                    $('#loadModal').modal({
                        keyboard: true
                    })
                });

    }

    function openSaveModal() {
        $('#saveModal').modal({
            keyboard: true
        })
    }

    function openGenerateModal() {
        $('#generateModal').modal({
            keyboard: true
        })
    }

    function save() {
        var filename = $('#save-filename').val();
        saveAs(filename);
    }


    function saveAs(filename, link) {
        var text = '\'' + JSON.stringify({nodes: nodes, links: links, last: lastNodeId}) + '\'';
        d3.text("/file/" + filename + "/tasks/save")
                .header("Content-type", "application/json")
                .post(text, function (error, text) {
//                    window.location.href = link;
                });
        text ='\'' + $('#svg-container').html() + '\'';
        d3.text("/file/" + filename + "/tasks/save/image")
                .header("Content-type", "application/json")
                .post(text, function (error, text) {
                   window.location.href = link;
                });
    }

    function load() {
        var filename = $('#filenames').val();
//        var text = '\'' + JSON.stringify({nodes: nodes, links: links}) + '\'';
        d3.text("/file/" + filename + "/tasks/load")
                .header("Content-type", "application/json")
                .get(function (error, text) {
                    var object = JSON.parse(text);
                    loadObject(object);
                });
    }

    function loadByName(filename) {
//        var text = '\'' + JSON.stringify({nodes: nodes, links: links}) + '\'';
        d3.text("/file/" + filename + "/tasks/load")
                .header("Content-type", "application/json")
                .get(function (error, text) {
                    var object = JSON.parse(text);
                    loadObject(object);
                });
    }

</script>
</body>
</html>
