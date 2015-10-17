#!/bin/bash -v
rm -rf "out"
mkdir -p "out/classes"
find . -name "*.java" | xargs javac -cp "lib/*" -d out/classes -sourcepath src -verbose

cp src/log4j2.xml out/classes/

mkdir -p "out/lib"
cp lib/* out/lib/

jar -cfe out/aaa.jar com.blzr.Main -C out/classes/ .
