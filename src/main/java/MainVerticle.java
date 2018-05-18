import io.vertx.core.Vertx;
import io.vertx.rxjava.core.AbstractVerticle;

public class MainVerticle extends AbstractVerticle {

    // Deploy & Start Vert.x server
   public static void main(String[] args) {
       
       Vertx vertx = Vertx.vertx();
       vertx.deployVerticle(new HttpServerVerticle());
   }
}
