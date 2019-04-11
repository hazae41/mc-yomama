package hazae41.minecraft.yomama

import hazae41.minecraft.kotlin.bukkit.BukkitPlugin
import hazae41.minecraft.kotlin.bukkit.command
import hazae41.minecraft.kotlin.bukkit.msg
import hazae41.minecraft.kotlin.bukkit.update
import hazae41.minecraft.kotlin.bungee.BungeePlugin
import hazae41.minecraft.kotlin.bungee.command
import hazae41.minecraft.kotlin.bungee.msg
import hazae41.minecraft.kotlin.bungee.update
import hazae41.minecraft.kotlin.catch
import net.md_5.bungee.api.ChatColor.AQUA
import net.md_5.bungee.api.ChatColor.LIGHT_PURPLE
import net.md_5.bungee.api.connection.ProxiedPlayer
import org.bukkit.entity.Player
import java.net.URL
import java.util.*

fun yomama() = URL("http://api.apithis.net/yomama.php").readText()
val err = "Yo mama's so stupid that she broke the connexion"
val <E> Collection<E>.random get() = toList()[Random().nextInt(size)]

class BukkitYoMama: BukkitPlugin(){
    override fun onEnable() {
        update(16000, AQUA)
        command("yomama"){ args ->
            fun err(ex: Exception) { msg(err); ex.printStackTrace() }
            var cb: (String) -> Unit = ::msg
            when(args.getOrNull(0)){
                "say" -> when(this){
                    is Player -> cb = ::chat
                    server.consoleSender -> cb = {server.broadcastMessage(it)}
                }
                "tell" -> if(this is Player)
                    cb = {
                        val player = args.getOrNull(1)
                            ?: server.onlinePlayers.random.name
                        chat("/tell $player $it")
                    }
            }
            catch(::err){ yomama().let(cb) }
        }
    }
}

class BungeeYoMama: BungeePlugin(){
    override fun onEnable() {
        update(16000, LIGHT_PURPLE)
        command("yomama"){ args ->
            fun err(ex: Exception){ msg(err); ex.printStackTrace() }
            var cb: (String) -> Unit = ::msg
            when(args.getOrNull(0)){
                "say" -> when(this) {
                    is ProxiedPlayer -> cb = ::chat
                    proxy.console -> cb = proxy::broadcast
                }
                "tell" -> if(this is ProxiedPlayer)
                    cb = { it ->
                        val player = args.getOrNull(1)
                            ?: server.info.players.random.name
                        chat("/tell $player $it")
                    }
            }
            catch(::err){ yomama().let(cb) }
        }
    }
}