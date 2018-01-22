#!/usr/bin/env bash

# Note: this should be working out of the box once you have Xcode installed.
# If not, good luck!

SCRIPT_DIRECTORY=$(dirname "$0")

open /Applications/Xcode.app/Contents/Developer/Applications/Simulator.app

# To have Karma capture the browser on ios, open Safari and go to
# http://localhost:9876
