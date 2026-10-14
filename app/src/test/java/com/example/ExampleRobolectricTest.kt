package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.AppRepository
import com.example.data.DefaultComics
import com.example.data.DefaultQuestions
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class ExampleRobolectricTest {

    @Test
    fun `read string from context`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val appName = context.getString(R.string.app_name)
        assertEquals("BK Sahabat", appName)
    }

    @Test
    fun `default questions should have 15 HOTS questions`() {
        val questions = DefaultQuestions.getList()
        assertEquals(15, questions.size)
        questions.forEach { q ->
            assertTrue(q.options.size == 4)
            assertTrue(q.correctIndex in 0..3)
            assertTrue(q.explanation.isNotBlank())
        }
    }

    @Test
    fun `default comics should have 4 chapters with branching choices`() {
        val chapters = DefaultComics.getChapters()
        assertEquals(4, chapters.size)
        val ch1 = chapters[0]
        val decisionPanel = ch1.panels.find { it.isDecisionPoint }
        assertTrue(decisionPanel != null)
        assertTrue(decisionPanel?.choiceA != null)
        assertTrue(decisionPanel?.choiceB != null)
    }

    @Test
    fun `repository handles student login and submission stats`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val repo = AppRepository(context)
        repo.setStudent("Budi Santoso", "9C")
        assertEquals("Budi Santoso", repo.currentStudent.value.name)
        assertEquals("9C", repo.currentStudent.value.studentClass)

        val csv = repo.generateExcelCsvContent()
        assertTrue(csv.contains("Nama Lengkap"))
    }
}
