

var dataseries;

var socket;

if (window.WebSocket) {

    socket = new WebSocket("ws://localhost:8080");

    socket.onmessage = function(event) {

        document.getElementById("dataInfo").innerHTML = "Last Data:" + event.data;
        /**
        var pointList = JSON.parse(event.data);
        for (var i = 0; i < pointList.length; i ++) {
            var counter = pointList[i];
            alert("Point " + counter.x);
            var npoint = [parseInt(counter.x), parseInt(counter.y)];
            dataseries.addPoint(npoint);
        }*/

        //alert("Data! " + event.data);
        var point = JSON.parse(event.data);
        //alert('json ok' + point)
        var npoint = [parseInt(point.x),parseInt(point.y)];
        dataseries.addPoint(npoint);
    }
} else {
    alert("Your browser does not support Websockets. (Use Chrome)");
}

function readData (event) {
    var point = JSON.parse(event.data);
    //alert('json ok' + point)
    var npoint = [parseInt(point.x),parseInt(point.y)];
    dataseries.addPoint(npoint);
}

function sleep(miliseconds) {
    var currentTime = new Date().getTime();

    while (currentTime + miliseconds >= new Date().getTime()) {
    }
}

// No need to send, test purpose
function send(message) {

    if (!window.WebSocket) {
        return;
    }
    if (socket.readyState == WebSocket.OPEN) {
        alert("Socket is ready!")
        socket.send(message);
    } else {
        alert("The socket is not open.");
    }
}

$(function () {
    var chart;
    $(document).ready(function() {
        chart = new Highcharts.Chart({
            chart: {
                backgroundColor: {
                    linearGradient: [0, 0, 0, 400],
                    stops: [
                        [0, 'rgb(250, 180, 180)'],
                        [1, 'rgb(255, 240, 240)']
                    ]
                },
                renderTo: 'container',
                type: 'scatter',
                margin: [70, 50, 60, 80],
                events: {
                    load: function(e) {

                        dataseries = this.series[0];
                    }
                }
            },
            title: {
                text: 'Vert.x Real Time Chart Demo'
            },
            subtitle: {
                text: 'SMHI STN 159880 Y: Temperature & X :Date'
            },

            boost: {
                useGPUTranslations: true
            },

            xAxis: {
                title: {
                    text: "Time"
                },
                minPadding: 0.2,
                maxPadding: 0.2,
                maxZoom: 60
            },
            yAxis: {
                title: {
                    text: 'Value'
                },
                minPadding: 0.2,
                maxPadding: 0.2,
                maxZoom: 60,
                plotLines: [{
                    value: 0,
                    width: 1,
                    color: '#FF0000'
                }]
            },
            legend: {
                enabled: false
            },
            exporting: {
                enabled: false
            },
            plotOptions: {
                line: {
                    dataLabels: {
                        color: '#CCC'
                    },
                    marker: {
                        lineColor: '#333'
                    }
                },
                series: {
                    lineWidth: 2,
                    color: '#FF0000',
                    point: {
                        events: {
                            'click': function() {
                                if (this.series.data.length > 1) this.remove();
                            }
                        }
                    }
                }
            },
            series: [{
                data: [[0, 0]]
            }]
        });
    });

});

