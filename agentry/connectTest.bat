@echo off
REM Find current Agentry JAR used by the Server
pushd ..\..\plugins
for /r %%f in (com.sap.mobile.platform.server.agentry.core_*) do (
	REM if several versions exist on the Server, this will select the greatest version number
	set AgentryJAR=%%f
)
REM return to where this batch file is
popd
rem ---- copy the classPath from the agentry.ini file or change below as required
set classPath=%AgentryJAR%;./Java/Agentry-v5.jar;./Java;./Java/common-20160901.jar;./Java/sapsso.jar;./sapjco3.jar;./Java/ini4j.jar;./Java/log4j-core.jar;./Java/log4j-api.jar
REM ECHO %classPath%
rem --- run the simple ConnectTest class
java -cp %classpath% com.syclo.sap.ConnectTest
