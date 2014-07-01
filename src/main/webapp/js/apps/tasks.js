//// set up SVG for D3
//var width = 400,
//    height = 400,
//    colors = d3.scale.category10();
//
//var force;
//var fixed = 0;
//var drag_line;
//var r = 17;
//var path, circle;
//var selected_node, selected_link, mousedown_link, mousedown_node, mouseup_node;
//
//var svg = d3.select('#svg-container')
//    .append('svg')
//    .attr('width', width)
//    .attr('id', "svg-graph")
//    .attr('height', height);
//
//var name = getCookie("name");
//
//
//// set up initial nodes and links
////  - nodes are known by 'id', not by index in array.
////  - reflexive edges are indicated on the node (as a bold black circle).
////  - links are always source < target; edge directions are set by 'left' and 'right'.
//var nodes, links, lastNodeId, object;
//
//
//if (typeof myVar != 'undefined') {
//    alert(name);
//} else {
//    loadDefault();
//}
//
//function init() {
//
//    $("#svg-graph").empty();
//// init D3 force layout
//    force = d3.layout.force()
//        .nodes(nodes)
//        .links(links)
//        .size([width, height])
//        .linkDistance(70)
//        .charge(-250)
////    .gravity(0.1)
////    .chargeDistance(40)
//        .on('tick', tick);
//
//// define arrow markers for graph links
//    svg.append('svg:defs').append('svg:marker')
//        .attr('id', 'end-arrow')
//        .attr('viewBox', '0 -5 10 10')
//        .attr('refX', 6)
//        .attr('markerWidth', 3)
//        .attr('markerHeight', 3)
//        .attr('orient', 'auto')
//        .append('svg:path')
//        .attr('d', 'M0,-5L10,0L0,5')
//        .attr('fill', '#000');
//
//    svg.append('svg:defs').append('svg:marker')
//        .attr('id', 'start-arrow')
//        .attr('viewBox', '0 -5 10 10')
//        .attr('refX', 4)
//        .attr('markerWidth', 3)
//        .attr('markerHeight', 3)
//        .attr('orient', 'auto')
//        .append('svg:path')
//        .attr('d', 'M10,-5L0,0L10,5')
//        .attr('fill', '#000');
//
//
//// line displayed when dragging new nodes
//
//    drag_line = svg.append('svg:path')
//        .attr('class', 'link dragline hidden')
//        .attr('d', 'M0,0L0,0');
//
//// handles to link and node element groups
//
//    path = svg.append('svg:g').selectAll('path');
//    circle = svg.append('svg:g').selectAll('g');
//
//// mouse event vars
//
//    selected_node = null;
//    selected_link = null;
//    mousedown_link = null;
//    mousedown_node = null;
//    mouseup_node = null;
//
//
//// app starts here
//    svg.on('mousedown', mousedown)
//        .on('mousemove', mousemove)
//        .on('mouseup', mouseup);
//    d3.select(window)
//        .on('keydown', keydown)
//        .on('keyup', keyup);
//
//    restart();
//}
//
//function loadObject(o) {
//    object = JSON.parse(o);
//    nodes = object.nodes;
//    lastNodeId = object.last;
//    links = object.links;
//
//    for (i = 0; i < links.length; ++i) {
//        for (j = 0; j < nodes.length; ++j) {
//            if (nodes[j].id == links[i].source.id) {
//                links[i].source = nodes[j];
//            }
//            else if (nodes[j].id == links[i].target.id) {
//                links[i].target = nodes[j];
//            }
//        }
//    }
//
//    init();
//}
//
//function loadDefault() {
//    nodes = [
//        {"id": 0,"plinks":1, "fixed": fixed, "x": 188, "y": 36, "nodeWeight": 1, "index": 0, "weight": 2, "px": 188, "py": 36},
//        {"id": 1,"plinks":1, "fixed": fixed, "x": 147, "y": 110, "nodeWeight": 2, "index": 1, "weight": 3, "px": 147, "py": 110},
//        {"id": 2,"plinks":1, "fixed": fixed, "x": 100, "y": 194, "nodeWeight": 3, "index": 2, "weight": 1, "px": 100, "py": 194},
//        {"id": 3,"plinks":1, "fixed": fixed, "nodeWeight": 1, "x": 187.5, "y": 190, "index": 3, "weight": 1, "px": 187.5, "py": 190},
//        {"id": 4,"plinks":1, "fixed": fixed, "nodeWeight": 4, "x": 298.5, "y": 198, "index": 4, "weight": 3, "px": 298.5, "py": 198},
//        {"id": 5,"plinks":1, "fixed": fixed, "nodeWeight": 1, "x": 266.5, "y": 287, "index": 5, "weight": 1, "px": 266.5, "py": 287},
//        {"id": 6,"plinks":1, "fixed": fixed, "nodeWeight": 2, "x": 345.5, "y": 284, "index": 6, "weight": 1, "px": 345.5, "py": 284}
//    ];
//    lastNodeId = 6;
//    links = [
//        {"source": nodes[0], "target": nodes[1], "left": false, "right": true, "linkWeight": 1},
//        {"source": nodes[1], "target": nodes[2], "left": false, "right": true, "linkWeight": 2},
//        {"source": nodes[1], "target": nodes[3], "left": false, "right": true, "linkWeight": 1},
//        {"source": nodes[0], "target": nodes[4], "left": false, "right": true, "linkWeight": 2},
//        {"source": nodes[4], "target": nodes[5], "left": false, "right": true, "linkWeight": 3},
//        {"source": nodes[4], "target": nodes[6], "left": false, "right": true, "linkWeight": 1}
//    ];
//
//    init();
//}
//
//
//function createNodeWeightsTable(div, data, columns, headers) {
//    var table = d3.select(div).append("table").classed("table table-hover", true),
//        thead = table.append("thead"),
//        tbody = table.append("tbody");
//    // append the header row
//    thead.append("tr")
//        .selectAll("th")
//        .data(headers)
//        .enter()
//        .append("th")
//        .text(function (header) {
//            return header;
//        });
//    // create a row for each object in the data
//    var rows = tbody.selectAll("tr")
//        .data(data)
//        .enter()
//        .append("tr");
//    // create a cell in each row for each column
//    var cells = rows.selectAll("td")
//        .data(function (row) {
//            return columns.map(function (column) {
//                return {column: column, value: row[column], id: row["id"]};
//            });
//        })
//        .enter()
//        .append("td")
//        .text(function (d) {
//            return d.value;
//        }).attr("node", function (d) {
//            return d.id;
//        }).attr("type", "node");
//    return table;
//}
//
//function createLinkWeightsTable(div, data, columns, headers) {
//
//    var table = d3.select(div).append("table").classed("table table-hover", true),
//        thead = table.append("thead"),
//        tbody = table.append("tbody");
//    // append the header row
//    thead.append("tr")
//        .selectAll("th")
//        .data(headers)
//        .enter()
//        .append("th")
//        .text(function (header) {
//            return header;
//        });
//    // create a row for each object in the data
//    var rows = tbody.selectAll("tr")
//        .data(data)
//        .enter()
//        .append("tr");
//    // create a cell in each row for each column
//    var cells = rows.selectAll("td")
//        .data(function (row) {
//            return columns.map(function (column) {
//                return {column: column, value: row[column], source: row.source.id, target: row.target.id, left: row.left};
//            });
//        })
//        .enter()
//        .append("td")
//        .text(function (d) {
//            if (d.column == "link") {
//                return d.source + (d.left ? " <= " : " => ") + d.target;
//            } else {
//                return d.value;
//            }
//        }).attr("source", function (d) {
//            return d.source;
//        }).attr("target", function (d) {
//            return d.target;
//        }).attr("left", function (d) {
//            return d.left;
//        }).attr("type", "link");
//    return table;
//}
//
//function redrawTables() {
//    d3.select('#node-weights').select('table').remove();
//    d3.select('#link-weights').select('table').remove();
//    createNodeWeightsTable("#node-weights", nodes, ["id", "nodeWeight"], ["node", "weight"]);
//    createLinkWeightsTable("#link-weights", links, ["link", "linkWeight"], ["link", "weight"]);
//
//    $(function () {
//        $("td:nth-child(2)").dblclick(function () {
//            var OriginalContent = $(this).text();
//            var node = $(this).attr("node");
//            var source = $(this).attr("source");
//            var target = $(this).attr("target");
//            var left = $(this).attr("left") == "true";
//            var type = $(this).attr("type");
//            $(this).addClass("cellEditing");
//            $(this).html("<input type='text' size='3' value='" + OriginalContent + "' />");
//            $(this).children().first().focus();
//
//            $(this).children().first().keypress(function (e) {
//                if (e.which == 13) {
//                    var newContent = parseInt($(this).val());
//                    if (type == "node") {
//                        for (var i = 0; i < nodes.length; i++) {
//                            if (nodes[i].id == node) {
//                                nodes[i].nodeWeight = newContent;
//                                console.log(nodes[i].nodeWeight);
//                                circle.selectAll("text.weight").data(nodes, function (d) {return d.id}).text(function (d) {
//                                    return d.nodeWeight
//                                })
//                            }
//                        }
//                    } else {
//                        for (i = 0; i < links.length; ++i) {
//                            var link = links[i];
//                            if (link.source.id == source && link.target.id == target && link.left == left) {
//                                link.linkWeight = newContent;
//                            }
//                        }
//                    }
//                    $(this).parent().text(newContent);
//                    $(this).parent().removeClass("cellEditing");
//                }
//            });
//
//            $(this).children().first().blur(function () {
//                $(this).parent().text(OriginalContent);
//                $(this).parent().removeClass("cellEditing");
//            });
//        });
//
//    });
//
//}
//
//
//function resetMouseVars() {
//    mousedown_node = null;
//    mouseup_node = null;
//    mousedown_link = null;
//}
//
//// update force layout (called automatically each iteration)
//function tick() {
//    // draw directed edges with proper padding from node centers
//    path.attr('d', function (d) {
//        var deltaX = d.target.x - d.source.x,
//            deltaY = d.target.y - d.source.y,
//            dist = Math.sqrt(deltaX * deltaX + deltaY * deltaY),
//            normX = deltaX / dist,
//            normY = deltaY / dist,
//            sourcePadding = d.left ? 17 : 12,
//            targetPadding = d.right ? 17 : 12,
//            sourceX = d.source.x + (sourcePadding * normX),
//            sourceY = d.source.y + (sourcePadding * normY),
//            targetX = d.target.x - (targetPadding * normX),
//            targetY = d.target.y - (targetPadding * normY);
//        return 'M' + sourceX + ',' + sourceY + 'L' + targetX + ',' + targetY;
//    });
//
//    circle.attr('transform', function (d) {
//        return 'translate(' + d.x + ',' + d.y + ')';
//    });
//
//
//}
//
//// update graph (called when needed)
//function restart() {
//
//    // path (link) group
//    path = path.data(links);
//    redrawTables();
//    // update existing links
//    path.classed('selected', function (d) {
//        return d === selected_link;
//    })
//        .style('marker-start', function (d) {
//            return d.left ? 'url(#start-arrow)' : '';
//        })
//        .style('marker-end', function (d) {
//            return d.right ? 'url(#end-arrow)' : '';
//        });
//
//
//    // add new links
//    //var group = path.enter().append('svg:g');
//    path.enter().append('svg:path')
//        .attr('class', 'link')
//        .classed('selected', function (d) {
//            return d === selected_link;
//        })
//        .style('marker-start', function (d) {
//            return d.left ? 'url(#start-arrow)' : '';
//        })
//        .style('marker-end', function (d) {
//            return d.right ? 'url(#end-arrow)' : '';
//        })
//        .on('mousedown', function (d) {
//            if (d3.event.ctrlKey) return;
//
//            // select link
//            mousedown_link = d;
//            if (mousedown_link === selected_link) selected_link = null;
//            else selected_link = mousedown_link;
//            selected_node = null;
//            restart();
//        });
//
//    // remove old links
//    path.exit().remove();
//
//
//    // circle (node) group
//    // NB: the function arg is crucial here! nodes are known by id, not by index!
//    circle = circle.data(nodes, function (d) {
//        return d.id;
//    });
//
//    // update existing nodes (reflexive & selected visual states)
//    circle.selectAll('circle')
//        .style('fill', function (d) {
//            return (d === selected_node) ? d3.rgb(colors(d.id)).brighter().toString() : colors(d.id);
//        })
//    ;
//
//    // add new nodes
//    var g = circle.enter().append('svg:g');
//
//
//    g.append('svg:circle')
//        .attr('class', 'node')
//        .attr('r', r)
//        .style('fill', function (d) {
//            return (d === selected_node) ? d3.rgb(colors(d.id)).brighter().toString() : colors(d.id);
//        })
//        .style('stroke', function (d) {
//            return d3.rgb(colors(d.id)).darker().toString();
//        })
//
//        .on('mouseover', function (d) {
//            if (!mousedown_node || d === mousedown_node) return;
//            // enlarge target node
//            d3.select(this).attr('transform', 'scale(1.1)');
//        })
//        .on('mouseout', function (d) {
//            if (!mousedown_node || d === mousedown_node) return;
//            // unenlarge target node
//            d3.select(this).attr('transform', '');
//        })
//        .on('mousedown', function (d) {
//            if (d3.event.ctrlKey) return;
//
//            // select node
//            mousedown_node = d;
//            if (mousedown_node === selected_node) selected_node = null;
//            else selected_node = mousedown_node;
//            selected_link = null;
//
//            // reposition drag line
//            drag_line
//                .style('marker-end', 'url(#end-arrow)')
//                .classed('hidden', false)
//                .attr('d', 'M' + mousedown_node.x + ',' + mousedown_node.y + 'L' + mousedown_node.x + ',' + mousedown_node.y);
//
//            restart();
//        })
//        .on('mouseup', function (d) {
//            if (!mousedown_node) return;
//
//            // needed by FF
//            drag_line
//                .classed('hidden', true)
//                .style('marker-end', '');
//
//            // check for drag-to-self
//            mouseup_node = d;
//            if (mouseup_node === mousedown_node) {
//                resetMouseVars();
//                return;
//            }
//
//            // unenlarge target node
//            d3.select(this).attr('transform', '');
//
//            // add link to graph (update if exists)
//            // NB: links are strictly source < target; arrows separately specified by booleans
//            var source, target, direction;
//            if (mousedown_node.id < mouseup_node.id) {
//                source = mousedown_node;
//                target = mouseup_node;
//                direction = 'right';
//            } else {
//                source = mouseup_node;
//                target = mousedown_node;
//                direction = 'left';
//            }
//
//            var link;
//            link = links.filter(function (l) {
//                return (l.source === source && l.target === target);
//            })[0];
//
//            if (link) {
//                link[direction] = true;
//            } else {
//                link = {source: source, target: target, left: false, right: false, linkWeight: 1};
//                link[direction] = true;
//                links.push(link);
//            }
//
//            // select new link
//            selected_link = link;
//            selected_node = null;
//            restart();
//
//        });
//
//    // show node IDs
//    g.append('svg:text')
//        .attr('x', -6)
//        .attr('y', -4)
//        .attr('class', 'id')
//        .text(function (d) {
//            return "#" + d.id;
//        });
//
////    g.append("svg:line")
////        .attr("x1", -r)
////        .attr("y1", 0)
////        .attr("x2", r)
////        .style('stroke', function (d) {
////            return d3.rgb(colors(d.id)).darker();
////        })
////        .style("stroke-width", 1)
////        .attr("y2", 0);
//
//    g.append('svg:text')
//        .attr('x', -3)
//        .attr('y', 12)
//        .attr('class', 'weight')
//        .text(function (d) {
//            return  d.nodeWeight;
//        });
//
//
//    // remove old nodes
//    circle.exit().remove();
//
//    // set the graph in motion
//    force.start();
//
//}
//
//function mousedown() {
//    // prevent I-bar on drag
//    //d3.event.preventDefault();
//
//    // because :active only works in WebKit?
//    svg.classed('active', true);
//
//    if (d3.event.ctrlKey || mousedown_node || mousedown_link) return;
//
//    // insert new node at point
//    var point = d3.mouse(this),
//        node = {id: ++lastNodeId, fixed: fixed, nodeWeight: 1, plinks:1};
//    console.log(node);
//    node.x = point[0];
//    node.y = point[1];
//    nodes.push(node);
//
//    restart();
//    setCookie("tasks", JSON.stringify({nodes: nodes, links: links, last: lastNodeId}));
//}
//
//function mousemove() {
//    if (!mousedown_node) return;
//
//    // update drag line
//    drag_line.attr('d', 'M' + mousedown_node.x + ',' + mousedown_node.y + 'L' + d3.mouse(this)[0] + ',' + d3.mouse(this)[1]);
//
//    restart();
//}
//
//function mouseup() {
//    if (mousedown_node) {
//        // hide drag line
//        drag_line
//            .classed('hidden', true)
//            .style('marker-end', '');
//    }
//
//    // because :active only works in WebKit?
//    svg.classed('active', false);
//
//    // clear mouse event vars
//    resetMouseVars();
//}
//
//function spliceLinksForNode(node) {
//    var toSplice = links.filter(function (l) {
//        return (l.source === node || l.target === node);
//    });
//    toSplice.map(function (l) {
//        links.splice(links.indexOf(l), 1);
//    });
//}
//
//// only respond once per keydown
//var lastKeyDown = -1;
//
//function keydown() {
//    //d3.event.preventDefault();
//
//    if (lastKeyDown !== -1) return;
//    lastKeyDown = d3.event.keyCode;
//
//    // ctrl
//    if (d3.event.keyCode === 17) {
//        circle.call(force.drag);
//        svg.classed('ctrl', true);
//    }
//
//    if (!selected_node && !selected_link) return;
//
//    switch (d3.event.keyCode) {
//        case 8: // backspace
//        case 46: // delete
//            if (selected_node) {
//                nodes.splice(nodes.indexOf(selected_node), 1);
//                spliceLinksForNode(selected_node);
//            } else if (selected_link) {
//                links.splice(links.indexOf(selected_link), 1);
//            }
//            selected_link = null;
//            selected_node = null;
//            restart();
//            break;
//        case 66: // B
//            if (selected_link) {
//                // set link direction to both left and right
//                selected_link.left = true;
//                selected_link.right = true;
//            }
//            restart();
//            break;
//        case 70: // F
//            if (selected_node) {
//                // set link direction to both left and right
//                if (selected_node.fixed == 0){
//                    selected_node.fixed = 1;
//                } else {
//                    selected_node.fixed = 0;
//                }
//            }
//            restart();
//            break;
//        case 76: // L
//            if (selected_link) {
//                // set link direction to left only
//                selected_link.left = true;
//                selected_link.right = false;
//            }
//            restart();
//            break;
//        case 82: // R
//            if (selected_node) {
//                // toggle node reflexivity
//
//            } else if (selected_link) {
//                // set link direction to right only
//                selected_link.left = false;
//                selected_link.right = true;
//            }
//            restart();
//            break;
//    }
//}
//
//function keyup() {
//    lastKeyDown = -1;
//
//    // ctrl
//    if (d3.event.keyCode === 17) {
//        circle
//            .on('mousedown.drag', null)
//            .on('touchstart.drag', null);
//        svg.classed('ctrl', false);
//    }
//}
//
//
//
