#!/bin/bash
echo "\nFetching all cubes ..."
curl --location 'localhost:8080/dimension/' | json_pp

echo "\nfetching cubes completed."
