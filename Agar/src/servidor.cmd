set path=%path%;C:\Program Files\Java\jdk1.7.0_79\bin
ECHO set path=%path%;C:\Program Files\Java\jdk1.8.0_40\bin
rmic Controller.GestorVirus
rmic Controller.GestorPlayer
start rmiregistry 1099
java agar.Server
pause