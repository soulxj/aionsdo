#!/bin/sh
java -Xms8m -Xmx32m -ea -Xbootclasspath/p:./lib/jsr166.jar -cp ./lib/*:loginserver.jar com.aionemu.loginserver.LoginServer
lspid=$!
echo ${lspid} > loginserver.pid
echo "LoginServer started!"
