import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;

public class CensusCount {
    public static void main(String[] args){
        SparkConf sparkConf = new SparkConf();
        sparkConf.set("spark.master", "local[4]");
        sparkConf.setAppName("US Census Analysis");

        SparkSession spark = SparkSession.builder().config(sparkConf).getOrCreate();
        JavaSparkContext jsc = new JavaSparkContext(spark.sparkContext());
        jsc.close();
        spark.close();
    }


}
