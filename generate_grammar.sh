#!/bin/sh

${JAVA:-java} -cp "$ANTLR_PATH" org.antlr.v4.Tool src/TestCase.g4 -o src/parser -package parser -visitor -no-listener
