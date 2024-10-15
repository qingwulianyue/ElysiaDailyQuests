package command.subcommands

import org.bukkit.command.CommandSender
//帮助命令
class HelpCommand {
    fun execute(commandSender: CommandSender, string: Array<String>){
        if (string.size == 1) {
            commandSender.sendMessage("/ElysiaDailyQuests reload   -   重载插件")
            commandSender.sendMessage("/ElysiaDailyQuests accept <name>    -   为指定玩家接取每日")
            commandSender.sendMessage("/ElysiaDailyQuests switch <name> <group>   -   为指定玩家切换任务分组")
        }
    }
}