#!/bin/bash
echo "Creating cubes ... "
sh ./create-cube.sh
echo
echo "Listing cubes ..."
sh ./get-cube.sh
echo
echo "getting cubes with uuid ..."
sh ./get-cube-by-uuid.sh
echo
echo "Getting cubes by dimensions ..."
sh ./get-cube-by-dimension.sh
echo
