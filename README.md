# checklist_bot

Gradle install: https://yallalabs.com/devops/how-to-install-gradle-ubuntu-18-04-ubuntu-16-04/

https://services.gradle.org/distributions/

source /etc/profile.d/gradle.sh

## Read and save test
gradle run --args="read_text_file 'English for Career Development' /home/eugene/Documents/English_for_Career_Development set_battr NOTE_692302932 attr1 save_storage /home/eugene/Documents/text.json statistics"

##Merge with file changes
    
## Create jar
gradle clean fatJar

gradle run --args="read_text_file 'English for Career Development' /home/eugene/Documents/English_for_Career_Development save_storage /home/eugene/Documents/text.json read_text_file 'English for Career Development' /home/eugene/Documents/my_changes"

cd build/libs/

java -jar -Xmx512M --illegal-access=warn checklist_bot-all-1.0.jar

WARNING: Illegal reflective access by com.google.inject.internal.cglib.core.$ReflectUtils$1 (file:/home/eugene/Projects/checklist_bot/core/build/libs/checklist_bot-all-1.0.jar) to method java.lang.ClassLoader.defineClass(java.lang.String,byte[],int,int,java.security.ProtectionDomain)
