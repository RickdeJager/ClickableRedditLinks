package ClickableRedditLinks;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HelperClass {

    String regex = "(?i)[^\\w\\[]\\/[ru]\\/[a-z0-9_]*\\b"; //[non-word][/][r or u][/][word][up to word boundary]

    //ToBeReplacedWithStuff is a placeholder for the /r/subreddit or /u/user link, if a user uses 'ToBeReplacedWithStuff' in a chat message, stuff will break, but that's on them tbh.
    String link = " \"},{\"text\":\"[ToBeReplacedWithStuff]\",\"color\":\"gold\",\"bold\":true,\"clickEvent\":{\"action\":\"open_url\",\"value\":\"https://www.reddit.comToBeReplacedWithStuff\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"Open https://www.reddit.comToBeReplacedWithStuff\",\"color\":\"blue\"}]}}},{\"text\":\" ";


    public String ReplaceAllLinks(String input) {

        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(input);

        if (m.find()) {

            String subString = m.group(0).replaceAll("[\\s\"]",""); //Filter out all spaces and " so it doesn't get stuck in a recursive loop
            System.out.println(subString);
            return ReplaceAllLinks(FilterMessage(input, subString));

        }else {
            return input;
        }
    }

    public String FilterMessage(String message, String substring) {

        return message.replace(substring, link.replaceAll("ToBeReplacedWithStuff", substring));
    }

    public boolean ContainsRedditLink(String input) {

        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(input);
        return m.find();
    }

}
