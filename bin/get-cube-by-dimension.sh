#!/bin/bash
echo "\nchecking cube with dimension 2,2,1"
curl --location 'localhost:8080/dimension/2-2-1' | json_pp

echo "\nchecking cube with dimension 2,2,10"
curl --location 'localhost:8080/dimension/2-2-10'| json_pp