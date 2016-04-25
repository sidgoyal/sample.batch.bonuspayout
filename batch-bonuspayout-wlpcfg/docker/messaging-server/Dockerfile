FROM websphere-liberty:kernel

MAINTAINER siddgoyal@gmail.com

RUN /opt/ibm/wlp/bin/server create BonusPayout

EXPOSE 7280

RUN installUtility install --acceptLicense wasJmsServer-1.0

COPY server.message-engine.xml /opt/ibm/wlp/usr/servers/BonusPayout/server.xml

CMD ["/opt/ibm/wlp/bin/server", "run", "BonusPayout"]

