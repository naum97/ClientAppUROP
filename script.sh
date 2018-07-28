#!/bin/sh

#Input arguments
noOfClients=$1
qS=$2
iter=$3
num_thread=$4
experiment_id=$5
agg_data=$6
FILES=/home/naummk/Desktop/$experiment_id/*

echo -ne " Testing passwordless connection to the server and client machine(s)... "
success=$( ssh -o BatchMode=yes kea01.doc.res.ic.ac.uk echo ok 2>&1 )
if [ $success != "ok" ]
then
    echo "Connection not successful on server machine. Exiting ..."
    exit -1
fi

success=$( ssh -o BatchMode=yes kea02.doc.res.ic.ac.uk echo ok 2>&1 )
 if [ $success != "ok" ]
 then
     echo "Connection not successful on client machine. Exiting ..."
     exit -1
 fi
echo "OK"

echo "	Starting the server"

sleep 1

echo " Starting $noOfClients clients ..."
clientIds=`seq 2 $(($noOfClients+1))`
pids=""
query="_query"
for clientId in $clientIds
do
    echo "Start client $clientId"
	ssh kea0$clientId.doc.res.ic.ac.uk "cd ClientAppUROP/src; java Main $qS $iter $num_thread > /home/naummk/client$clientId$query$qS" & pids="$pids $!"
done
echo -ne " Waiting for clients to finish ... "
for f in $pids
do
    wait $f
done
echo "OK"

echo " Copying log files from client machines... "
mkdir -p $experiment_id
mkdir -p $agg_data
for clientId in $clientIds
do
	scp kea0$clientId.doc.res.ic.ac.uk:/home/naummk/client$clientId$query$qS /home/naummk/Desktop/$experiment_id
    sort -k5 -n /home/naummk/Desktop/$experiment_id/client$clientId$query$qS | ./aggregate.sh > /home/naummk/Desktop/$agg_data/agg_client$clientId$query$qS
done
echo -ne "  Cleaning up files on client machines... "
for clientId in $clientIds
do
        ssh kea0$clientId.doc.res.ic.ac.uk "cd; rm client$clientId$query$qS"
done
echo "OK"
