FROM jenkins

COPY plugins.txt /usr/share/jenkins/ref/
RUN /usr/local/bin/plugins.sh /usr/share/jenkins/ref/plugins.txt

COPY config /usr/share/jenkins/ref/

COPY docker /usr/local/bin/docker

