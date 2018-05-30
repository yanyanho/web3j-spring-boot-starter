 #! /usr/bin/env bash

set -e
#shopt -s globstar

#if [ $# -ne 1 ]; then
#  echo "usage: $0 <api|bds>"
#  exit 1
#fi

cd $(dirname $0)/$1
java -Dfile.encoding=UTF-8 -jar ../moco.jar http -p 12306 -c **/*.json