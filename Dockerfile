
# we are extending everything from tomcat:8.0 image ...

FROM tomcat:9.0
MAINTAINER fbahadirg@gmail.com

# COPY path-to-your-application-war path-to-webapps-in-docker-tomcat
COPY ./target/SampleBootProject-1.0.0.war /usr/local/tomcat/webapps/