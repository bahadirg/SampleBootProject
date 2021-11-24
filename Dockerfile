
# we are extending everything from tomcat:8.0 image ...

FROM tomcat:9.0.52-jdk11-adoptopenjdk-hotspot
MAINTAINER fbahadirg@gmail.com

# COPY path-to-your-application-war path-to-webapps-in-docker-tomcat
COPY ./target/SampleBootProject.war /usr/local/tomcat/webapps/

# normally this CMD not needed, but since tomcat starts before .WAR copied, app does not start, this ensures start after copy
CMD ["catalina.sh", "run"]