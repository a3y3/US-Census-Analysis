# US-Census-Analysis
An Apache Spark program that performs analysis on a the [US Cenus dataset](https://www.census.gov/data/tables/2017/demo/popest/counties-detail.html).

Specifically, it calculates the [Diversity Index](https://catalog.data.gov/dataset/diversity-index) for specific races during the years 2010-2017.

### Building the project
- Download the dataset and rename it to `data.csv`. Place it in the root folder.
- Build using Maven (`mvn package`)
- Run it to see the Spark program run locally and produce the following output:

### Example Output
```
+-------+----------------+-------------------+
| STNAME|         CTYNAME|    Diversity Index|
+-------+----------------+-------------------+
|Alabama|  Autauga County| 0.3509529354189893|
|Alabama|  Baldwin County|0.22703415227240026|
|Alabama|  Barbour County| 0.5201912665001125|
|Alabama|     Bibb County|0.36474615468663946|
|Alabama|   Blount County|0.07275405231720622|
|Alabama|  Bullock County|0.42975603086821046|
|Alabama|   Butler County| 0.5164475024792679|
|Alabama|  Calhoun County| 0.3779498849796942|
|Alabama| Chambers County| 0.5032526628314044|
                  .
                  .
                  .
                  .

```