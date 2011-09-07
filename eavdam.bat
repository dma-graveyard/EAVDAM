@echo off

set OPENMAP_HOME=.

set CLASSPATH=%OPENMAP_HOME%;%OPENMAP_HOME%\build\classes;%OPENMAP_HOME%\share;%OPENMAP_HOME%\lib\milStd2525_png.jar;%OPENMAP_HOME%\lib\openmap.jar;%OPENMAP_HOME%\lib\omsvg.jar;%OPENMAP_HOME%\lib\omj3d.jar;%OPENMAP_HOME%\lib\omcorba_vb.jar;%OPENMAP_HOME%\lib\mail.jar;%OPENMAP_HOME%\lib\commons-net-3.0.1.jar;

java -Xmx64m -Ddebug.showprogress com.bbn.openmap.app.OpenMap
