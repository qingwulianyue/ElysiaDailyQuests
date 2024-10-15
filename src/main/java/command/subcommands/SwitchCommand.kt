package command.subcommands

import main.ElysiaDailyQuests
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
//切换委托分组命令
class SwitchCommand {
    fun execute(commandSender: CommandSender, string: Array<String>){
        if (string.size == 3) {
            val name: String = string[1]
            val id: String = string[2]
            if (!searchPlayer(name)) {
                commandSender.sendMessage("该玩家不在线")
                return
            }
            if (!searchGroup(id)) {
                commandSender.sendMessage("该分组不存在")
                return
            }
            val player = Bukkit.getPlayer(name)
            if (! ElysiaDailyQuests.getPlayerDataManager().getPlayerDataHashMap().containsKey(player.uniqueId)){
                ElysiaDailyQuests.getPlayerDataManager().newPlayerData(player.uniqueId) //当该玩家是第一次切换分组时新建一个信息
            }
            ElysiaDailyQuests.getPlayerDataManager().changeGroup(player.uniqueId,id)
            commandSender.sendMessage("完成")
        }
    }
    private fun searchGroup(id: String): Boolean {
        return ElysiaDailyQuests.getQuestsManager().getQuestsDataHashMap().containsKey(id)
    }
    private fun searchPlayer(name: String): Boolean {
        return Bukkit.getPlayer(name) != null
    }
}