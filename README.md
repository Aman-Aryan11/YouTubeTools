# YouTube Tools

A Spring Boot web application with small utilities for working with YouTube videos. It uses the YouTube Data API v3 to search for video tags and retrieve video metadata, and it can build a direct high-resolution thumbnail URL from a video ID.

## Features

- **SEO tag generator** — searches YouTube for a title and displays tags from the primary and related videos.
- **Video data retriever** — displays a video's title, channel, publication date, description, tags, and thumbnail.
- **Thumbnail tool** — creates a direct `maxresdefault` thumbnail URL from a YouTube video URL or ID.
- Accepts standard `youtube.com/watch`, `youtu.be`, and embed URLs, as well as 11-character video IDs.

## Tech stack

- Java 25
- Spring Boot 4.1
- Maven (including the Maven Wrapper)
- Thymeleaf
- Spring WebClient
- YouTube Data API v3

## Prerequisites

- JDK 25
- A Google Cloud project with the **YouTube Data API v3** enabled
- A YouTube Data API key

## Configure the API key

The application reads its key from the `YOUTUBE_API_KEY` environment variable. Do not put a real key in `src/main/resources/application.properties`.

macOS/Linux:

```bash
export YOUTUBE_API_KEY="your_api_key_here"
./mvnw spring-boot:run
```

Windows PowerShell:

```powershell
$env:YOUTUBE_API_KEY = "your_api_key_here"
.\mvnw.cmd spring-boot:run
```

The application runs at [http://localhost:8080](http://localhost:8080) by default.

## Run locally

```bash
./mvnw clean test
./mvnw spring-boot:run
```

## Routes

| Route | Purpose |
| --- | --- |
| `/` | SEO tag generator page |
| `/thumbnail` | Thumbnail lookup page |
| `/video-details` | Video metadata page |
| `POST /youtube/search` | Searches for videos by title |
| `POST /get-thumbnail` | Returns a thumbnail URL for a submitted video URL or ID |
| `POST /video-details` | Retrieves metadata for a submitted video URL or ID |
