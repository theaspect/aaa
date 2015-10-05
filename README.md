Reference implementation for AAA project

0 `java -cp "../../../lib/*:." com.blzr.Main || echo $?`
0 `java -cp "../../../lib/*:." com.blzr.Main -h || echo $?`

1 `java -cp "../../../lib/*:." com.blzr.Main -u "XXX" -p "XXX" || echo $?`
2 `java -cp "../../../lib/*:." com.blzr.Main -u "jdoe" -p "XXX" || echo $?`
0 `java -cp "../../../lib/*:." com.blzr.Main -u "jdoe" -p "sup3rpaZZ" || echo $?`

3 `java -cp "../../../lib/*:." com.blzr.Main -u "jdoe" -p "sup3rpaZZ" -r "XXX" -s "XXX" || echo $?`
4 `java -cp "../../../lib/*:." com.blzr.Main -u "jdoe" -p "sup3rpaZZ" -r "READ" -s "XXX" || echo $?`
0 `java -cp "../../../lib/*:." com.blzr.Main -u "jdoe" -p "sup3rpaZZ" -r "READ" -s "a" || echo $?`

5 `java -cp "../../../lib/*:." com.blzr.Main -u "jdoe" -p "sup3rpaZZ" -r "READ" -s "a" -ds "XXX" -de "XXX" -v "XXX" || echo $?`
0 `java -cp "../../../lib/*:." com.blzr.Main -u "jdoe" -p "sup3rpaZZ" -r "READ" -s "a" -ds "2015-05-01" -de "2015-05-02" -v 100 || echo $?`
