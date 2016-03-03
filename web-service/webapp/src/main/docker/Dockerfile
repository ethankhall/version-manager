FROM java:8
VOLUME /tmp

ENV DATABASE_URL localhost
ENV DATABASE_NAME version_manager
ENV DATABASE_USERNAME version_manager
ENV DATABASE_PASSWORD password
ENV JWT_SIGNING_KEY key

ENV GITHUB_KEY e26002913dce86419723
ENV GITHUB_SECRET c8d18cb9b65212ad8acb3d0603a85f6bc3aa704f
ENV GOOGLE_KEY 428210344673-bmar2c3qa5daop98pu9731l8v0tihrt1.apps.googleusercontent.com
ENV GOOGLE_SECRET Y4UZtHiDP1R2o5_INu9MWykI

ADD webapp.jar app.jar

RUN bash -c 'touch /app.jar'

ENTRYPOINT [ "java", "-Djava.security.egd=file:/dev/./urandom", "-Dspring.profiles.active=container", "-jar", "/app.jar"]
