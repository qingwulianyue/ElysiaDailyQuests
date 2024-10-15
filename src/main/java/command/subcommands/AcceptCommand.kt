package command.subcommands

import main.ElysiaDailyQuests
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender

//接取每日委托栏命令
class AcceptCommand {
    fun execute(commandSender: CommandSender, string: Array<String>){
        if (string.size == 2) {
            val name: String = string[1]
            if (!searchPlayer(name)) {
                commandSender.sendMessage("该玩家不在线")
                return
            }
            val player = Bukkit.getPlayer(name)
            if (!player.hasPermission("ElysiaDailyQuests.accept"))
                player.addAttachment(ElysiaDailyQuests.getInstance(),"ElysiaDailyQuests.accept",true)
            ElysiaDailyQuests.getPlayerDataManager().newPlayerData(player.uniqueId)
            ElysiaDailyQuests.getPlayerDataManager().newTask(player.uniqueId)
        }
    }
    private fun searchPlayer(name: String): Boolean {
        return Bukkit.getPlayer(name) != null
    }

}