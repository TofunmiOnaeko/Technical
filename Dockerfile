FROM mcr.microsoft.com/mssql/server:2019-GA-ubuntu-16.04

ENV ACCEPT_EULA=Y

COPY ./sql /sql

EXPOSE 1433

# Make sure to run the script
CMD /bin/bash /run_sql_files.sh
