package file

import java.time.LocalDateTime
import java.util.UUID
//玩家数据类
data class PlayerData(var uuid : UUID, var group : String, var refreshTime : LocalDateTime)
