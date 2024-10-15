package file

import java.time.LocalTime
//config配置类
data class ConfigData(val debug: Boolean, val  prefix: String,val default: String, val number: Int,val refreshTime: LocalTime,val messages: HashMap<String,String>)
