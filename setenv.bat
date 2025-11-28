@echo off
REM Load environment variables from .env file for Tomcat

if exist ".env" (
    for /f "tokens=*" %%i in ('type .env') do (
        if not "%%i"=="" (
            if not "%%i:~0,1%%" == "#" (
                set "%%i"
            )
        )
    )
    echo Environment variables loaded from .env
) else (
    echo WARNING: .env file not found. Using default values or system environment variables.
)

REM Start Tomcat
echo Starting Tomcat...
