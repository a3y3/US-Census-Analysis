import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.sql.*;
import org.apache.spark.sql.types.DataTypes;
import scala.*;

/**
 * Uses Apache Spark to find the Diversity Index for a given set of races from the US
 * Census Data.
 */
public class CensusCount {
    private static final String DATASET = "data.csv";

    /**
     * Creates spark context and session and calls {@code runProgram}.
     * @param args STDIN.
     */
    public static void main(String[] args) {
        SparkConf sparkConf = new SparkConf();
        sparkConf.set("spark.master", "local[4]");
        sparkConf.setAppName("US Census Analysis");

        SparkSession sparkSession = SparkSession.builder().config(sparkConf).getOrCreate();
        JavaSparkContext jsc = new JavaSparkContext(sparkSession.sparkContext());
        CensusCount censusCount = new CensusCount();
        censusCount.runProgram(sparkSession);
        jsc.close();
        sparkSession.close();
    }

    /**
     * Runs the analysis by using Spark Datasets and JavaRDD.
     * @param sparkSession the current spark session passed from main.
     */
    private void runProgram(SparkSession sparkSession) {
        Dataset dataset = sparkSession.read()
                .option("header", "true")
                .option("delimiter", ",")
                .option("inferSchema", "true")
                .csv(DATASET);

        dataset = dataset.select("STNAME", "CTYNAME", "YEAR", "AGEGRP",
                "WA_MALE", "WA_FEMALE", "BA_MALE", "BA_FEMALE", "IA_MALE",
                "IA_FEMALE", "AA_MALE", "AA_FEMALE", "NA_MALE", "NA_FEMALE", "TOM_MALE"
                , "TOM_FEMALE");
        dataset = dataset.filter("AGEGRP=0");
        dataset = dataset.withColumn("WA_TOTAL",
                dataset.col("WA_MALE").plus(dataset.col("WA_FEMALE")))
                .withColumn("BA_TOTAL",
                        dataset.col("BA_MALE").plus(dataset.col("BA_FEMALE")))
                .withColumn("IA_TOTAL",
                        dataset.col("IA_MALE").plus(dataset.col("IA_FEMALE")))
                .withColumn("AA_TOTAL",
                        dataset.col("AA_MALE").plus(dataset.col("AA_FEMALE")))
                .withColumn("NA_TOTAL",
                        dataset.col("NA_MALE").plus(dataset.col("NA_FEMALE")))
                .withColumn("TOM_TOTAL",
                        dataset.col("TOM_MALE").plus(dataset.col("TOM_FEMALE")));
        dataset = dataset.select("STNAME", "CTYNAME", "YEAR", "AGEGRP", "WA_TOTAL",
                "BA_TOTAL", "IA_TOTAL", "AA_TOTAL", "NA_TOTAL", "TOM_TOTAL");

        Encoder<CensusRecord> reviewEncoder = Encoders.bean(CensusRecord.class);
        Dataset<CensusRecord> ds1 = dataset.as(reviewEncoder);
        ds1.show();

        Dataset<CensusRecord> ds2 = ds1.as(Encoders.bean(CensusRecord.class));
        ds2.show();
        JavaRDD<CensusRecord> javaRDD = ds2.javaRDD();

        JavaPairRDD<Tuple2<String, String>, CensusRecord> javaPairRDD =
                javaRDD.mapToPair((PairFunction<CensusRecord, Tuple2<String, String>,
                        CensusRecord>) record ->
                        new Tuple2<>(new Tuple2<>(record.STNAME, record.CTYNAME),
                                record));
        JavaPairRDD<Tuple2<String, String>, CensusRecord> result =
                javaPairRDD.reduceByKey((Function2<CensusRecord,
                        CensusRecord, CensusRecord>) (record1, record2) -> new CensusRecord(record1.STNAME,
                        record1.CTYNAME,
                        record1.YEAR + record2.YEAR,
                        record1.AGEGRP + record2.AGEGRP,
                        record1.WA_TOTAL + record2.WA_TOTAL,
                        record1.BA_TOTAL + record2.BA_TOTAL,
                        record1.IA_TOTAL + record2.IA_TOTAL,
                        record1.AA_TOTAL + record2.AA_TOTAL,
                        record1.NA_TOTAL + record2.NA_TOTAL,
                        record1.TOM_TOTAL + record2.TOM_TOTAL));

        JavaRDD<CensusRecord> reducedValues = result.values();
        Dataset<CensusRecord> reducedRecords = sparkSession.createDataset(reducedValues.rdd(),
                Encoders.bean(CensusRecord.class));

        dataset = reducedRecords.select("STNAME", "CTYNAME", "WA_TOTAL",
                "BA_TOTAL", "IA_TOTAL", "AA_TOTAL", "NA_TOTAL", "TOM_TOTAL");
        dataset = dataset.withColumn("T",
                dataset.col("WA_TOTAL")
                        .plus(dataset.col("BA_TOTAL"))
                        .plus(dataset.col("IA_TOTAL"))
                        .plus(dataset.col("AA_TOTAL"))
                        .plus(dataset.col("NA_TOTAL"))
                        .plus(dataset.col("TOM_TOTAL")));
        dataset = dataset.withColumn("T^2",
                dataset.col("T").multiply(dataset.col("T")));
        dataset = dataset.withColumn("SUM_WA",
                dataset.col("T")
                        .minus(dataset.col("WA_TOTAL"))
                        .multiply(dataset.col("WA_TOTAL")));
        dataset = dataset.withColumn("SUM_BA",
                dataset.col("T")
                        .minus(dataset.col("BA_TOTAL"))
                        .multiply(dataset.col("BA_TOTAL")));
        dataset = dataset.withColumn("SUM_IA",
                dataset.col("T")
                        .minus(dataset.col("IA_TOTAL"))
                        .multiply(dataset.col("IA_TOTAL")));
        dataset = dataset.withColumn("SUM_AA",
                dataset.col("T")
                        .minus(dataset.col("AA_TOTAL"))
                        .multiply(dataset.col("AA_TOTAL")));
        dataset = dataset.withColumn("SUM_NA",
                dataset.col("T")
                        .minus(dataset.col("NA_TOTAL"))
                        .multiply(dataset.col("NA_TOTAL")));
        dataset = dataset.withColumn("SUM_TOM",
                dataset.col("T")
                        .minus(dataset.col("TOM_TOTAL"))
                        .multiply(dataset.col("TOM_TOTAL")));

        dataset = dataset.withColumn("SUM_TOTAL",
                dataset.col("SUM_WA")
                        .plus(dataset.col("SUM_BA"))
                        .plus(dataset.col("SUM_IA"))
                        .plus(dataset.col("SUM_AA"))
                        .plus(dataset.col("SUM_NA"))
                        .plus(dataset.col("SUM_TOM")));
        dataset = dataset.withColumn("Diversity Index",
                dataset.col("SUM_TOTAL").divide(dataset.col("T^2")));

        dataset = dataset.select("STNAME", "CTYNAME", "Diversity Index");
        dataset = dataset.orderBy("STNAME", "CTYNAME");
        dataset.show();
    }
}
