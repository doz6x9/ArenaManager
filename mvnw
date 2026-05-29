#!/bin/sh
# Lightweight Maven wrapper for Unix-like shells.

set -e

BASE_DIR=$(CDPATH= cd -- "$(dirname -- "$0")" && pwd)
WRAPPER_DIR="$BASE_DIR/.mvn/wrapper"
PROPERTIES="$WRAPPER_DIR/maven-wrapper.properties"

if [ ! -f "$PROPERTIES" ]; then
  echo "Missing Maven wrapper properties: $PROPERTIES" >&2
  exit 1
fi

MAVEN_VERSION=$(sed -n 's/^mavenVersion=//p' "$PROPERTIES" | tail -n 1)
DISTRIBUTION_URL=$(sed -n 's/^distributionUrl=//p' "$PROPERTIES" | tail -n 1)

if [ -z "$MAVEN_VERSION" ] || [ -z "$DISTRIBUTION_URL" ]; then
  echo "mavenVersion and distributionUrl are required in $PROPERTIES" >&2
  exit 1
fi

MAVEN_HOME="$WRAPPER_DIR/dists/apache-maven-$MAVEN_VERSION"
ZIP_FILE="$WRAPPER_DIR/apache-maven-$MAVEN_VERSION-bin.zip"

if [ ! -x "$MAVEN_HOME/bin/mvn" ]; then
  mkdir -p "$WRAPPER_DIR/dists"
  echo "Downloading Apache Maven $MAVEN_VERSION..."
  if command -v curl >/dev/null 2>&1; then
    curl -fsSL "$DISTRIBUTION_URL" -o "$ZIP_FILE"
  elif command -v wget >/dev/null 2>&1; then
    wget -q "$DISTRIBUTION_URL" -O "$ZIP_FILE"
  else
    echo "curl or wget is required to download Maven." >&2
    exit 1
  fi

  if command -v unzip >/dev/null 2>&1; then
    unzip -q "$ZIP_FILE" -d "$WRAPPER_DIR/dists"
  elif command -v jar >/dev/null 2>&1; then
    (cd "$WRAPPER_DIR/dists" && jar xf "$ZIP_FILE")
  else
    echo "unzip or jar is required to extract Maven." >&2
    exit 1
  fi
  rm -f "$ZIP_FILE"
fi

exec "$MAVEN_HOME/bin/mvn" "$@"
