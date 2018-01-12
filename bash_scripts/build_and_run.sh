#!/usr/bin/env bash

# NOTE: You must run this file from the root of the project. Boot does not
# provide a way to specify the paths to its properties file and build.boot.

SCRIPT_DIRECTORY=$(dirname "$0")

./$SCRIPT_DIRECTORY/build_release_artifact.sh
sudo java -jar release/my-app.jar
