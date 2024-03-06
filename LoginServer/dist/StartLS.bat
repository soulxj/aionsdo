@ECHO off
TITLE AION - Login Server Console
color 0F
:START
CLS
IF "%MODE%" == "" (
CALL PanelLS.bat
)

ECHO Starting AION Login Server in %MODE% mode.
JAVA %JAVA_OPTS% -cp ./lib/*;loginserver.jar com.aionemu.loginserver.LoginServer
SET CLASSPATH=%OLDCLASSPATH%
IF ERRORLEVEL 1 GOTO ERROR
IF ERRORLEVEL 0 GOTO END
:ERROR
ECHO.
ECHO Login Server has terminated abnormaly!
ECHO.
PAUSE
EXIT
:END
ECHO.
ECHO Login Server is terminated!
ECHO.
PAUSE
EXIT
