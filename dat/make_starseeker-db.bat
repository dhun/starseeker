@echo off

set DB_FILE=starseeker.db

set SQLFILE=%~dp0\initial.sql
set SQLITE3=D:\dev\_opt\sqlite3\sqlite3



cd /d %~dp0\original_data
(echo.) > %SQLFILE%
(echo .read constellation.sql)      >> %SQLFILE%
(echo .read constellation_name.sql) >> %SQLFILE%
(echo .read fk_name.sql)            >> %SQLFILE%
(echo .read fk_data.sql)            >> %SQLFILE%
(echo .read custom_name.sql)        >> %SQLFILE%
"%SQLITE3%" "..\%DB_FILE%" < "%SQLFILE%"



cd /d %~dp0\convert
(echo.) > %SQLFILE%
(echo .read 100-create-star_data.sql)      >> %SQLFILE%
(echo .read 110-create-horoscope_data.sql) >> %SQLFILE%
(echo .read 120-create-horoscope_path.sql) >> %SQLFILE%
"%SQLITE3%" "..\%DB_FILE%" < "%SQLFILE%"



pause
del %SQLFILE%
