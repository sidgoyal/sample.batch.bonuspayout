from sidgoyal/bonuspayout-runtime:singleServer

MAINTAINER siddgoyal@gmail.com

RUN /opt/ibm/wlp/bin/server create BonusPayout 

ENV WLP_OUTPUT_DIR=/opt/ibm/wlp/usr/servers/  LOG_DIR=/opt/ibm/wlp/usr/servers/BonusPayout

ADD server.versioned.xml /opt/ibm/wlp/usr/servers/BonusPayout

ADD bootstrap.properties /opt/ibm/wlp/usr/servers/BonusPayout

COPY BonusPayout.war /opt/ibm/wlp/usr/servers/BonusPayout/dropins

CMD ["/opt/ibm/wlp/bin/server", "run", "BonusPayout"]
