PIRCBOT= ./pircbot.jar

botaki:
	javac -classpath ${PIRCBOT}:. IRCBot/*.java
	
run: botaki
	java -classpath ${PIRCBOT}:. IRCBot.BotGui
 	
jar: botaki
	jar cvfe ./IRCBot.jar IRCBot.BotGui IRCBot/*.class IRCBot:/i.png
	
run-jar: IRCBot.jar pircbot.jar
	java -Xbootclasspath/a:${PIRCBOT}:. -jar IRCBot.jar
