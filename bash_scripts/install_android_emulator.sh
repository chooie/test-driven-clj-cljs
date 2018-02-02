#!/usr/bin/env bash

SCRIPT_DIRECTORY=$(dirname "$0")
ANDROID_PACKAGE='system-images;android-25;google_apis;x86'

# Install android SDK
~/Library/Android/sdk/tools/bin/sdkmanager $ANDROID_PACKAGE

# Create Android Virtual Device
~/Library/Android/sdk/tools/bin/avdmanager \
  create avd \
  --name 'TEST_BROWSER_IN_EMULATOR_AVD' \
  --package $ANDROID_PACKAGE \
  --force

# SAY YES TO CREATING A CUSTOM HARDWARE PROFILE
# NOTE: You will need to set some hardware options (e.g. keyboard support) for
# a better experience.
