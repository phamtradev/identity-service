# Multi-stage build để giảm kích thước image cuối cùng

# Stage 1: Build application
FROM ubuntu:22.04 AS build
WORKDIR /app

# Cài đặt OpenJDK 21 và Maven
RUN apt-get update && \
    apt-get install -y openjdk-21-jdk wget curl ca-certificates && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

# Cài đặt Maven
ARG MAVEN_VERSION=3.9.6
ARG MAVEN_HOME=/opt/maven

RUN wget https://archive.apache.org/dist/maven/maven-3/${MAVEN_VERSION}/binaries/apache-maven-${MAVEN_VERSION}-bin.tar.gz && \
    tar -xzf apache-maven-${MAVEN_VERSION}-bin.tar.gz -C /opt && \
    mv /opt/apache-maven-${MAVEN_VERSION} ${MAVEN_HOME} && \
    rm apache-maven-${MAVEN_VERSION}-bin.tar.gz

ENV PATH="${MAVEN_HOME}/bin:${PATH}"
ENV JAVA_HOME=/usr/lib/jvm/java-21-openjdk-amd64

# Copy pom.xml và download dependencies (layer caching)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source code và build
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Runtime image
FROM ubuntu:22.04
WORKDIR /app

# Cài đặt OpenJDK 21 JRE
RUN apt-get update && \
    apt-get install -y openjdk-21-jre-headless curl ca-certificates && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

ENV JAVA_HOME=/usr/lib/jvm/java-21-openjdk-amd64

# Tạo non-root user để chạy ứng dụng (security best practice)
RUN groupadd -r spring && useradd -r -g spring spring

# Copy JAR file từ build stage
COPY --from=build /app/target/bookstore_backend-0.0.1-SNAPSHOT.jar app.jar

# Change ownership
RUN chown spring:spring app.jar

# Switch to non-root user
USER spring:spring

# Expose port
EXPOSE 8080

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=40s --retries=3 \
  CMD curl -f http://localhost:8080/actuator/health || exit 1

# Run application với profile production
ENTRYPOINT ["java", \
  "-Djava.security.egd=file:/dev/./urandom", \
  "-Dspring.profiles.active=prod", \
  "-jar", \
  "app.jar"]
