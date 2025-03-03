package com.example.TryVertX;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.http.HttpServerOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;


public class MainVerticle extends AbstractVerticle {

  final static Logger logger = LoggerFactory.getLogger(MainVerticle.class);

  private static AtomicInteger staticInteger = new AtomicInteger(0);

  private int total = 1000;

  @Override
  public void start(Promise<Void> startPromise) {

    while (total > 0) {
      staticInteger.getAndUpdate(v -> {
        int s = v;
        return v+1;
      });
      total--;
    }


    logger.info("Done looping !!!! :" + staticInteger.get());

    /*staticInteger.getAndUpdate(v -> {
      if (v == 0) {
        logger.info("Adding +++++++++++++++++++");
        return 1;
      }
      return v;
    });*/

    /*vertx.sharedData().getLock("try", ar -> {
      if (staticInteger.get() == 0) {
        staticInteger.set(1);
      }
      ar.result().release();
    });*/
    HttpServerOptions options = new HttpServerOptions();
    vertx.createHttpServer().requestHandler(req -> {
      req.response()
        .putHeader("content-type", "text/plain")
        .putHeader(HttpHeaders.CONNECTION, "keep-alive")
        .end("Hello from Vert.x! " + staticInteger.get());
    }).listen(8888, http -> {
      if (http.succeeded()) {
        startPromise.complete();
        System.out.println("HTTP server started on port 8888");
      } else {
        startPromise.fail(http.cause());
      }
    });
  }
}
