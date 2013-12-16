#!/bin/bash
script_path="$(dirname "$(readlink -f "$0")")"
java "-Djava.library.path=$script_path/natives" -jar "$script_path/yogo-platform.jar" &
