# Narwhal

Narwhal is a YARN application for Docker container orchestration for YARN.

## Features

## Roadmap

- [x] Simple short-lived Docker container scheduling
- [ ] Retreive meta data of Docker container
- [ ] Secured Docker container
- [ ] Task re-run when failure
- [ ] Application master fault tolerance
- [ ] Client and AM RPC
- [ ] Dynamically scale tasks

## Setting up and running Narwhal
```sh
git clone ..
mvn clean package -DskipTests
```
```sh
vim artifact.json
```
```sh
{
    "name": "simple-docker",
    "cpus": 2,
    "mem": 32,
    "instances": 2,
    "cmd": "echo hello",
    "image": "centos",
    "local": true
}
```
```sh
yarn jar target/narwhal-1.0-SNAPSHOT.jar -configFile artifact.json -jar target/narwhal-1.0-SNAPSHOT.jar
```
## Narwhal client
