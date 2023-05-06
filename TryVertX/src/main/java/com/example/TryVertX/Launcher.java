package com.example.TryVertX;


import io.vertx.core.DeploymentOptions;
import io.vertx.core.VertxOptions;

public class Launcher extends io.vertx.core.Launcher {

  public static void main(String[] args) {
    new Launcher().dispatch(args);
  }

  @Override
  public void beforeStartingVertx(VertxOptions options) {
    super.beforeStartingVertx(options);
  }

  @Override
  public void beforeDeployingVerticle(DeploymentOptions deploymentOptions) {
    deploymentOptions.setInstances(16);
    super.beforeDeployingVerticle(deploymentOptions);
  }
}
