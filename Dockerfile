FROM openjdk:12-alpine
ARG RUN_AS_USER
ARG RUN_AS_GROUP
ENV USER=docker
ENV UID=$RUN_AS_USER
ENV GID=$RUN_AS_GROUP
RUN addgroup --gid "$GID" "$USER" \
   && adduser \
   --disabled-password \
   --gecos "" \
   --home "$(pwd)" \
   --ingroup "$USER" \
   --no-create-home \
   --uid "$UID" \
   "$USER"

WORKDIR /opt/app
COPY --chown docker:docker ./target/biostudies.war /opt/app
EXPOSE 8080
CMD java -Dhttp.proxyHost="$proxy_host" -Dhttp.proxyPort="$proxy_port" -Dhttps.proxyHost="$proxy_host" -Dhttps.proxyPort="$proxy_port" -Djava.io.tmpdir=/tmp -Dlog-path=logs -Dlog.level=debug -Dtomcat.hostname=`hostname` -jar biostudies.war