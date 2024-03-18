# PROD v2: Pulse Backend

This project is my solution for the PROD Olympiad qualifying round (I didn't pass lol, but anyway it is a good experience for me and I wanna share it)

# About app

This is a social network for investors that uses some *environment variables*:

- `SERVER_PORT` &mdash; port that HTTP server is listening to (IP `0.0.0.0` and this port)

- `POSTGRES_JDBC_URL` &mdash; JDBC-string for connection to the PostgreSQL in the format of `jdbc:postgresql://{host}:{port}/{dbname}`

- `POSTGRES_USERNAME` &mdash; username for connection to the PostgreSQL.

- `POSTGRES_PASSWORD` &mdash; password for connection to the PostgreSQL.

- `RANDOM_SECRET` &mdash; secret-key (a-z, A-Z, 0-9) for the JWT

App is running through the Docker and builds from Dockerfile (docker-compose was restricted by the rules of olympiad)

# Usage

## Build

I'm tired so maybe finish it later (project is not done yet, it is working, but not as it wanted to be working) 