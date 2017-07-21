package pl.hypeapp.dataproviders.executor

import io.reactivex.schedulers.Schedulers
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class JobExecutorTest {

    lateinit var jobExecutor: JobExecutor

    @Before
    fun setUp() {
        jobExecutor = JobExecutor()
    }

    @Test
    fun `should return io scheduler`() {
        val scheduler = jobExecutor.getScheduler()

        assertEquals(scheduler, Schedulers.io())
    }

}
