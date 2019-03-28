FROM postgres

ENV POSTGRES_DB barbershop
ENV POSTGRES_USER barbershop
ENV POSTGRES_PASSWORD 12345

COPY init.sql /docker-entrypoint-initdb.d/10-init.sql