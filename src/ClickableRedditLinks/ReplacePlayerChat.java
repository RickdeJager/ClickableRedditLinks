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


public class ReplacePlayerChat implements Listener{

    HelperClass hc = new HelperClass();
    Set<Player> recipients;


        @EventHandler
        public void OnPlayerChatEvent (AsyncPlayerChatEvent e){
            String message = e.getMessage();
            message = "[\"\",{\"text\":\""+"<"+e.getPlayer().getDisplayName()+"> "+message+"\"}]"; //convert the string to a single json object.

            if(hc.ContainsRedditLink(message)){
                recipients = e.getRecipients();
                ReplaceChat(message, recipients);
                e.setCancelled(true);

            }


        }

        private void ReplaceChat(String message, Set<Player> recipients){

            message = hc.FilterMessage(message);

            for(Player p : recipients){
                Packet MessagePacket = new PacketPlayOutChat(IChatBaseComponent.ChatSerializer.a(message));
                ((CraftPlayer) p).getHandle().playerConnection.sendPacket(MessagePacket);

            }

        }
}
