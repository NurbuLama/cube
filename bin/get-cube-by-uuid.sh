#!/bin/bash
echo"\n fetching cube with uuid 1"
curl --location 'localhost:8080/dimension/uuid/1' | json_pp

echo"\n fetching cube with uuid 3"
curl --location 'localhost:8080/dimension/uuid/3' | json_pp