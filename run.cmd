set "HERE=%~dp0"
java "-Djava.library.path=%HERE%natives" -jar "%HERE%out\artifacts\platform_jar\platform.jar"
pause
