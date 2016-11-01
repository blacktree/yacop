# YACOP

YACOP(Yet Another Container Orchestration Platform) is a container orchestration platform for YARN.

## Features

## Roadmap

- [x] Simple short-lived Docker container scheduling
- [x] Retreive meta data of Docker container
- [x] Enable Docker container access to secured Hadoop services
- [x] Read only volume mount
- [ ] Task re-run when failure
- [ ] Application master fault tolerance
- [ ] Client and AM RPC
- [ ] Dynamically scale tasks
- [x] Remove appname submission to AM
- [ ] Use MD5 hash to check if image has already been loaded
- [ ] Support "YARN" containers

## Setting up and running YACOP
1. A LinuxContainerExecutor enabled hadoop cluster
2. Enable YARN Registry for the cluster
3. Prepare your Docker image. It should have the same user (and uid) in it which is also used when run YACOP
4. Clone, build and run YACOP with the Docker image
```sh
git clone https://github.com/intel-hadoop/yacop.git
cd yacop
mvn clean package -DskipTests
```
```sh
docker pull centos:latest
cat <<'EOF' > artifact.json
{
    'name': 'hello-docker',
    'cpus': 2.0,
    'mem': 32.0,
    'instances': 2,
    'cmd': 'echo \"hello docker\"',
    'engine': {
        'type': 'DOCKER',
        'image': 'centos',
        'localImage': true
        'network': {
            'mode': 'overlay',
            'name': 'your_custom_network'
        }
    }
}
EOF
```
```sh
yarn jar target/yacop-1.0-SNAPSHOT.jar run -configFile artifact.json -jar target/yacop-1.0-SNAPSHOT.jar
```
## YACOP client
To list Docker container status of an applicatoin
```sh
yarn jar target/yacop-1.0-SNAPSHOT.jar resolve -applicationId <applicationId>
```
To list all applications history
```sh
yarn jar target/yacop-1.0-SNAPSHOT.jar registry
```
## YACOP Limitations
1.To support the overlay network mode, using the following configuration:
```sh
etc/hadoop/yarn-site.xml
<property>
    <name>yarn.nodemanager.runtime.linux.docker.allowed-container-networks</name>
    <value>host,none,bridge</value>
</property>
```
2.Docker container cannot access host by hostname since there is no DNS management currently.
