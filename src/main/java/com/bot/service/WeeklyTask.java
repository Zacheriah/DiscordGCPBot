package com.bot.service;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

import static com.bot.service.TopPostsOfTheWeek.*;

public class WeeklyTask extends TimerTask {

    MessageChannel channel;

    public WeeklyTask(MessageChannel channel){
        this.channel = channel;
    }

    @Override
    public void run() {
        long DAY_IN_MS = 1000 * 60 * 60 * 24;
        Date date = new Date(System.currentTimeMillis() - (7*DAY_IN_MS));

        System.out.println("Weekly Timer task started");
        try {
            Message message = channel.retrieveMessageById(channel.getLatestMessageId()).submit().get();
            OffsetDateTime offsetDateTime = date.toInstant().atOffset(message.getTimeCreated().getOffset());
            MessageChannel channel = message.getChannel();
            List<Message> messageList = new ArrayList<>();

            for(Message currentMessage : channel.getIterableHistory()){
                if(currentMessage.getTimeCreated().isAfter(offsetDateTime)){
                    messageList.add(currentMessage);
                }else if (messageList.size() > 1500){
                    break;
                }else{
                    break;
                }
            }

            Message topMessage = returnMostUpvoted(messageList);
            Message worstMessage = returnMostDownvoted(messageList);
            Message controversialMessage = returnMostReacted(messageList);

            System.out.println("Weekly Timer task ended");

            String messageToSend = "";
            if(topMessage != null){
                messageToSend = messageToSend + "The top message of the past month is: " + topMessage.getJumpUrl() + "\n";
            }
            if(controversialMessage != null){
                messageToSend = messageToSend + "The most controversial message of the past month is: " + controversialMessage.getJumpUrl() + "\n";
            }
            if(worstMessage != null){
                messageToSend = messageToSend + "The worst message of the past month is: " + worstMessage.getJumpUrl();
            }

            channel.sendMessage(messageToSend).queue();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


    }
}
