#!/bin/sh
javac -d ./compiled -classpath ./lib/JSX0.8.20.jar:./lib/gnu-regexp-1.1.3.jar:./lib/bsh-1.2b5.jar:. org/javabot/engine/*.java org/javabot/config/*.java org/javabot/engine/*.java org/javabot/security/*.java org/javabot/message/*.java org/javabot/channel/*.java org/javabot/task/*.java org/javabot/user/*.java org/javabot/util/*.java org/javabot/script/*.java
