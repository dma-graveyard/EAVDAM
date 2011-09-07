@echo off

echo Deleting old files from directory 
echo dk\frv\eavdam\io\jaxb

pause

echo Generating ftp files from schema ftp.xsd
xjc -p dk.frv.eavdam.io.jaxb.ftp ftp.xsd -d .

echo done.
echo.
echo Remember to add @XmlRootElement annotation to class 
echo   dk.frv.eavdam.io.jaxb.EavdamData
echo.
echo Add also associated import 
echo   import javax.xml.bind.annotation.XmlRootElement;
echo.

pause