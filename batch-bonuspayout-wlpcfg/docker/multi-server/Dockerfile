FROM sidgoyal/bonuspayout-runtime:singleServer

MAINTAINER siddgoyal@gmail.com

RUN installUtility install --acceptLicense wasJmsClient-2.0

COPY derbyclient.jar /opt/ibm/wlp/usr/shared/resources/derbyclient/derbyclient.jar

# Clean-up artifacts related to derby-embedded which is only need by sinlgeServer
RUN rm -rf /opt/ibm/wlp/usr/shared/resources/BatchDB \ 
    && rm -rf /opt/ibm/wlp/usr/shared/resources/derby
