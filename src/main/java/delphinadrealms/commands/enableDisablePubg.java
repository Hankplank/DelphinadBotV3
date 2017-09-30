package delphinadrealms.commands;

import delphinadrealms.handlers.SQLManager;
import net.dv8tion.jda.core.entities.Message;


/**
 * Created by henry27 on 8/15/2017.
 */
public class enableDisablePubg {

    public static void changePubgEnabled(Message message, boolean bool) {
        long serverid = message.getGuild().getIdLong();
        SQLManager sql = new SQLManager();
        if (bool == true) {
            sql.setPUBGEnabled(serverid);
        } else if (bool == false) {
            sql.setPUBGDisabled(serverid);
        }
    }

    public static void changeLeagueEnabled(Message message, boolean bool) {
    long serverid = message.getGuild().getIdLong();
    SQLManager sql = new SQLManager();
        if (bool == true) {
        sql.setLeagueEnabled(serverid);
    } else if (bool == false) {
        sql.setLeagueDisabled(serverid);
    }
}
}
