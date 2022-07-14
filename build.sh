#!/bin/bash
set -e

./gradlew detekt dependencyCheckAnalyze dependencyUpdates test

./build_image.sh

echo "Build. FINISHED"
