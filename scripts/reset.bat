@echo off
setlocal

REM Delete mysql-data folder
set "folderName=mysql-data"

REM Combine the current path and the mysql-data folder
set "folderPath=%cd%\%folderName%"

REM Check if the folder exists before attempting to delete it
if exist "%folderPath%" (
    rmdir /s /q "%folderPath%"
    echo SOAPService mysql-data folder deleted successfully.
) else (
    echo SOAPService mysql-data folder does not exist.
)

endlocal

REM Delete the Docker image with the tag "moments-soap-service:latest"
docker rmi moments-soap-service:latest

echo.