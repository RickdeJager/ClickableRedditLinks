package ClickableRedditLinks;


import net.minecraft.server.v1_12_R1.IChatBaseComponent;
import net.minecraft.server.v1_12_R1.Packet;
import net.minecraft.server.v1_12_R1.PacketPlayOutChat;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.Set;

/**
 * This class is used to replace reddit links in player chat only
 */
public class ReplacePlayerChat implements Listener {

    HelperClass hc = new HelperClass(); //grab a copy of the helper class to access its methods
    Set<Player> recipients;             //This set object is used to save recipients of chat messages

    /**
     *This method captures the chat event and edits the message if it contains a reddit link
     */
    @EventHandler
        public void OnPlayerChatEvent (AsyncPlayerChatEvent e) {

            String message = e.getMessage();
            message = "[\"\",{\"text\":\""+"<"+e.getPlayer().getDisplayName()+"> "+message+"\"}]"; //convert the string to a simple json object.

            if(hc.ContainsRedditLink(message)) {

                recipients = e.getRecipients();
                ReplaceChat(message, recipients);
                e.setCancelled(true);       //We can cancel the event, since ReplaceChat() will handle sending the message

            }
        }

    /**
     * This method manually sends a message to a set of recipients
     *
     * @param message       The message to be send
     * @param recipients    The set of recipients
     */
    private void ReplaceChat(String message, Set<Player> recipients) {

            message = hc.ReplaceAllLinks(message);
            Packet MessagePacket;
            try {
                MessagePacket = new PacketPlayOutChat(IChatBaseComponent.ChatSerializer.a(message));    //Construct a chat packet
            }catch (Exception e) {
                return;
            }

            for(Player p : recipients) {    //Send the packet to all recipients

                ((CraftPlayer) p).getHandle().playerConnection.sendPacket(MessagePacket);
            }

        }
}
