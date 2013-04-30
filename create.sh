#!/bin/sh
#
# A script for creating a new cookbook entry

if [ -z "$1" -o -z "$2" ]; then
    echo "Usage: $0 artifactId JavaGameClassName"
    exit 255
fi

VERS=1.8-SNAPSHOT

mvn archetype:generate \
    -DarchetypeRepository=local \
    -DarchetypeRepository=$HOME/.m2/repository \
    -DarchetypeGroupId=com.googlecode.playn \
    -DarchetypeArtifactId=playn-archetype \
    -DarchetypeVersion=$VERS \
    -DgroupId=com.googlecode.playn.cookbook \
    -DartifactId=$1 \
    -Dversion=1.0-SNAPSHOT \
    -DJavaGameClassName=$2 \
    -Dpackage=cookbook.$1
