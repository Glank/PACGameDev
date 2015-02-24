#!/usr/bin/env bash
make examples.jar || exit
java -cp pac.jar:examples.jar examples.$1
