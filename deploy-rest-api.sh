#!/bin/bash
mvn clean package
cd target
cp appUtils.jar ../dist/