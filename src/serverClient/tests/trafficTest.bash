#!/bin/bash

# Fungerar nog bara i Eclipse

for N in {1..50}
do
    java bashTestClient &
done
wait
