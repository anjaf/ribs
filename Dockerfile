FROM openjdk:12-alpine
ARG RUN_AS_USER
ARG RUN_AS_GROUP
ARG PROXY_HOST
ARG PROXY_PORT
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

COPY ./target/biostudies.war ./
WORKDIR /tmp

EXPOSE 8080
CMD java -Dhttp.proxyHost=${PROXY_HOST} -Dhttp.proxyPort=${PROXY_PORT} -Dhttps.proxyHost=${PROXY_HOST} -Dhttps.proxyPort=${PROXY_PORT} -Djava.io.tmpdir=/tmp -Dlog-path=/tmp/logs -Dlog.level=debug -Dtomcat.hostname=ui -jar /biostudies.war