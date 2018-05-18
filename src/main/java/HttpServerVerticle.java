import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.http.ServerWebSocket;
import io.vertx.core.json.JsonArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class HttpServerVerticle extends AbstractVerticle {

    private ArrayList<DispPoint> points = null;

    private ArrayList<DispPoint> getRealTimeData() {
        StationJSON stationJSON = new StationJSON();
        ArrayList<DispPoint> points = null;
        try {
            points = stationJSON.getXYData();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return points;
    }

    @Override
    public void start() {

        vertx.createHttpServer().websocketHandler((ServerWebSocket serverWebSocket) -> {
            // Get data and publish/sent to frontend via socket
            points = this.getRealTimeData();
            /**
            JsonArray jsonArray = new JsonArray(points);
            serverWebSocket.writeFinalTextFrame(jsonArray.toString());
             */

            int counter = points.size() - 1;
            while (counter > 0) {

                DispPoint point = points.get(counter);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("x", point.getX());
                jsonObject.put("y", point.getY());
                //serverWebSocket.writeFinalTextFrame(jsonObject.toString());
                counter --;
                vertx.<String>executeBlocking(future -> {
                    try {
                        Thread.sleep(300);
                    } catch (Exception ignore) {
                    }
                    String result = jsonObject.toString();
                    future.complete(result);
                }, (AsyncResult<String> res) -> {
                    if (res.succeeded()) {
                        serverWebSocket.writeFinalTextFrame(res.result());
                    } else {
                        res.cause().printStackTrace();
                    }
                });
            }
        }).requestHandler(httpServerRequest -> {
            httpServerRequest.response().sendFile("index.html");
        }).listen(8080);
    }
}
