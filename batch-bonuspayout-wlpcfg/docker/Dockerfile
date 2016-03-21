FROM websphere-liberty:kernel

RUN installUtility install --acceptLicense batchManagement-1.0 cdi-1.2

COPY BatchDB /opt/ibm/wlp/usr/shared/resources/BatchDB

COPY derby /opt/ibm/wlp/usr/shared/resources/derby

