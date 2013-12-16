set "HERE=%~dp0"
java "-Djava.library.path=%HERE%lib/natives-win32" -jar "%HERE%out\artifacts\platform_jar\platform.jar"
pause