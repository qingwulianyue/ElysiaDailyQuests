package command

import command.subcommands.HelpCommand
import command.subcommands.AcceptCommand
import command.subcommands.ReloadCommand
import command.subcommands.SwitchCommand
import main.ElysiaDailyQuests
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
//命令执行
class CommandManager : CommandExecutor {
    override fun onCommand(commandSender: CommandSender?, command: Command?, s: String?, strings: Array<String>?): Boolean {
        if (commandSender != null) {
            if (commandSender.hasPermission("ElysiaDailyQuests.admin")){
                if (strings != null) {
                    if (strings.isEmpty()) {
                        commandSender.sendMessage(
                            ElysiaDailyQuests.getConfigManager().getConfigData().prefix
                                    + "请输入/ElysiaDailyQuests help 以获取指令列表"
                        )
                        return true
                    }
                }
                when (strings?.get(0)?.lowercase()) {
                    "reload" -> ReloadCommand().execute(commandSender, strings)
                    "help" -> HelpCommand().execute(commandSender, strings)
                    "accept" -> AcceptCommand().execute(commandSender, strings)
                    "switch" -> SwitchCommand().execute(commandSender, strings)
                }
            }
        }
        return false
    }
}