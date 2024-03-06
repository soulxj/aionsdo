@echo off
TITLE Aion Lightning - Game Server Console
PATH=C:\Program Files\Java\jdk1.8.0_202\bin
REM NOTE: Remove tag REM from previous line.
:START
CLS

echo.

echo Starting Aion Lightning Version 2.4 Game Server.

echo.

REM -------------------------------------  
REM Default parameters for a basic server.
java -Xms1280m -Xmx3192m -XX:MaxHeapSize=3192m -Xdebug -ea -cp ./libs/*;Encom-Game-1.8.jar com.aionemu.gameserver.GameServer
REM -------------------------------------
SET CLASSPATH=%OLDCLASSPATH%

if ERRORLEVEL 2 goto restart
if ERRORLEVEL 1 goto error
if ERRORLEVEL 0 goto end

REM Restart...
:restart
echo.
echo Administrator Restart ...
echo.
goto start

REM Error...
:error
echo.
echo Server terminated abnormaly ...
echo.
goto end

REM End...
:end
echo.
echo Server terminated ...
echo.
pause
