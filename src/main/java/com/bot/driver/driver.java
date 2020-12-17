package com.bot.driver;
/*
Feature list:

- Top scoring posts of the week
 */
import com.bot.entities.BirthdayUser;
import com.bot.features.Birthdays;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.*;
import com.bot.features.Listener;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.security.auth.login.LoginException;
import java.text.ParseException;

public class driver extends ListenerAdapter {

    static JDA jda;

    public static void main(String[] args) throws LoginException, ParseException {
        System.out.println("hello world");

        jda = JDABuilder.createDefault("Mjg0NjIwNDc4OTczMzQ1ODAy.WK__eg.ibdUuq1f2AESEslkf75kx6x_A3I")
                .setActivity(Activity.watching("my elo drop"))
                .addEventListeners(new Listener())
                .build();

        Birthdays birthdays = new Birthdays(jda);
        BirthdayUser user = birthdays.checkBirthdays();
        if (user != null) {
            jda.getTextChannelById("158082957645578240").sendMessage("Happy birthday " + user.getName() + "!").queue();
        }
    }
}