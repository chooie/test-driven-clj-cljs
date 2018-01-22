#!/usr/bin/env bash

SCRIPT_DIRECTORY=$(dirname "$0")

~/Library/Android/sdk/tools/emulator \
  -avd 'TEST_BROWSER_IN_EMULATOR_AVD' \
  -dns-server 8.8.8.8

# To have Karma capture the browser on android, open Chrome and go to
# http://10.0.2.2:9876
