package com.bot.service;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageReaction;

import java.util.List;
public class TopPostsOfTheWeek {

    //logic methods
    static public Message returnMostReacted(List<Message> messageList){
        Message topMessage = messageList.get(0);

        for(int i = 0; i < messageList.size(); i++){
            int reactions1 = numberOfReactions(messageList.get(i).getReactions());
            int reactions2 = numberOfReactions(topMessage.getReactions());
            if(reactions1 > reactions2){
                topMessage = messageList.get(i);
            }
        }
        if(numberOfReactions(topMessage.getReactions()) > 0){
            return topMessage;
        }else{
            return null;
        }
    }

    static public Message returnMostUpvoted(List<Message> messageList){
        Message mostUpvoted = messageList.get(0);

        for(int i = 0; i < messageList.size(); i++){
            int reactions1 = numberOfUpvotes(messageList.get(i).getReactions());
            int reactions2 = numberOfUpvotes(mostUpvoted.getReactions());

            if(reactions1 > reactions2){
                mostUpvoted = messageList.get(i);
            }
        }
        if(numberOfUpvotes(mostUpvoted.getReactions()) > 0){
            return mostUpvoted;
        }else{
            return null;
        }
    }

    static public Message returnMostDownvoted(List<Message> messageList){
        Message mostDownvoted = messageList.get(0);

        for(int i = 0; i < messageList.size(); i++){
            int reactions1 = numberOfDownvotes(messageList.get(i).getReactions());
            int reactions2 = numberOfDownvotes(mostDownvoted.getReactions());

            if(reactions1 > reactions2){
                mostDownvoted = messageList.get(i);
            }
        }
        if(numberOfDownvotes(mostDownvoted.getReactions()) > 0){
            return mostDownvoted;
        }else{
            return null;
        }
    }

    //helper methods

    static public int numberOfReactions(List<MessageReaction> reactions){
        int totalReactions = 0;
        for(MessageReaction reaction : reactions){
            totalReactions += reaction.getCount();
        }
        return totalReactions;
    }

    static public int numberOfUpvotes(List<MessageReaction> reactions){
        int totalReactions = 0;
        for(MessageReaction reaction : reactions){
            if(reaction.getReactionEmote().isEmote() && reaction.getReactionEmote().getEmote().getName().equals("UpVote")){
                totalReactions += reaction.getCount();
            }else if(reaction.getReactionEmote().isEmote() && reaction.getReactionEmote().getEmote().getName().equals("DownVote")){
                totalReactions = totalReactions - reaction.getCount();
            }
        }
        return totalReactions;
    }

    static public int numberOfDownvotes(List<MessageReaction> reactions){
        int totalReactions = 0;
        for(MessageReaction reaction : reactions){
            if(reaction.getReactionEmote().isEmote() && reaction.getReactionEmote().getEmote().getName().equals("DownVote")){
                totalReactions += reaction.getCount();
            }else if(reaction.getReactionEmote().isEmote() && reaction.getReactionEmote().getEmote().getName().equals("UpVote")){
                totalReactions = totalReactions - reaction.getCount();
            }
        }
        return totalReactions;
    }
}
