#!/bin/bash

protoc -I=./resources/ptoto --java_out=./java ./resources/ptoto/addressbook.proto