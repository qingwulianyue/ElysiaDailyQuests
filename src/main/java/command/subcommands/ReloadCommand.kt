package command.subcommands

import main.ElysiaDailyQuests
import org.bukkit.command.CommandSender
//重载命令
class ReloadCommand {
    fun execute(commandSender: CommandSender,string: Array<String>){
        if (string.size == 1) {
            ElysiaDailyQuests.getConfigManager().loadConfig()
            ElysiaDailyQuests.getQuestsManager().loadQuestsData()
            commandSender.sendMessage("重载完成")
        }
    }
}