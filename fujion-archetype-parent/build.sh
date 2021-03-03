#!/bin/bash

# Script to build a project from one of the Fujion archetypes.

set -e

case "$1" in

"")
  echo You must specify an archetype
  echo For example: ./build.sh component
  echo Recognized archetypes are:
  echo angular, component, fsp, react, webapp
  exit 1
;;

"angular" | "component" | "fsp" | "react" | "webapp")
  mvn archetype:generate -DarchetypeGroupId=org.fujion -DarchetypeArtifactId=fujion-archetype-$1 -DarchetypeVersion=LATEST
  exit 0
;;

*)
  echo Unrecognized archetype: $1
  exit 1
;;

esac

