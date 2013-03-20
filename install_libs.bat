@echo off

pushd %~dp0

call :INSTALL_LIB_JAR "libs\robolectric-0.9.4-all.jar" "com.pivotallabs" "robolectric" "0.9.4"
call :INSTALL_LIB_JAR "libs\maps-10.jar" "com.google.android.maps" "maps" "10"

popd

echo.
echo 関連ライブラリのインストールに成功しました
pause
goto :eof


:INSTALL_LIB_JAR
	set FILE=%~1
	set GROUP_ID=%~2
	set ARTIFACT_ID=%~3
	set VERSION=%~4
	cmd /c 	mvn install:install-file -Dfile="%FILE%" ^
							 -DgroupId="%GROUP_ID%" ^
							 -DartifactId="%ARTIFACT_ID%" ^
							 -Dversion="%VERSION%" ^
							 -Dpackaging=jar

	if not "%ERRORLEVEL%"=="0" (
		echo.
		echo %ARTIFACT_ID%のインストールに失敗しました
		pause
		exit 9
	)
	goto :eof
