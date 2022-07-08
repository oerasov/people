#!/bin/bash
set -e

echo "Build image"

docker build -t "${IMAGE_NAME:="people_test"}:${IMAGE_TAG:="latest"}" .
