#!/bin/bash

result=0

test () {
    arr=($1)
    ./RUN.sh ${arr[*]}
    status=$?
    if [[ $status -ne $2 ]]; then
        echo TESTING FAIL [$1] $status "!=" $2
        ((result+=1))
    else
        echo TESTING OK [$1] $status "==" $2
    fi
    return $status
}

test "" 1
test "-h" 1
test "-u XXX -p XXX" 1

#test "-u jdoe -p XXX" 2
#test "-u jdoe -p sup3rpaZZ" 0

test "-u jdoe -p sup3rpaZZ -r XXX -s XX'" 3
test "-u jdoe -p sup3rpaZZ -r READ -s XXX" 4
test "-u jdoe -p sup3rpaZZ -r READ -s \"a\"" 0
test "-u jdoe -p sup3rpaZZ -r READ -s \"a.b\"" 0

test "-u jdoe -p sup3rpaZZ -r READ -s a -ds XXX -de XXX -v XXX" 5
test "-u jdoe -p sup3rpaZZ -r READ -s a -ds \"2015-05-01\" -de \"2015-05-02\" -v 100" 0

echo
if [[ $result -gt 0 ]]; then
    echo $result tests failed
else
    echo ALL TESTS PASSED
fi
