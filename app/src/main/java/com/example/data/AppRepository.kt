package com.example.data

import android.content.Context
import android.content.SharedPreferences
import com.example.model.QuizQuestion
import com.example.model.QuizSubmission
import com.example.model.StudentProfile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

class AppRepository(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("bk_sahabat_prefs", Context.MODE_PRIVATE)

    private val _currentStudent = MutableStateFlow(StudentProfile())
    val currentStudent: StateFlow<StudentProfile> = _currentStudent.asStateFlow()

    private val _questions = MutableStateFlow<List<QuizQuestion>>(emptyList())
    val questions: StateFlow<List<QuizQuestion>> = _questions.asStateFlow()

    private val _submissions = MutableStateFlow<List<QuizSubmission>>(emptyList())
    val submissions: StateFlow<List<QuizSubmission>> = _submissions.asStateFlow()

    init {
        loadSavedStudent()
        loadQuestions()
        loadSubmissions()
    }

    private fun loadSavedStudent() {
        val name = prefs.getString("student_name", "") ?: ""
        val studentClass = prefs.getString("student_class", "9A") ?: "9A"
        if (name.isNotBlank()) {
            _currentStudent.value = StudentProfile(name, studentClass)
        }
    }

    fun setStudent(name: String, studentClass: String) {
        val profile = StudentProfile(name.trim(), studentClass)
        _currentStudent.value = profile
        prefs.edit()
            .putString("student_name", profile.name)
            .putString("student_class", profile.studentClass)
            .apply()
    }

    fun clearStudent() {
        _currentStudent.value = StudentProfile()
        prefs.edit().remove("student_name").remove("student_class").apply()
    }

    private fun loadQuestions() {
        val rawJson = prefs.getString("custom_questions_json", null)
        if (rawJson != null) {
            try {
                val array = JSONArray(rawJson)
                val list = mutableListOf<QuizQuestion>()
                for (i in 0 until array.length()) {
                    val obj = array.getJSONObject(i)
                    val optsArray = obj.getJSONArray("options")
                    val opts = mutableListOf<String>()
                    for (j in 0 until optsArray.length()) {
                        opts.add(optsArray.getString(j))
                    }
                    list.add(
                        QuizQuestion(
                            id = obj.getInt("id"),
                            question = obj.getString("question"),
                            options = opts,
                            correctIndex = obj.getInt("correctIndex"),
                            explanation = obj.getString("explanation"),
                            category = obj.getString("category"),
                            isCustom = obj.optBoolean("isCustom", false)
                        )
                    )
                }
                if (list.isNotEmpty()) {
                    _questions.value = list
                    return
                }
            } catch (_: Exception) {
                // fallback to default
            }
        }
        _questions.value = DefaultQuestions.getList()
    }

    private fun saveQuestions(list: List<QuizQuestion>) {
        _questions.value = list
        val array = JSONArray()
        for (q in list) {
            val obj = JSONObject()
            obj.put("id", q.id)
            obj.put("question", q.question)
            val opts = JSONArray()
            for (opt in q.options) {
                opts.put(opt)
            }
            obj.put("options", opts)
            obj.put("correctIndex", q.correctIndex)
            obj.put("explanation", q.explanation)
            obj.put("category", q.category)
            obj.put("isCustom", q.isCustom)
            array.put(obj)
        }
        prefs.edit().putString("custom_questions_json", array.toString()).apply()
    }

    fun editQuestion(updated: QuizQuestion) {
        val current = _questions.value.toMutableList()
        val index = current.indexOfFirst { it.id == updated.id }
        if (index != -1) {
            current[index] = updated.copy(isCustom = true)
        } else {
            current.add(updated.copy(isCustom = true))
        }
        saveQuestions(current)
    }

    fun deleteQuestion(questionId: Int) {
        val current = _questions.value.filter { it.id != questionId }
        saveQuestions(current)
    }

    fun addQuestion(question: QuizQuestion) {
        val current = _questions.value.toMutableList()
        val newId = (current.maxOfOrNull { it.id } ?: 0) + 1
        current.add(question.copy(id = newId, isCustom = true))
        saveQuestions(current)
    }

    fun resetQuestionsToDefault() {
        val defaultList = DefaultQuestions.getList()
        saveQuestions(defaultList)
    }

    private fun loadSubmissions() {
        val rawJson = prefs.getString("submissions_json", null)
        if (rawJson != null) {
            try {
                val array = JSONArray(rawJson)
                val list = mutableListOf<QuizSubmission>()
                for (i in 0 until array.length()) {
                    val obj = array.getJSONObject(i)
                    list.add(
                        QuizSubmission(
                            id = obj.getString("id"),
                            studentName = obj.getString("studentName"),
                            studentClass = obj.getString("studentClass"),
                            score = obj.getInt("score"),
                            correctCount = obj.getInt("correctCount"),
                            totalQuestions = obj.getInt("totalQuestions"),
                            timeSpentSeconds = obj.getLong("timeSpentSeconds"),
                            completedAtFormatted = obj.getString("completedAtFormatted"),
                            timestampMillis = obj.optLong("timestampMillis", System.currentTimeMillis())
                        )
                    )
                }
                _submissions.value = list
                return
            } catch (_: Exception) {
                // fallback
            }
        }
        // Seed realistic sample data if empty so teacher dashboard charts look alive initially!
        seedInitialSampleSubmissions()
    }

    private fun seedInitialSampleSubmissions() {
        val samples = listOf(
            QuizSubmission(
                id = UUID.randomUUID().toString(),
                studentName = "Ahmad Zaki",
                studentClass = "9A",
                score = 93,
                correctCount = 14,
                totalQuestions = 15,
                timeSpentSeconds = 480,
                completedAtFormatted = "14/10/2026 08:15"
            ),
            QuizSubmission(
                id = UUID.randomUUID().toString(),
                studentName = "Nabila Putri",
                studentClass = "9A",
                score = 100,
                correctCount = 15,
                totalQuestions = 15,
                timeSpentSeconds = 410,
                completedAtFormatted = "14/10/2026 08:32"
            ),
            QuizSubmission(
                id = UUID.randomUUID().toString(),
                studentName = "Dimas Arya",
                studentClass = "9B",
                score = 80,
                correctCount = 12,
                totalQuestions = 15,
                timeSpentSeconds = 540,
                completedAtFormatted = "14/10/2026 09:10"
            ),
            QuizSubmission(
                id = UUID.randomUUID().toString(),
                studentName = "Siti Rahma",
                studentClass = "9C",
                score = 87,
                correctCount = 13,
                totalQuestions = 15,
                timeSpentSeconds = 495,
                completedAtFormatted = "14/10/2026 09:45"
            ),
            QuizSubmission(
                id = UUID.randomUUID().toString(),
                studentName = "Bagas Pratama",
                studentClass = "9D",
                score = 73,
                correctCount = 11,
                totalQuestions = 15,
                timeSpentSeconds = 620,
                completedAtFormatted = "14/10/2026 10:05"
            ),
            QuizSubmission(
                id = UUID.randomUUID().toString(),
                studentName = "Larasati Dewi",
                studentClass = "9E",
                score = 100,
                correctCount = 15,
                totalQuestions = 15,
                timeSpentSeconds = 395,
                completedAtFormatted = "14/10/2026 10:30"
            )
        )
        saveSubmissionsList(samples)
    }

    private fun saveSubmissionsList(list: List<QuizSubmission>) {
        _submissions.value = list
        val array = JSONArray()
        for (s in list) {
            val obj = JSONObject()
            obj.put("id", s.id)
            obj.put("studentName", s.studentName)
            obj.put("studentClass", s.studentClass)
            obj.put("score", s.score)
            obj.put("correctCount", s.correctCount)
            obj.put("totalQuestions", s.totalQuestions)
            obj.put("timeSpentSeconds", s.timeSpentSeconds)
            obj.put("completedAtFormatted", s.completedAtFormatted)
            obj.put("timestampMillis", s.timestampMillis)
            array.put(obj)
        }
        prefs.edit().putString("submissions_json", array.toString()).apply()
    }

    fun submitQuizResult(score: Int, correctCount: Int, total: Int, timeSeconds: Long): QuizSubmission {
        val student = _currentStudent.value
        val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
        val formattedDate = dateFormat.format(Date())

        val submission = QuizSubmission(
            id = UUID.randomUUID().toString(),
            studentName = if (student.name.isNotBlank()) student.name else "Murid Kelas 9",
            studentClass = student.studentClass,
            score = score,
            correctCount = correctCount,
            totalQuestions = total,
            timeSpentSeconds = timeSeconds,
            completedAtFormatted = formattedDate,
            timestampMillis = System.currentTimeMillis()
        )

        val updated = listOf(submission) + _submissions.value
        saveSubmissionsList(updated)
        return submission
    }

    fun clearAllSubmissions() {
        saveSubmissionsList(emptyList())
    }

    // CSV for Excel Download
    fun generateExcelCsvContent(): String {
        val sb = StringBuilder()
        sb.append("No,Nama Lengkap,Kelas,Nilai,Benar,Total Soal,Durasi (Menit:Detik),Waktu Selesai\n")
        val list = _submissions.value
        list.forEachIndexed { index, item ->
            val safeName = item.studentName.replace(",", " ")
            sb.append("${index + 1},$safeName,${item.studentClass},${item.score},${item.correctCount},${item.totalQuestions},${item.durationFormatted},\"${item.completedAtFormatted}\"\n")
        }
        return sb.toString()
    }

    // Printable Official BK Report (PDF ready)
    fun generateOfficialPdfReport(): String {
        val sb = StringBuilder()
        sb.append("=================================================================\n")
        sb.append("         LAPORAN HASIL ASESMEN BIMBINGAN & KONSELING (BK)        \n")
        sb.append("                SMP - LAYANAN BIMBINGAN KONSELING                \n")
        sb.append("                 Guru Pembimbing: Bu Selvi, S.Pd                 \n")
        sb.append("=================================================================\n\n")

        val list = _submissions.value
        val total = list.size
        val highest = list.maxOfOrNull { it.score } ?: 0
        val lowest = list.minOfOrNull { it.score } ?: 0
        val avg = if (list.isNotEmpty()) list.map { it.score }.average() else 0.0
        val fastest = list.minByOrNull { it.timeSpentSeconds }

        sb.append("RINGKASAN STATISTIK EVALUASI:\n")
        sb.append("- Total Murid Mengikuti: $total siswa\n")
        sb.append("- Nilai Tertinggi      : $highest\n")
        sb.append("- Nilai Terendah       : $lowest\n")
        sb.append("- Rata-rata Nilai      : %.1f\n".format(avg))
        sb.append("- Waktu Tercepat       : ${fastest?.durationFormatted ?: "-"} (${fastest?.studentName ?: "-"})\n\n")

        sb.append("DISTRIBUSI NILAI PER KELAS:\n")
        listOf("9A", "9B", "9C", "9D", "9E").forEach { cls ->
            val classList = list.filter { it.studentClass == cls }
            val classAvg = if (classList.isNotEmpty()) classList.map { it.score }.average() else 0.0
            sb.append("  * Kelas $cls: ${classList.size} siswa | Rata-rata: %.1f\n".format(classAvg))
        }

        sb.append("\nDAFTAR DETAIL HASIL SISWA:\n")
        sb.append("-----------------------------------------------------------------\n")
        sb.append("%-4s %-22s %-6s %-6s %-8s %-16s\n".format("No", "Nama Siswa", "Kelas", "Nilai", "Durasi", "Tanggal"))
        sb.append("-----------------------------------------------------------------\n")
        list.forEachIndexed { i, s ->
            sb.append("%-4d %-22s %-6s %-6d %-8s %-16s\n".format(
                i + 1,
                s.studentName.take(20),
                s.studentClass,
                s.score,
                s.durationFormatted,
                s.completedAtFormatted
            ))
        }
        sb.append("-----------------------------------------------------------------\n\n")
        sb.append("Catatan Evaluasi Guru BK:\n")
        sb.append("Sebagian besar siswa telah memahami pentingnya batasan diri dalam pacaran sehat,\n")
        sb.append("bahaya judi online dan vape, serta etika kesantunan bermedia sosial.\n\n")
        sb.append("Mengetahui,\n")
        sb.append("Guru BK (Bimbingan Konseling)\n\n\n")
        sb.append("Bu Selvi, S.Pd\n")
        sb.append("NIP. 19870512 201101 2 008\n")
        return sb.toString()
    }

    // Questions and Keys Document
    fun generateQuestionsAndKeyDoc(): String {
        val sb = StringBuilder()
        sb.append("=================================================================\n")
        sb.append("           NASKAH SOAL & KUNCI JAWABAN ASESMEN HOTS BK           \n")
        sb.append("         TEMA: BATASAN DIRI, ANTI-JUDOL/VAPE, DISIPLIN & MEDSOS   \n")
        sb.append("                 Guru Pengampu: Bu Selvi, Layanan BK             \n")
        sb.append("=================================================================\n\n")

        val qList = _questions.value
        qList.forEachIndexed { idx, q ->
            sb.append("${idx + 1}. [${q.category}]\n")
            sb.append("   ${q.question}\n\n")
            val labels = listOf("A", "B", "C", "D")
            q.options.forEachIndexed { optIdx, opt ->
                val label = labels.getOrElse(optIdx) { "-" }
                sb.append("   $label. $opt\n")
            }
            sb.append("\n   >>> KUNCI JAWABAN: ${q.correctOptionLetter} (${q.correctOptionText})\n")
            sb.append("   >>> PEMBAHASAN: ${q.explanation}\n")
            sb.append("-----------------------------------------------------------------\n\n")
        }
        return sb.toString()
    }
}
