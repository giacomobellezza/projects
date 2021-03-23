#!/bin/bash 

echo "Importazione del DATABASE ...."
mongoimport --db comuni --collection comuni --jsonArray --drop --file $HADOOP_DIR/Comuni-Italia-Project/input/comuni.json

echo "ESPORTAZIONE DELLE DIPENDENZE NECCESSARIE ...."
#Questo comando richiama lo script che permette di esportare correttamente le librerie
sh $HADOOP_DIR/etc/hadoop/hadoop-env.sh

echo "  COMPLETATO"
