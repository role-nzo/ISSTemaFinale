FROM debian:stable
RUN apt update
RUN apt -y full-upgrade
RUN apt purge ca-certificates-java
RUN apt -y install ca-certificates-java
EXPOSE 8022
## ADD extracts the tar
ADD ./unibo.coldstorageservice-1.0.tar /
WORKDIR /unibo.coldstorageservice-1.0/bin
COPY ./*.pl ./
COPY ./*.json ./
COPY ./*.bin ./
COPY ./*.txt ./


CMD ["bash", "unibo.coldstorageservice"]
