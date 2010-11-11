#!/bin/bash
CURRENTDIR=`pwd`
H2DIR=~/Downloads/h2/bin
cd $H2DIR
java -cp h2*.jar org.h2.tools.Server -baseDir ${CURRENTDIR}
