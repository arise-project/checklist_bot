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

