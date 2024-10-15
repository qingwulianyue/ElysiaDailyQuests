package listener

import main.ElysiaDailyQuests
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.scheduler.BukkitRunnable



class ElysiaDailyQuestsListener : Listener {
    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent){
        val player = event.player
        if (player.hasPermission("ElysiaDailyQuests.accept")){
            if (!ElysiaDailyQuests.getPlayerDataManager().getPlayerDataHashMap().containsKey(player.uniqueId))
                ElysiaDailyQuests.getPlayerDataManager().newPlayerData(player.uniqueId)
            object : BukkitRunnable() {
                override fun run() {
                    ElysiaDailyQuests.getPlayerDataManager().newTask(player.uniqueId)
                }
            }.runTaskLater(ElysiaDailyQuests.getInstance(), 20L)
        }
    }
}