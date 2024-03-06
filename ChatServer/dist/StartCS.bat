@ECHO off
TITLE AION - Chat Server Console
:START
CLS
IF "%MODE%" == "" (
CALL PanelCS.bat
)
ECHO Starting AION Chat Server in %MODE% mode.
JAVA %JAVA_OPTS% -cp ./lib/*;chatserver.jar com.aionemu.chatserver.ChatServer
SET CLASSPATH=%OLDCLASSPATH%
IF ERRORLEVEL 2 GOTO START
IF ERRORLEVEL 1 GOTO ERROR
IF ERRORLEVEL 0 GOTO END
:ERROR
ECHO.
ECHO Chat Server has terminated abnormaly!
ECHO.
PAUSE
EXIT
:END
ECHO.
ECHO Chat Server is terminated!
ECHO.
PAUSE
EXIT
