FROM balenalib/raspberry-pi-debian:buster
RUN apt update
RUN apt -y full-upgrade
RUN apt -yq install pigpio python-pigpio python3-pigpio wget
RUN apt -y install python3-dev python3-rpi.gpio
### COPY docker/install-java.sh /install-java.sh
### RUN chmod u+x /install-java.sh && /install-java.sh
RUN sudo apt update
RUN sudo apt purge ca-certificates-java
RUN sudo apt install ca-certificates-java
#RUN sudo apt install -y openjdk-11-jdk
ENV LOG_LEVEL=debug
ENV TZ=Europe/Berlin
ENV DEMOMODE=false
EXPOSE 8023/tcp
EXPOSE 8023/udp
#EXPOSE 8085/tcp
## ADD extracts the tar
ADD ./unibo.raspberry-3.0.tar /
COPY ./*.pl ./unibo.raspberry-3.0/bin
COPY ./*.json ./unibo.raspberry-3.0/bin
COPY ./*.bin ./unibo.raspberry-3.0/bin
COPY ./*.txt ./unibo.raspberry-3.0/bin
COPY ./*.py ./unibo.raspberry-3.0/bin


WORKDIR /unibo.raspberry-3.0/bin
#COPY ../filerasp/*.pl ./
#COPY ../filerasp/*.json ./
#COPY ../filerasp/*.bin ./
#COPY ../filerasp/*.txt ./

#CMD ["bash"]
CMD ["bash", "unibo.raspberry"]