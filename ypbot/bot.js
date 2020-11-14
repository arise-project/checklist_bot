// Copyright (c) Artificial Renaissance itself. All rights reserved.
// Licensed under the MIT License.

const { ActivityHandler, MessageFactory } = require('botbuilder');
const { spawn } = require('child_process');
const { count } = require('console');

class ChecklistBot extends ActivityHandler {
    constructor() {
        super();
        // See https://aka.ms/about-bot-activity-message to learn more about the message and other activity types.
        this.onMessage(async (context, next) => {
            const replyText = `Echo: ${context.activity.text}`;

            var prc = spawn('java', ['-jar', '-Xmx512M', '-Dfile.encoding=utf8', '../core/build/libs/checklist_bot-all-1.0.jar']);

            //noinspection JSUnresolvedFunction
            var strs = [];
            prc.stdout.setEncoding('utf8');
            prc.stdout.on('data', function (data) {
                var str = data.toString()
                
                var lines = str.split(/(\r?\n)/g);
                console.log(lines.join(""));
                var count = 0;
                var buff = '';
                for(var i =0; i < lines.length;i++)
                {
                    buff += lines[i] + '\n';

                    if(count++ > 5 || i == lines.length - 1)
                    {
                        strs.push(buff);
                        buff = '';
                        count = 0;
                    }
                }
                str = lines.join("\n");
            });

            prc.on('close', function (code) {
                if(code != 0)
                {
                    console.log('process exit code ' + code);
                }
            });

            await new Promise(resolve => setTimeout(() => resolve(                
            ), 1000));

            for(var i =0; i < strs.length;i++)
            {
                await context.sendActivity(MessageFactory.text(strs[i], strs[i]));
            }
            

            // By calling next() you ensure that the next BotHandler is run.
            await next();

            //todo:
            //How to send a message to user if the bot is idle for 5 mins in botframework v4
            //https://stackoverflow.com/questions/61315710/how-to-send-a-message-to-user-if-the-bot-is-idle-for-5-mins-in-botframework-v4
        });

        this.onMembersAdded(async (context, next) => {
            const membersAdded = context.activity.membersAdded;
            const welcomeText = 'Hello and welcome!';
            for (let cnt = 0; cnt < membersAdded.length; ++cnt) {
                if (membersAdded[cnt].id !== context.activity.recipient.id) {
                    await context.sendActivity(MessageFactory.text(welcomeText, welcomeText));
                }
            }
            // By calling next() you ensure that the next BotHandler is run.
            await next();
        });
    }
}

module.exports.ChecklistBot = ChecklistBot;
