#!/bin/bash
# add -x for debug output
# to suppress log add > /dev/null

VERSION=4.0

java -jar target/aaa-$VERSION.jar $@
