#Setup on Windows

1. create oracle account

2. install java

https://www.oracle.com/java/technologies/javase-jdk11-downloads.html

3. Add system environment variable:

JAVA_HOME
C:\Program Files\Java\jdk-11.0.10

4. Add java bin to PATH environment variable

C:\Program Files\Java\jdk-11.0.10\bin

5. Create a new directory C:\Gradle

6. Download Gradle binary

https://gradle.org/releases/

7. Copy Gradle content to c:\Gradle

8. Add gradle bin to PATH environment variable

C:\Gradle\bin

9. Install nodejs

Use option to automatically install necessary tools

Press enter to inhird paries in opened termanal

https://nodejs.org/dist/v14.16.0/node-v14.16.0-x64.msi

9. install Microsoft Bot Framework Emulator

Use: anyone who use this comuter option

https://github.com/microsoft/BotFramework-Emulator/releases

10. install git for windows

11. Open Git Bash Here for folder menu

12. Create woring directory

mkdir c:\my\
cd c:\my\

13. Clone repository in terminal

git clone https://github.com/arise-project/checklist_bot.git

14. Open cmd

15. cd c:\my\checklist_bot\core

16. Build core for bot

gradle build

17. cd c:\my\checklist_bot\ypbot

18. Install bot dependancies

npm install

19. Stat bot

npm start

20.  Run Microsoft Bot Emulator

21. Open bot for url

http://locahost:3978/api/messages

22. Click configure ngrok

23. Uncheck Bypass ngrok to local address

24. Try send command to bot

statistics

# checklist_bot

Gradle install: https://yallalabs.com/devops/how-to-install-gradle-ubuntu-18-04-ubuntu-16-04/

https://services.gradle.org/distributions/

source /etc/profile.d/gradle.sh

## Read and save test
gradle run --args="read_text_file 'English for Career Development' /home/eugene/Documents/English_for_Career_Development set_battr NOTE_692302932 attr1 save_storage /home/eugene/Documents/text.json statistics"

##Merge with file changes
    
## Create jar
gradle clean fatJar

gradle run --args="read_text_file 'English for Career Development' /home/eugene/Documents/English_for_Career_Development read_text_file 'English for Career Development' /home/eugene/Documents/my_changes save_storage /home/eugene/Documents/text.json"

cd build/libs/

java -jar -Xmx512M --illegal-access=warn checklist_bot-all-1.0.jar


https://sandbox.evernote.com

gradle run --args="connect_everynote list_enotebooks list_all_enotes list_enotes_for_notebook eat"
gradle run --args="connect_everynote read_enote plov statistics save_storage 1.xml"

