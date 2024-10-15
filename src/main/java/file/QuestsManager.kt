package file

import main.ElysiaDailyQuests
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File
//任务数据管理类
class QuestsManager(private val instance : ElysiaDailyQuests) {
    private var questsDataHashMap : HashMap<String,QuestsData> = HashMap()
    fun getQuestsDataHashMap() = this.questsDataHashMap
    fun loadQuestsData(){
        questsDataHashMap.clear()
        val file = File("${instance.dataFolder}/Quests")
        findAllYmlFiles(file)
    }
    private fun findAllYmlFiles(folder: File) {
        val files = folder.listFiles()
        if (files != null) {
            for (file in files) {
                if (file.isDirectory) {
                    // 如果是文件夹则递归查找
                    findAllYmlFiles(file)
                } else if (file.name.endsWith(".yml")) {
                    // 如果是YML文件则加入结果列表
                    val gemFolder = File(folder.toString() + "/" + file.name)
                    val config = YamlConfiguration.loadConfiguration(gemFolder)
                    loadAllData(config)
                }
            }
        }
    }
    private fun loadAllData(config: YamlConfiguration) {
        val questsData: QuestsData = createQuestsData(config)
        questsDataHashMap[questsData.group] = questsData
        logQuestsDataIfDebug(questsData,config.name)
    }
    private fun createQuestsData(config: YamlConfiguration): QuestsData {
        return QuestsData(
            config.getString("Group"),
            config.getStringList("Tasks")
        )
    }
    private fun logQuestsDataIfDebug(questsData: QuestsData,name: String){
        if (ElysiaDailyQuests.getConfigManager().getConfigData().debug){
            instance.logger.info("载入文件:$name")
            instance.logger.info("Group:" + questsData.group)
            instance.logger.info("Tasks:" + questsData.tasks.toString())
        } else return
    }
}