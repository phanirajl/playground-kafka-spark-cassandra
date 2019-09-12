# Playground
This is a playground project for the frameworks:
* Apache Kafka
* Apache Spark
* Apache Cassandra

# Requirements
* Docker installed
* docker-compose installed

# Startup
## Prerequisite
1. `sbt`
2. `clean`
3. `assembly`
4. copy `late-events-assembly-0.0.1-SNAPSHOT.jar` from `late-events/target/scala-2.11/` to `late-events/script/`

## Startup - Environment
1. `docker-compose rm -svf`
2. `docker-compose up`

## Startup - TopicReader
1. `sbt`
2. `project topicReader`
3. `runMain org.playground.topic.reader.Application`

## Startup - Generator
1. `sbt`
2. `project generator`
3. `runMain org.playground.generator.Application`

## Startup - LateEvents Application
1. `docker ps`
2. Go into spark-master container: `docker exec -it <container-id> bash`
8. `./bin/spark-submit   --class org.playground.late.events.Application --master spark://spark-master:7077 --executor-memory 1G --total-executor-cores 2 --packages org.apache.spark:spark-sql-kafka-0-10_2.11:2.4.0 late-events/late-events-assembly-0.0.1-SNAPSHOT.jar`

# Shut Down
`docker-compose stop`

# Modules
|  Module                              |  Description                                  |
|:------------------------------------:|:---------------------------------------------:|
| [late-events](late-events/readme.md) | How to handle incoming late events with spark |
|                                      |                                               |

# Links:

## Kafka
* https://ertan-toker.de/apache-kafka-mit-docker-compose/
* http://wurstmeister.github.io/kafka-docker/

## Spark
* https://gitlab.com/dsncode/spark-docker
* https://hub.docker.com/r/tashoyan/docker-spark-submit/ 

