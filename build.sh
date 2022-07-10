#!/bin/bash
set -e

echo "Launching linter"
./gradlew detekt

echo "Launching dependency security check"
./gradlew dependencyCheckAnalyze

echo "Launching dependency updates check"
./gradlew dependencyUpdates

echo "Launching tests"
./gradlew test

./build_image.sh

echo "Build. FINISHED"
