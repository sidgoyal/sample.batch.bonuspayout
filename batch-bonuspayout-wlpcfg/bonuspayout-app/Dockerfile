from bonuspayout-runtime

RUN mkdir -p /opt/ibm/wlp/usr/servers/BonusPayout/dropins 

COPY server.xml /opt/ibm/wlp/usr/servers/BonusPayout 

COPY BonusPayout.war /opt/ibm/wlp/usr/servers/BonusPayout/dropins

COPY runToCompletionParms.txt /opt/ibm/wlp/usr/shared/resources/

RUN  echo 1 | /opt/ibm/wlp/bin/installUtility install BonusPayout


