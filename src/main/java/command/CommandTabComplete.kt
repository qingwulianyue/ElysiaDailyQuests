package command

import main.ElysiaDailyQuests
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
//命令补全
class CommandTabComplete : TabCompleter{
    override fun onTabComplete(commandSender: CommandSender?, command: Command?, s: String?, strings: Array<String>?): MutableList<String> {
        val tabMessages: MutableList<String> = mutableListOf()
        if (strings!!.size == 1) {
            tabMessages.add("reload")
            tabMessages.add("help")
            tabMessages.add("accept")
            tabMessages.add("switch")
        }
        if (strings.size == 2) {
            if (strings[0].equals("accept", ignoreCase = true) || strings[0].equals("switch", ignoreCase = true)) {
                for (player in Bukkit.getOnlinePlayers()) {
                    tabMessages.add(player.name)
                }
            }
        }
        if (strings.size == 3) {
            if (strings[0].equals("switch", ignoreCase = true)) {
                val quests = ElysiaDailyQuests.getQuestsManager().getQuestsDataHashMap().keys
                quests.forEach { group ->
                    tabMessages.add(group)
                }
            }
        }
        return tabMessages
    }
}