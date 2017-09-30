package delphinadrealms.handlers;

import pro.lukasgorny.dto.Player;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLManager {

    public SQLManager() {
        connect();
    }

    private Connection connect = null;

    public void connect() {
        try {
            String url = "jdbc:SQLite:/root/SQLBot/discordbot.db";
            connect = DriverManager.getConnection(url);
            //System.out.println("Connection to SQLite has been established. At " + url);

            // CREATES A NEW TABLE IF ONE DOESNT EXIST, IF ONE ALREADY EXISTS DOES NOTHING
            String createTable = "CREATE TABLE IF NOT EXISTS servers (serverid long(18) unique," +
                    " lobbyChannelID long(18), sendJoinMessage boolean, sendLeaveMessage boolean, enablePUBG boolean, enableLeague boolean);";
            Statement createIfDoesntExist = connect.createStatement();
            createIfDoesntExist.executeUpdate(createTable);

        } catch (SQLException e){
            System.out.println(e.getMessage());
        }

    }
    //                            SERVER ID          sendJoinMessageChannelID sendLeaveMessage.. sendJoinMessage SendLeaveMessage enablePUBG enableLeague
    //INSERT INTO SERVERS VALUES (334189774741045249,334190910852169729,"true","true","true","true");


    public void addServer(long serverID,long lobbyChannelID, boolean sendJoinMessage, boolean sendLeaveMessage, boolean enablePUBG, boolean enableLeague) {

        if (!connect.equals(null)) {
            try {
                // CREATES A NEW TABLE IF ONE DOESNT EXIST, IF ONE ALREADY EXISTS DOES NOTHING
                String createTable = "CREATE TABLE IF NOT EXISTS servers (serverid long(18) unique," +
                        " lobbyChannelID long(18), sendJoinMessage boolean, sendLeaveMessage boolean, enablePUBG boolean, enableLeague boolean);";
                Statement createTableStatement = connect.createStatement();
                createTableStatement.executeUpdate(createTable);
                /*String command = "INSERT INTO SERVERS VALUES (" + serverID + "," + lobbyChannelID + ", \"" +  sendJoinMessage + "\" , \"" + sendLeaveMessage + "\" , \"" + enablePUBG + "\" , \""
                        + enableLeague + "\");"; */
                System.out.println("INSERT INTO SERVERS VALUES (" + serverID + "," + lobbyChannelID + ", \"" +  sendJoinMessage + "\" , \"" + sendLeaveMessage + "\" , \"" + enablePUBG + "\" , \""
                        + enableLeague + "\");");
                String command = "INSERT INTO servers VALUES (334189774741045249, 334190910852169729, \"true\" , \"true\" , \"true\" , \"true\" );";
                Statement statement = connect.createStatement();
                try {
                   statement.executeUpdate(command);
                } catch (SQLException e) {

                }

                System.out.println("New server has been added with the serverID of: " + serverID + ".");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void removeServer(long serverID) {
            try {
                if (!connect.isClosed()) {
                    System.out.println(serverID);
                    String command = "DELETE FROM servers WHERE serverid=" + Long.toString(serverID) + ";";
                    Statement statement = connect.createStatement();
                    try {
                        ResultSet rs = statement.executeQuery(command);
                    } catch (SQLException e) {

                    }

                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

    }

    public long getServerLobbyID(long serverID) {

            try {
                if (connect.isClosed()) {

                    String command = "SELECT * FROM SERVERS WHERE serverid=" + serverID + ";";
                    Statement statement = connect.createStatement();
                    ResultSet rs = statement.executeQuery(command);
                    return rs.getLong("lobbyChannelID");
                }
            } catch (SQLException e) {
                return 0;
            }

        return 0;
    }


    public boolean getLeagueEnabled(long serverID) {
        try {
            if (!connect.isClosed()) {
                String command = "SELECT * FROM SERVERS WHERE serverid=" + serverID + ";";
                Statement statement = connect.createStatement();
                ResultSet rs = statement.executeQuery(command);
                return rs.getBoolean("enableLeague");
            } else {
                System.out.println("Something was wrong. Just returned false. getLeagueEnabled");
                return false;
            }
        } catch (SQLException e) {
           e.printStackTrace();
        }
        return false;
    }

    public boolean getPUBGEnabled(long serverID) {
        try {
            if (!connect.isClosed()) {
                String command = "SELECT * FROM SERVERS WHERE serverid=" + serverID + ";";
                Statement statement = connect.createStatement();
                ResultSet rs = statement.executeQuery(command);
                return rs.getBoolean("enablePUBG");
            } else {
                System.out.println("Something was wrong. Just returned false. getLeagueEnabled");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void setLeagueEnabled(long serverid) {
        try {
            if (!connect.isClosed()) {
                String command = "UPDATE servers SET enableLeague = \"true\" WHERE serverid=" + serverid + ";";
                Statement statement = connect.createStatement();
                statement.executeQuery(command);

            }
            } catch (SQLException e) {
                e.printStackTrace();
            }

    }

    public void setLeagueDisabled(long serverid) {
        try {
            if (!connect.isClosed()) {
                String command = "UPDATE servers SET enableLeague = \"false\" WHERE serverid=" + serverid + ";";
                Statement statement = connect.createStatement();
                statement.executeQuery(command);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void setPUBGEnabled(long serverid) {
        try {
            if (!connect.isClosed()) {
                String command = "UPDATE servers SET enablePUBG = \"true\" WHERE serverid=" + serverid + ";";
                Statement statement = connect.createStatement();
                statement.executeUpdate(command);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void setPUBGDisabled(long serverid) {
        try {
            if (!connect.isClosed()) {
                String command = "UPDATE servers SET enablePUBG = \"false\" WHERE serverid=" + serverid + ";";
                Statement statement = connect.createStatement();
                statement.executeUpdate(command);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public boolean isConnectionNull() {
        try {
            if (connect.isClosed()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return true;
        }

    }

    public boolean getServerJoinMessageEnabled(long serverID) {
        if (!connect.equals(null)) {
            String command = "SELECT * FROM servers WHERE serverid=" + serverID + ";";
            try {
                Statement statement = connect.createStatement();
                ResultSet rs = statement.executeQuery(command);
                if (rs.getString("sendJoinMessage").equalsIgnoreCase("true")) {
                    return true;
                } else if (rs.getString("sendJoinMessage").equalsIgnoreCase("false")) {
                    return false;
                } else {
                    return false;
                }
            } catch (SQLException e) {
                System.out.println("Error in SQLManager.java in getServerJoinMessageEnabled.\n" + e.getMessage());
            }
        }
return true;
    }

    public boolean changeLobbyID(long serverID, String channelID) {
        long channelIdLong = Long.parseLong(channelID);
        if (!connect.equals(null) && channelIdLong > 0) {
            String command = "UPDATE servers SET lobbyChannelID = \"" + channelIdLong + "\" WHERE serverID = " + serverID + ";";
            try {
                Statement statement = connect.createStatement();
                statement.executeQuery(command);
            } catch (SQLException e) {
                if (e.getMessage().contains("does not return ResultSet")) {
                    //do nothing
                } else {
                    System.out.println(e.getMessage());
                }
            }


            // UPDATE servers SET lobbychannelID = "334918139580252172" WHERE serverid = 334189774741045249;
            return true;
        } else {
            return false;
        }
    }

    public void close() {
        try {
            connect.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
