import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.*;
import org.apache.spark.sql.types.DataTypes;

public class CensusCount {
    private static final String DATASET = "data.csv";

    public static void main(String[] args) {
        SparkConf sparkConf = new SparkConf();
        sparkConf.set("spark.master", "local[4]");
        sparkConf.setAppName("US Census Analysis");

        SparkSession sparkSession = SparkSession.builder().config(sparkConf).getOrCreate();
        JavaSparkContext jsc = new JavaSparkContext(sparkSession.sparkContext());
        CensusCount censusCount = new CensusCount();
        censusCount.printData(sparkSession);
        jsc.close();
        sparkSession.close();
    }

    void printData(SparkSession sparkSession) {
        Dataset dataset = sparkSession.read()
                .option("header", "true")
                .option("delimiter", ",")
                .option("inferSchema", "true")
                .csv(DATASET);

        dataset = dataset.withColumn("STNAME", dataset.col("STNAME"))
                .withColumn("CTYNAME", dataset.col("CTYNAME"))
                .withColumn("YEAR", dataset.col("YEAR").cast(DataTypes.IntegerType))
                .withColumn("AGEGRP", dataset.col("AGEGRP").cast(DataTypes.IntegerType))
                .withColumn("WA_MALE", dataset.col("WA_MALE").cast(DataTypes.IntegerType))
                .withColumn("WA_FEMALE", dataset.col("WA_FEMALE").cast(DataTypes.IntegerType))
                .withColumn("BA_MALE", dataset.col("BA_MALE").cast(DataTypes.IntegerType))
                .withColumn("BA_FEMALE", dataset.col("BA_FEMALE").cast(DataTypes.IntegerType))
                .withColumn("IA_MALE", dataset.col("IA_MALE").cast(DataTypes.IntegerType))
                .withColumn("IA_FEMALE", dataset.col("IA_FEMALE").cast(DataTypes.IntegerType))
                .withColumn("AA_MALE", dataset.col("AA_MALE").cast(DataTypes.IntegerType))
                .withColumn("AA_FEMALE", dataset.col("AA_FEMALE").cast(DataTypes.IntegerType))
                .withColumn("NA_MALE", dataset.col("NA_MALE").cast(DataTypes.IntegerType))
                .withColumn("NA_FEMALE", dataset.col("NA_FEMALE").cast(DataTypes.IntegerType))
                .withColumn("TOM_MALE", dataset.col("TOM_MALE").cast(DataTypes.IntegerType))
                .withColumn("TOM_FEMALE", dataset.col("TOM_FEMALE").cast(DataTypes.IntegerType));

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
        dataset.show();
    }
}
