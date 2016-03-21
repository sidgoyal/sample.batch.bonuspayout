from sidgoyal/bonuspayout-runtime

MAINTAINER siddgoyal@gmail.com

RUN /opt/ibm/wlp/bin/server create BonusPayout 

ENV WLP_OUTPUT_DIR=/opt/ibm/wlp/usr/servers/  LOG_DIR=/opt/ibm/wlp/usr/servers/BonusPayout

ADD server.xml /opt/ibm/wlp/usr/servers/BonusPayout

ADD server.versioned.xml /opt/ibm/wlp/usr/servers/BonusPayout 

ADD bootstrap.properties /opt/ibm/wlp/usr/servers/BonusPayout

COPY BonusPayout.war /opt/ibm/wlp/usr/servers/BonusPayout/dropins

#VOLUME /opt/ibm/wlp/usr/shared/resources/BatchDB 

CMD ["/opt/ibm/wlp/bin/server", "run", "BonusPayout"]
