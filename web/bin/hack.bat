@echo off
rem /**
rem  * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
rem  *
rem  * Author: ThinkGem@163.com
rem  */
echo.
echo [��Ϣ] ���Web���̣�����war/jar���ļ���
echo.

%~d0
cd %~dp0

cd ..


call mvn clean package spring-boot:repackage -Dtest=HackTest -Dmaven.test.skip=false -U -Djeesite.initdata=true -Dflowable.databaseSchemaUpdate
cd bin
cmd /c msg %username% /time:0 /w "�ƽ������..."
pause