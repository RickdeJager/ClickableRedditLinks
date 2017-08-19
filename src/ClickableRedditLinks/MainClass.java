package ClickableRedditLinks;


import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.reflect.StructureModifier;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import org.bukkit.plugin.java.JavaPlugin;


public class MainClass extends JavaPlugin {


    //ToBeReplacedWithStuff is a placeholder for the /r/subreddit or /u/user link, if a user uses 'ToBeReplacedWithStuff' in a chat message, stuff will break, but that's on them tbh.
    String link = " \"},{\"text\":\"[ToBeReplacedWithStuff]\",\"color\":\"gold\",\"bold\":true,\"clickEvent\":{\"action\":\"open_url\",\"value\":\"https://www.reddit.comToBeReplacedWithStuff\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"\",\"extra\":[{\"text\":\"Open ToBeReplacedWithStuff\",\"color\":\"blue\"}]}}},{\"text\":\" ";



    @Override
    public void onEnable(){



        ProtocolLibrary.getProtocolManager().addPacketListener(
                new PacketAdapter(this, PacketType.Play.Server.CHAT) {
                    // Note that this is executed asynchronously
                    @Override
                    public void onPacketSending(PacketEvent event) {
                        PacketContainer packet = event.getPacket();
                        StructureModifier<WrappedChatComponent> chatComponents = packet.getChatComponents();
                        WrappedChatComponent msg = chatComponents.read(0);
                        String message = msg.getJson();

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
                        }catch(IllegalArgumentException e){
                            System.out.println("[ClickableRedditLinks] An error occurred while trying to get the subreddit/user");
                            return;
                        }

                        try{
                            msg.setJson(message);
                            chatComponents.write(0,msg);
                        }catch (Exception e){
                            System.out.println("[ClickableRedditLinks] An error occurred while trying to convert back to json chat");
                            return;
                        }

                    }
                });
    }

    @Override
    public void onDisable(){

    }

    public String FindSubString(String message, String s){

        int startIndex = message.indexOf("/"+s+"/");
        int endIndex = message.indexOf(" ", startIndex);
        if ((message.indexOf("\"", startIndex) < endIndex ||endIndex < 0 )&& message.indexOf("\"", startIndex)>0){
            endIndex = message.indexOf("\"", startIndex);
        }

        String substring = message.substring(message.indexOf("/"+s+"/")+3,endIndex);

        return substring;
    }



}
