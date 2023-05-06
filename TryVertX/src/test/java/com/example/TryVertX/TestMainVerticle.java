package com.example.TryVertX;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.junit5.Timeout;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

@ExtendWith(VertxExtension.class)
public class TestMainVerticle {

  @BeforeEach
  void deploy_verticle(Vertx vertx, VertxTestContext testContext) {
    DeploymentOptions deploymentOptions = new DeploymentOptions();
    deploymentOptions.setInstances(2);
    deploymentOptions.setConfig(new JsonObject(loadJsonFileResource("conf-test.json")));
    vertx.deployVerticle(MainVerticle.class, deploymentOptions, testContext.succeeding(id -> testContext.completeNow()));
  }

  @Test
  @Timeout(value = 600, timeUnit = TimeUnit.SECONDS)
  void verticle_deployed(Vertx vertx, VertxTestContext testContext) {
    vertx.setTimer(600 * 1000L, ar -> {
      testContext.completeNow();
    });
  }

  public String loadJsonFileResource(String filePath) {
    try {
      URL url = MainVerticle.class.getClassLoader().getResource(filePath);
      InputStream inputStream = null;
      assert url != null;
      inputStream = url.openConnection().getInputStream();
      // Scanner will handle the charset.
      Scanner scanner = new Scanner(inputStream);
      StringBuilder stringBuilder = new StringBuilder();
      while (scanner.hasNextLine()) {
        stringBuilder.append(scanner.nextLine());
      }
      return stringBuilder.toString();
    } catch (IOException e) {
      return "";
    }
  }
}
