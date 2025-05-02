# # Use the official Python base image from Docker Hub
# FROM python:3.9-slim

# # Set the working directory inside the container
# WORKDIR /app

# # Copy the Python script and other files into the container
# COPY Script.py /app

# # Install any required Python packages (if any) - modify this line based on your needs
# # For example, if you have a requirements.txt file:
# # COPY requirements.txt /app
# # RUN pip install --no-cache-dir -r requirements.txt

# # Run the Python script
# CMD ["python", "Script.py"]
# Use Python base image
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
