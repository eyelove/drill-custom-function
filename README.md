# Custom Function for Apache Drill

This project contains a simple example for a custom function for Apache Drill.

This functions is a mask one that allows the following queries to be executed:

```
SELECT FULL_NAME , SHA1(FULL_NAME) FROM cp.`employee.json` LIMIT 5;
+------------------+-------------------------------------------+
|    full_name     |                  EXPR$1                   |
+------------------+-------------------------------------------+
| Sheri Nowmer     | 360b44bc053bebdc6a3f2f843488411f06ef0773  |
| Derrick Whelply  | b09612c53591f39166132ff25c17aa66d7827be7  |
| Michael Spence   | 2aac36697c0d472e210aa88fbaa1928afd36810f  |
| Maya Gutierrez   | 11cf1c7b3022e66694b507866793c3e48e3c4383  |
| Roberta Damstra  | 06275ae482a7ee10bbe20a784b6dfb68f9906269  |
+------------------+-------------------------------------------+
```


## How to Compile Install

Clone and compile.

```

git clone https://github.com/eyelove/drill-custom-function.git

cd drill-custom-function

mvn clean package

```

Now download and unpack Apache Drill.

```
wget http://getdrill.org/drill/download/apache-drill-1.3.0.tar.gz
tar xvf apache-drill-1.3.0.tar.gz
```

Copy the jar files from your functions into the 3rdparty directory in the Drill distro

```
cp drill-custom-function/target/*.jar apache-drill-1.1.0/jars/3rdparty
```

Now run drill and test the results

```
$ cd apache-drill-1.3.0/
$ bin/drill-embedded
0: jdbc:drill:zk=local>
SELECT FULL_NAME , SHA1(FULL_NAME) FROM cp.`employee.json` LIMIT 5;
+------------------+-------------------------------------------+
|    full_name     |                  EXPR$1                   |
+------------------+-------------------------------------------+
| Sheri Nowmer     | 360b44bc053bebdc6a3f2f843488411f06ef0773  |
| Derrick Whelply  | b09612c53591f39166132ff25c17aa66d7827be7  |
| Michael Spence   | 2aac36697c0d472e210aa88fbaa1928afd36810f  |
| Maya Gutierrez   | 11cf1c7b3022e66694b507866793c3e48e3c4383  |
| Roberta Damstra  | 06275ae482a7ee10bbe20a784b6dfb68f9906269  |
+------------------+-------------------------------------------+
```


