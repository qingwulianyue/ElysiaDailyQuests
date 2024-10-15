package file

import main.ElysiaDailyQuests
import org.bukkit.Bukkit
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File
import java.io.IOException
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
//玩家数据管理类
class PlayerDataManager(private val instance : ElysiaDailyQuests) {
    private var playerDataHashMap : HashMap<UUID,PlayerData> = HashMap()
    fun getPlayerDataHashMap() = this.playerDataHashMap
    //保存玩家数据
    @Throws(IOException::class)
    fun savePlayerData() {
        val playerDataFolderPath = instance.dataFolder.toPath().resolve("PlayerData")//定位PlayerData目录
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")//时间数据格式化标准
        for (uuid in playerDataHashMap.keys) {
            val playerDataFilePath = playerDataFolderPath.resolve(uuid.toString())//定位玩家数据文件
            val playerDataFile = playerDataFilePath.toFile()
            if (!playerDataFile.exists()) {
                playerDataFile.createNewFile()//若无则创建
            }
            val playerDataFileConfiguration: FileConfiguration = YamlConfiguration.loadConfiguration(playerDataFile)//序列化文件
            val playerData = playerDataHashMap[uuid]
            playerDataFileConfiguration.set("group",playerData?.group)
            playerDataFileConfiguration.set("refreshTime",playerData?.refreshTime?.format(formatter))
            playerDataFileConfiguration.save(playerDataFile)
        }
    }
    //缓存玩家数据
    fun loadPlayerData() {
        playerDataHashMap.clear()
        val folder = File("${instance.dataFolder}/PlayerData") //获取PlayerData目录
        val files = folder.listFiles() //获取该目录下的文件列表
        if (files != null) { //若有文件
            for (file in files) { //遍历所有文件
                val uuid = UUID.fromString(file.name) //获取文件名即uuid作为键
                val playerDataFileConfiguration: FileConfiguration = YamlConfiguration.loadConfiguration(file) //序列化文件
                val playerData = PlayerData(
                    uuid,
                    playerDataFileConfiguration.getString("group"),
                    LocalDateTime.parse(playerDataFileConfiguration.getString("refreshTime"),DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                )
                playerDataHashMap[uuid] = playerData //存放uuid-玩家数据
            }
        }
    }
    //更改玩家每日任务分组
    fun changeGroup(uuid: UUID, group: String){
        val playerData = playerDataHashMap[uuid]
        playerData?.group = group
        playerDataHashMap[uuid] = playerData!!
    }
    //新建玩家数据信息
    fun newPlayerData(uuid: UUID){
        val playerData = PlayerData(
            uuid,
            ElysiaDailyQuests.getConfigManager().getConfigData().default,
            LocalDateTime.now(),
        )
        playerDataHashMap[uuid] = playerData
    }
    //刷新任务
    fun newTask(uuid: UUID){
        println(ElysiaDailyQuests.getPlayerDataManager().getPlayerDataHashMap().containsKey(uuid))
        if (! ElysiaDailyQuests.getPlayerDataManager().getPlayerDataHashMap().containsKey(uuid)){
            ElysiaDailyQuests.getPlayerDataManager().newPlayerData(uuid)
        } else if (!checkRefresh(ElysiaDailyQuests.getPlayerDataManager().getPlayerDataHashMap()[uuid]!!)){
            return
        }
        acceptTask(uuid)
    }
    //接取任务
    private fun acceptTask(uuid: UUID) {
        println("acceptTask")
        val playerData = playerDataHashMap[uuid]
        val player = Bukkit.getPlayer(uuid)
        val taskChoose = ElysiaDailyQuests.getQuestsManager().getQuestsDataHashMap()[playerData?.group]?.tasks
        println(taskChoose)
        if (taskChoose != null) {
            for (task in taskChoose.take(ElysiaDailyQuests.getConfigManager().getConfigData().number)) {
                println(task)
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender()!!,"chemdah quest accept ${player.name} $task")
            }
        }
    }

    private fun checkRefresh(playerData: PlayerData) : Boolean{
        val refreshTime = LocalDateTime.of(LocalDate.now(),ElysiaDailyQuests.getConfigManager().getConfigData().refreshTime)
        return playerData.refreshTime.isAfter(refreshTime)
    }
}