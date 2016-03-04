from sidgoyal/bonuspayout-runtime

RUN mkdir -p /opt/ibm/wlp/usr/servers/BonusPayout/dropins 

ADD server.xml /opt/ibm/wlp/usr/servers/BonusPayout 

ADD server.versioned.xml /opt/ibm/wlp/usr/servers/BonusPayout   

ADD bootstrap.properties /opt/ibm/wlp/usr/servers/BonusPayout

COPY BonusPayout.war /opt/ibm/wlp/usr/servers/BonusPayout/dropins

RUN  installUtility install --acceptLicense BonusPayout

CMD ["/opt/ibm/wlp/bin/server", "run", "BonusPayout"]
