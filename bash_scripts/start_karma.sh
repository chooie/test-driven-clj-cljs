#!/usr/bin/env bash

SCRIPT_DIRECTORY=$(dirname "$0")

./node_modules/karma/bin/karma \
  start \
  $SCRIPT_DIRECTORY/../tool_configurations/karma.config.js
