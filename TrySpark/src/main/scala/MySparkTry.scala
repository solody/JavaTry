import org.apache.spark.{SparkConf, SparkContext}

object MySparkTry {
  def main(args: Array[String]): Unit = {
    val logFile = "/opt/bitnami/spark/README.md" // Should be some file on your system
    val conf = new SparkConf().setAppName("MySpark")
    val sc = new SparkContext(conf)
    val distFile = sc.textFile(logFile)
    val numAs = distFile.filter((s: String) => s.contains("a")).count
    val numBs = distFile.filter((s: String) => s.contains("b")).count
    System.out.println("Scala ran Lines with a: " + numAs + ", lines with b: " + numBs)
    sc.stop()
  }
}