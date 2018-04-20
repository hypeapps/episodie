package pl.hypeapp.dataproviders.datasource

import org.junit.Test
import java.util.logging.Logger

class PagingHelperTest {

    @Test
    fun test() {
        val totalpage: Double = 82.0 / 20.0
        Logger.getAnonymousLogger().info("" + Math.floor(totalpage))
    }

}
