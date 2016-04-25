from sidgoyal/bonuspayout-runtime:multiServer

MAINTAINER siddgoyal@gmail.com

RUN /opt/ibm/wlp/bin/server create BonusPayout 

ENV WLP_OUTPUT_DIR=/opt/ibm/wlp/usr/servers/  LOG_DIR=/opt/ibm/wlp/usr/servers/BonusPayout

ENV DB_HOST=localhost DB_PORT=1527 MESSAGE_HOST=localhost MESSAGE_PORT=7280

ADD common.config.xml /opt/ibm/wlp/usr/servers/BonusPayout

COPY BonusPayout.war /opt/ibm/wlp/usr/servers/BonusPayout/dropins

ARG SERVER_XML 

COPY $SERVER_XML /opt/ibm/wlp/usr/servers/BonusPayout/server.xml

CMD ["/opt/ibm/wlp/bin/server", "run", "BonusPayout"]
