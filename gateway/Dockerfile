FROM registry.gitlab.com/armdev/dockers/openjdk15
COPY target/gateway.jar /opt/gateway.jar
RUN useradd -ms /bin/sh mpaxele2050 \
    && echo "root:Docker!" | chpasswd \
    && chown -R mpaxele2050 /opt/
USER mpaxele2050
ENTRYPOINT ["java", "-XX:+UseZGC", "-XX:MinHeapSize=512M", "-XX:InitialHeapSize=512M", "-XX:MaxHeapSize=1G", \
            "-XX:ParallelGCThreads=30", "-XX:ConcGCThreads=3", \
            "-XX:SoftRefLRUPolicyMSPerMB=0","-XX:ZCollectionInterval=30", "-XX:ZFragmentationLimit=10", \
            "-XX:MaxGCPauseMillis=5", "-XX:+UseCompressedClassPointers", "-XX:-UseCompressedOops", "-XX:ZUncommitDelay=30", \
            "-XX:InitiatingHeapOccupancyPercent=35", "-XX:G1HeapRegionSize=16M", \
            "-XX:MinMetaspaceFreeRatio=50", "-XX:MaxMetaspaceFreeRatio=100",  \
            "-XX:+UnlockExperimentalVMOptions",  "-XshowSettings:vm" ,"-jar","/opt/gateway.jar"]
EXPOSE 2022
