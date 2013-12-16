#!/bin/bash
script_path="$(dirname "$(readlink -f "$0")")"
javaw "-Djava.library.path=$script_path/natives" -jar "$script_path/#yogo-platform.jar"
