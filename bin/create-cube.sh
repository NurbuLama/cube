#!/bin/bash

echo "creating cube with 2,2,16"
curl --location 'localhost:8080/dimension' \
--header 'Content-Type: application/json' \
--data '{
    "length": 2,
    "breadth": 2,
    "height": 16
}' | json_pp
echo "creating cube with 2,2,2"
curl --location 'localhost:8080/dimension' \
--header 'Content-Type: application/json' \
--data '{
    "length": 2,
    "breadth": 2,
    "height": 2
}' | json_pp

echo "creating cube with 2,2,10"

curl --location 'localhost:8080/dimension' \
--header 'Content-Type: application/json' \
--data '{
    "length": 2,
    "breadth": 2,
    "height": 10
}' | json_pp

curl --location 'localhost:8080/dimension' \
--header 'Content-Type: application/json' \
--data '{
    "length": 4,
    "breadth": 4,
    "height": 4
}' | json_pp

curl --location 'localhost:8080/dimension' \
--header 'Content-Type: application/json' \
--data '{
    "length": 1,
    "breadth": 2,
    "height": 3
}' | json_pp