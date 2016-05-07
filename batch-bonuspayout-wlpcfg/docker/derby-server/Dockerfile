FROM websphere-liberty:kernel

MAINTAINER siddgoyal@gmail.com

RUN rm -rf /opt/ibm/wlp 

EXPOSE 1527

RUN wget -q http://www-us.apache.org/dist//db/derby/db-derby-10.12.1.1/db-derby-10.12.1.1-bin.tar.gz -O /tmp/derby.tar.gz \
&& tar xf /tmp/derby.tar.gz -C /tmp \
&& mv /tmp/db-derby-10.12.1.1-bin /opt/derby \
&& chmod -R 755 /opt/derby \
&& rm /tmp/derby.tar.gz

ENV JAVA_HOME=/opt/ibm/java/jre \
    DERBY_INSTALL=/opt/derby

CMD ["/opt/derby/bin/startNetworkServer","-h","0.0.0.0"]
