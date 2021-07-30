FROM hseeberger/scala-sbt:16.0.2_1.5.5_2.13.6
LABEL maintainer="alexander.dobrynin@th-koeln.de"

WORKDIR /cp-backend
COPY . .
RUN sbt compile
CMD sbt run