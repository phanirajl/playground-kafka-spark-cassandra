# Playground
This is a playground project for the frameworks:
* Apache Kafka
* Apache Spark
* Apache Cassandra

# Requirements
* Docker installed
* docker-compose installed

# Start Up Environment
0. `docker-compose rm -svf`
1. `docker-compose up`
2. start `ApplicationKafkaReader` in IntelliJ
3. start `ApplicationKafkaWriter` in IntelliJ

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

