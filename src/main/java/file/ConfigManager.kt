package file

import main.ElysiaDailyQuests
import java.time.LocalTime
import java.time.format.DateTimeFormatter
//config管理类
class ConfigManager(private val instance : ElysiaDailyQuests) {
    private lateinit var configData : ConfigData
    fun getConfigData() = this.configData
    //缓存config数据
    fun loadConfig() {
        instance.reloadConfig()
        val config = instance.config
        configData = ConfigData(
            config.getBoolean("Debug"),
            config.getString("Prefix"),
            config.getString("Default"),
            config.getInt("number"),
            LocalTime.parse(config.getString("RefreshTime"),DateTimeFormatter.ofPattern("HH:mm")),
            hashMapOf(
                "OnOpen" to config.getString("Messages.OnOpen"),
                "OnAcceptQuests" to config.getString("Messages.OnAcceptQuests"),
                "OnAcceptFailure" to config.getString("Messages.OnAcceptFailure"),
                "OnFinish" to config.getString("Messages.OnFinish")
            )
        )
        logConfigIfDebug()
    }
    //当debug时输出信息
    private fun logConfigIfDebug(){
        if (configData.debug){
            instance.logger.info("Debug:" + configData.debug)
            instance.logger.info("Prefix:" + configData.prefix)
            instance.logger.info("Default:" + configData.default)
            instance.logger.info("Number:" + configData.number)
            instance.logger.info("RefreshTime:" + configData.refreshTime)
            instance.logger.info("OnOpen:" + configData.messages["OnOpen"])
            instance.logger.info("OnAcceptQuests:" + configData.messages["OnAcceptQuests"])
            instance.logger.info("OnAcceptFailure:" + configData.messages["OnAcceptFailure"])
            instance.logger.info("OnFinish:" + configData.messages["OnFinish"])
        } else return
    }
}