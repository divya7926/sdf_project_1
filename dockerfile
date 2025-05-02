
# Use Python base image
FROM python:3.9-slim

# Set working directory
WORKDIR /app

# Copy your project files into the container
COPY . /app

# Install Java and Ant for building the Java project
RUN apt-get update && \
    apt-get install -y openjdk-17-jdk ant

# Run the Python script
ENTRYPOINT ["python", "Script.py"]
