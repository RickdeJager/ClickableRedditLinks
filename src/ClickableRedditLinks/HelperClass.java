package ClickableRedditLinks;


import org.bukkit.Bukkit;

public class HelperClass {


    //ToBeReplacedWithStuff is a placeholder for the /r/subreddit or /u/user link, if a user uses 'ToBeReplacedWithStuff' in a chat message, stuff will break, but that's on them tbh.
    String link = " \"},{\"text\":\"[ToBeReplacedWithStuff]\",\"color\":\"gold\",\"bold\":true,\"clickEvent\":{\"action\":\"open_url\",\"value\":\"https://www.reddit.comToBeReplacedWithStuff\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"Open ToBeReplacedWithStuff\",\"color\":\"blue\"}]}}},{\"text\":\" ";


    public String FindSubString(String message, String s){

        int startIndex = message.indexOf("/"+s+"/");
        int endIndex = message.indexOf(" ", startIndex);
        if ((message.indexOf("\"", startIndex) <= endIndex ||endIndex < 0 )&& message.indexOf("\"", startIndex)>0) {
            if (message.indexOf("\"", startIndex) > 0) {
                endIndex = message.indexOf("\"", startIndex);
            }
        }
        if(endIndex < 0)  endIndex = message.length();
     //   Bukkit.broadcastMessage("Start:"+startIndex +" || End:"+endIndex); //Useful line for debugging

        String substring = message.substring(message.indexOf("/"+s+"/")+3,endIndex);

        return substring;
    }

    public String FilterMessage(String message, Boolean UseJson){

        if (!UseJson) link = "https:reddit.comToBeReplacedWithStuff";

        try {
            if (message.contains("/r/") && !message.contains("reddit.com/r/")) {
                String subreddit = FindSubString(message, "r");
                message = message.replace("/r/" + subreddit, link);
                message = message.replaceAll("ToBeReplacedWithStuff", "/r/" + subreddit);
            }
            if (message.contains("/u/") && !message.contains("reddit.com/u/")) {
                String user = FindSubString(message, "u");
                message = message.replace("/u/" + user, link);
                message = message.replaceAll("ToBeReplacedWithStuff", "/u/" + user);
            }

            return message;

        } catch (IllegalArgumentException e) {
            System.out.println("[ClickableRedditLinks] An error occurred while trying to get the subreddit/user.");
            return null;
        }

    }

    public boolean ContainsRedditLink(String message){
        if ((message.contains("/r/") && !message.contains("reddit.com/r/"))||message.contains("/u/") && !message.contains("reddit.com/u/")) {
            return true;
        }
        return false;
    }



}
