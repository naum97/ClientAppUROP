#!/bin/sh
sequence=`seq 1 22`
for num in $sequence
do
    ./proba.sh 1 $num 10 1 queries aggs
done
echo "OK"
