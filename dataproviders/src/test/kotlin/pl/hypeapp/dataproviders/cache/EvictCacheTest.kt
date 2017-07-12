package pl.hypeapp.dataproviders.cache

import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.verify
import io.rx_cache2.DynamicKey
import io.rx_cache2.internal.Disk
import org.amshove.kluent.mock
import org.junit.Before
import org.junit.Test

class EvictCacheTest {

    private lateinit var evictCache: EvictCache

    private val disk: Disk = mock()

    companion object {
        val KEY = "MP"
        val COMPOSED_KEY = "getMostPopular\$d\$d\$d\$$KEY\$g\$g\$g\$"
    }

    @Before
    fun setUp() {
        evictCache = EvictCache(disk)
    }

    @Test
    fun `should evict records matching dynamic key`() {
        val dynamicKey: DynamicKey = DynamicKey(KEY)
        given(disk.allKeys()).willReturn(arrayListOf(COMPOSED_KEY))

        evictCache.evictAllMatchingDynamicKey(dynamicKey)

        verify(disk).evict(COMPOSED_KEY)
    }

}
