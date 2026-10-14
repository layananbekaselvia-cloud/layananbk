package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.data.AppRepository
import com.example.data.DefaultComics
import com.example.model.ComicChapter
import com.example.model.QuizSubmission
import com.example.ui.screens.ComicReaderScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.LoginScreen
import com.example.ui.screens.QuizResultScreen
import com.example.ui.screens.QuizScreen
import com.example.ui.screens.TeacherDashboardScreen
import com.example.ui.screens.TeacherPinDialog
import com.example.ui.theme.MyApplicationTheme

sealed interface AppScreen {
    data object Login : AppScreen
    data object Home : AppScreen
    data class ComicReader(val chapter: ComicChapter) : AppScreen
    data object Quiz : AppScreen
    data class QuizResult(val submission: QuizSubmission) : AppScreen
    data object TeacherDashboard : AppScreen
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val repository = AppRepository(applicationContext)

        setContent {
            MyApplicationTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    AppRoot(repository = repository)
                }
            }
        }
    }
}

@Composable
fun AppRoot(repository: AppRepository) {
    val currentStudent by repository.currentStudent.collectAsState()
    val questions by repository.questions.collectAsState()
    val submissions by repository.submissions.collectAsState()

    val chapters = remember { DefaultComics.getChapters() }

    var currentScreen by remember(currentStudent.isValid) {
        mutableStateOf<AppScreen>(
            if (currentStudent.isValid) AppScreen.Home else AppScreen.Login
        )
    }

    var showTeacherPinDialog by remember { mutableStateOf(false) }
    var quizStartTimeMillis by remember { mutableLongStateOf(0L) }

    when (val screen = currentScreen) {
        is AppScreen.Login -> {
            LoginScreen(
                onLoginSuccess = { name, studentClass ->
                    repository.setStudent(name, studentClass)
                    currentScreen = AppScreen.Home
                },
                onOpenTeacherDashboard = {
                    showTeacherPinDialog = true
                }
            )
        }

        is AppScreen.Home -> {
            HomeScreen(
                student = currentStudent,
                chapters = chapters,
                onSelectChapter = { chapter ->
                    currentScreen = AppScreen.ComicReader(chapter)
                },
                onStartQuiz = {
                    quizStartTimeMillis = System.currentTimeMillis()
                    currentScreen = AppScreen.Quiz
                },
                onOpenTeacherDashboard = {
                    showTeacherPinDialog = true
                },
                onLogout = {
                    repository.clearStudent()
                    currentScreen = AppScreen.Login
                }
            )
        }

        is AppScreen.ComicReader -> {
            val currentIndex = chapters.indexOfFirst { it.id == screen.chapter.id }.coerceAtLeast(0)
            val hasPrevious = currentIndex > 0
            val hasNext = currentIndex < chapters.size - 1

            ComicReaderScreen(
                chapter = screen.chapter,
                hasPreviousChapter = hasPrevious,
                hasNextChapter = hasNext,
                onBack = { currentScreen = AppScreen.Home },
                onPreviousChapter = {
                    if (hasPrevious) {
                        currentScreen = AppScreen.ComicReader(chapters[currentIndex - 1])
                    }
                },
                onNextChapter = {
                    if (hasNext) {
                        currentScreen = AppScreen.ComicReader(chapters[currentIndex + 1])
                    }
                },
                onStartQuiz = {
                    quizStartTimeMillis = System.currentTimeMillis()
                    currentScreen = AppScreen.Quiz
                }
            )
        }

        is AppScreen.Quiz -> {
            QuizScreen(
                questions = questions,
                quizStartTimeMillis = quizStartTimeMillis,
                onBack = { currentScreen = AppScreen.Home },
                onSubmitQuiz = { score, correctCount, total, timeSpentSeconds ->
                    val submission = repository.submitQuizResult(
                        score = score,
                        correctCount = correctCount,
                        total = total,
                        timeSeconds = timeSpentSeconds
                    )
                    currentScreen = AppScreen.QuizResult(submission)
                }
            )
        }

        is AppScreen.QuizResult -> {
            QuizResultScreen(
                submission = screen.submission,
                questions = questions,
                onHome = { currentScreen = AppScreen.Home },
                onReadComic = {
                    val firstChapter = chapters.firstOrNull() ?: DefaultComics.getChapters().first()
                    currentScreen = AppScreen.ComicReader(firstChapter)
                }
            )
        }

        is AppScreen.TeacherDashboard -> {
            TeacherDashboardScreen(
                repository = repository,
                submissions = submissions,
                questions = questions,
                onBack = { currentScreen = AppScreen.Home }
            )
        }
    }

    // Teacher PIN Verification Modal
    if (showTeacherPinDialog) {
        TeacherPinDialog(
            onDismiss = { showTeacherPinDialog = false },
            onSuccess = {
                showTeacherPinDialog = false
                currentScreen = AppScreen.TeacherDashboard
            }
        )
    }
}
