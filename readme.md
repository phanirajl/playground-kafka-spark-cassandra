# Playground
This is a playground project for the frameworks:
* Apache Kafka
* Apache Spark
* Apache Cassandra

# Requirements
* Docker installed
* docker-compose installed

# Start Up Environment
1. Modify `docker-compose.yml` variable `KAFKA_ADVERTISED_HOST_NAME` with your docker host ip
2. `docker-compose up`

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

