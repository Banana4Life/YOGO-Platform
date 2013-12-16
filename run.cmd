@echo off
cls
set "HERE=%~dp0"
start javaw "-Djava.library.path=%HERE%natives" -jar "%HERE%yogo-platform.jar"
