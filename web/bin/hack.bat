@echo off
rem /**
rem  * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
rem  *
rem  * Author: ThinkGem@163.com
rem  */
echo.
echo [信息] 打包Web工程，生成war/jar包文件。
echo.

%~d0
cd %~dp0

cd ..


call mvn clean package spring-boot:repackage -Dtest=HackTest -Dmaven.test.skip=false -U -Djeesite.initdata=true -Dflowable.databaseSchemaUpdate
cd bin
cmd /c msg %username% /time:0 /w "破解打包完成..."
pause