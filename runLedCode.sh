#!/bin/bash
# This is the command that has to be used to run the jar file, unless it is uploaded directly through the WPILib
# Raspberry Pi configuration console, in which case the WPILib libraries' location is automatically specified.
java -Djava.library.path=/usr/local/frc/lib -jar /home/pi/leds/LedCode-all.jar
