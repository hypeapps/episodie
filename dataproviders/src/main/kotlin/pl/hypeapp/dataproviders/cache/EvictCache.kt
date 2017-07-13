package pl.hypeapp.dataproviders.cache

import io.rx_cache2.DynamicKey
import io.rx_cache2.internal.Disk
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EvictCache @Inject constructor(private val disk: Disk) {

    private companion object {
        val PREFIX_DYNAMIC_KEY = "\$d\$d\$d$"
        val PREFIX_DYNAMIC_KEY_GROUP = "\$g\$g\$g$"
    }

    fun evictAllMatchingDynamicKey(dynamicKey: DynamicKey) {
        val key: String = dynamicKey.dynamicKey.toString()
        disk.allKeys().forEach({
            if (isMatchingKey(it, key)) {
                disk.evict(it)
            }
        })
    }

    private fun isMatchingKey(diskKey: String, dynamicKey: String): Boolean = decomposeKey(diskKey) == (dynamicKey)

    private fun decomposeKey(diskKey: String): String =
            diskKey.substring(diskKey.indexOf(PREFIX_DYNAMIC_KEY) + PREFIX_DYNAMIC_KEY.length, diskKey.lastIndexOf(PREFIX_DYNAMIC_KEY_GROUP))

}
