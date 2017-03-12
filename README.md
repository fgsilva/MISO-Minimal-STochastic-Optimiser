# MISO - MInimal Stochastic Optimiser for 2048

## What is it?
--------------------

MISO is an intelligent player for the 2048 game. MISO is based on a so-called minimal stochastic optimisation process inspired by expectimax search. MISO attempts to maximise game score expectations by stochastically simulating which move (left, right, up, down) may result in a larger payoff over the long-run. MISO is currently configured to estimate the next best move by generating and evaluating 1,000 possible sequences (sets of consecutive moves). Each sequence is composed of 50 moves, and is evaluated over 10 randomised trials with varying conditions. MISO typically reaches the 2048 tile and the 4096 tile.

## Getting started
--------------------

The application is provided as a self-contained JAR called “fernando_miso_2048-1.0.jar” located in the "target" folder. The source code is provided as a Maven project. There are no dependencies.

## Building from source
--------------------

Code can be compiled and packaged into a new JAR, as indicated below. The JAR will be placed in the "target" folder, unless the user configures Maven otherwise.

> mvn clean package

## Configuration and execution
--------------------

To launch the game, the following command should be executed:

>  java -jar fernando_miso_2048-1.0.jar

A given game can be started or paused via the GUI. If the game is paused, the human user can play the game. Once the game is restarted, MISO will automatically adjust to the state of the game. Once a game has ended, a new game can be started.

## Built with
--------------------

     - IntelliJ IDEA, the Java IDE used 
     https://www.jetbrains.com/idea/ 
     
     - Maven, for dependency management
     https://maven.apache.org/ 
     
     - Maven Compiler plugin, to compile the sources
     https://maven.apache.org/plugins/maven-compiler-plugin/ 
     
     - Maven Shade plugin, to build the JAR
     https://maven.apache.org/plugins/maven-shade-plugin/ 
