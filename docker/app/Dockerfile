FROM openjdk:17-oracle
LABEL maintainer="nguyenvv4"

# add user
RUN groupadd nv
RUN useradd nv -g nv -m -s /bin/bash
RUN echo "nv:dream" | chpasswd

# setup
ADD ./files/etc/profile /etc/profile
ENV TZ Asia/Ho_Chi_Minh
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
ENV LANG C.UTF-8
# see https://spring.io/guides/gs/spring-boot-docker/
VOLUME /tm
ADD ./app.jar app.jar
RUN sh -c 'touch /app.jar'
ENV JAVA_OPTS=""
EXPOSE 8080
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -jar /app.jar" ]
