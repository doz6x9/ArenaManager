@echo off
setlocal enabledelayedexpansion

set "BASE_DIR=%~dp0"
set "WRAPPER_DIR=%BASE_DIR%.mvn\wrapper"
set "PROPERTIES=%WRAPPER_DIR%\maven-wrapper.properties"

if not defined JAVA_HOME call :setJavaHomeFromPath

if not exist "%PROPERTIES%" (
  echo Missing Maven wrapper properties: %PROPERTIES% 1>&2
  exit /b 1
)

set "MAVEN_VERSION="
set "DISTRIBUTION_URL="
for /f "usebackq tokens=1,* delims==" %%A in ("%PROPERTIES%") do (
  if "%%A"=="mavenVersion" set "MAVEN_VERSION=%%B"
  if "%%A"=="distributionUrl" set "DISTRIBUTION_URL=%%B"
)

if "%MAVEN_VERSION%"=="" (
  echo mavenVersion is required in %PROPERTIES% 1>&2
  exit /b 1
)
if "%DISTRIBUTION_URL%"=="" (
  echo distributionUrl is required in %PROPERTIES% 1>&2
  exit /b 1
)

set "MAVEN_HOME=%WRAPPER_DIR%\dists\apache-maven-%MAVEN_VERSION%"
set "ZIP_FILE=%WRAPPER_DIR%\apache-maven-%MAVEN_VERSION%-bin.zip"

if not exist "%MAVEN_HOME%\bin\mvn.cmd" (
  if not exist "%WRAPPER_DIR%\dists" mkdir "%WRAPPER_DIR%\dists"
  echo Downloading Apache Maven %MAVEN_VERSION%...
  powershell -NoProfile -ExecutionPolicy Bypass -Command "[Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Tls12; Invoke-WebRequest -Uri '%DISTRIBUTION_URL%' -OutFile '%ZIP_FILE%'; Expand-Archive -LiteralPath '%ZIP_FILE%' -DestinationPath '%WRAPPER_DIR%\dists' -Force; Remove-Item -LiteralPath '%ZIP_FILE%'"
  if errorlevel 1 exit /b 1
)

call "%MAVEN_HOME%\bin\mvn.cmd" %*
exit /b %ERRORLEVEL%

:setJavaHomeFromPath
for /f "delims=" %%J in ('where java 2^>nul') do (
  set "JAVA_EXE=%%J"
  goto javaFound
)
goto :eof

:javaFound
for %%D in ("!JAVA_EXE!\..\..") do set "JAVA_HOME=%%~fD"
goto :eof
