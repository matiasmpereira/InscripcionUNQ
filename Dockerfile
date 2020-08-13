FROM ubuntu:latest

ENV DEBIAN_FRONTEND=noninteractive

EXPOSE 9999

RUN apt-get update
RUN apt-get install -y openjdk-11-jdk-headless 

##############
# APACHE PROXY
##############
RUN apt-get install -y apache2
RUN /usr/sbin/a2enmod ssl
RUN /usr/sbin/a2enmod proxy
RUN /usr/sbin/a2enmod proxy_balancer
RUN /usr/sbin/a2enmod proxy_http
############

WORKDIR /var/www/html

COPY ./dist/static .

#RUN cd /var/www/html

#RUN service apache2 start 
CMD service apache2 start && /usr/bin/bash
