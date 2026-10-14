package com.example.data

import com.example.R
import com.example.model.BubbleType
import com.example.model.ComicChapter
import com.example.model.ComicChoice
import com.example.model.ComicPanel
import com.example.model.ConsequenceType

object DefaultComics {
    fun getChapters(): List<ComicChapter> = listOf(
        ComicChapter(
            id = 1,
            title = "Episode 1: Getar Rasa Suka & Menjaga Batasan Diri",
            subtitle = "Pacaran Sehat Usia SMP: Jangan Sampai Kebablasan!",
            themeTag = "Pacaran Sehat & Batasan",
            summary = "Laras dan Bima saling mengagumi di kelas 9. Namun ketika Bima mulai menuntut bukti rasa sayang fisik dan berduaan di tempat sepi, Laras berada di persimpangan keputusan penting.",
            coverResId = R.drawable.comic_ch1_dating_1791988061714,
            panels = listOf(
                ComicPanel(
                    speaker = "Narator",
                    roleBadge = "Kisah Remaja SMP",
                    text = "Sore itu di sudut taman sekolah, semilir angin meniup pelan halaman buku catatan. Laras (14) dan Bima (14) sedang duduk bersama setelah jam ekstrakurikuler selesai...",
                    bubbleType = BubbleType.NARRATOR
                ),
                ComicPanel(
                    speaker = "Bima",
                    roleBadge = "Siswa Kelas 9B",
                    text = "Laras... kita kan udah jadian sebulan nih. Tapi kenapa sih kamu selalu nolak kalau aku ajak nongkrong berdua aja di rumahku pas sepi? Kan kita pacaran...",
                    bubbleType = BubbleType.SPEECH_RIGHT,
                    soundEffect = "*DEG!*"
                ),
                ComicPanel(
                    speaker = "Laras",
                    roleBadge = "Batin Laras",
                    text = "Duh... jantungku berdegup kencang. Aku emang suka sama Bima, tapi ajakannya bikin aku nggak nyaman dan takut kebablasan...",
                    bubbleType = BubbleType.THOUGHT
                ),
                ComicPanel(
                    speaker = "Bima",
                    roleBadge = "Siswa Kelas 9B",
                    text = "Buktikan dong kalau kamu beneran sayang sama aku! Jangan kaku gitu, teman-teman yang lain aja santai...",
                    bubbleType = BubbleType.SPEECH_RIGHT,
                    soundEffect = "*TEKANAN EMOSIONAL*"
                ),
                ComicPanel(
                    speaker = "Keputusan Laras",
                    roleBadge = "PILIHAN BERCABANG",
                    text = "Laras harus mengambil sikap sekarang! Pilihan apa yang akan diambil Laras?",
                    bubbleType = BubbleType.NARRATOR,
                    isDecisionPoint = true,
                    choiceA = ComicChoice(
                        title = "PILIHAN POSITIF: TEGASKAN BATASAN DIRI",
                        description = "Menolak dengan tenang & tegas, mengutamakan kehormatan diri dan masa depan.",
                        consequenceType = ConsequenceType.POSITIVE,
                        storyResult = "Laras menarik napas panjang dan menatap mata Bima dengan mantap: 'Bima, aku hargai perasaan kamu. Tapi di usia kita sekarang, batasanku jelas: kita saling dukung belajar dan nggak berduaan di tempat sepi. Kalau kamu beneran sayang dan menghormatiku, kamu akan jaga batas ini!'\n\nHasil: Bima terdiam dan sadar bahwa kehormatan Laras tidak bisa diganggu gugat. Hubungan mereka justru menjadi saling menghargai. Nilai tryout keduanya meningkat, orang tua bangga, dan Laras terhindar dari rasa bersalah atau risiko hamil usia dini.",
                        teacherGuidance = "Nasihat Bu Selvi (Guru BK):\n'Hebat sekali, Laras! Suka pada lawan jenis itu fitrah alami remaja, tapi pacaran yang sehat HARUS punya rem dan batasan yang kuat! Jangan pernah menukar masa depan, harga diri, dan cita-citamu hanya demi pembuktian rasa sayang yang salah jalan. Jika dia memaksa, itu bukan cinta, melainkan keegoisan!'"
                    ),
                    choiceB = ComicChoice(
                        title = "PILIHAN NEGATIF: IKUT KEINGINAN KARENA TAKUT DIPUTUS",
                        description = "Pasrah menuruti ajakan berduaan di tempat sepi dan melanggar batasan kesusilaan.",
                        consequenceType = ConsequenceType.NEGATIVE,
                        storyResult = "Laras merasa ragu tapi takut ditinggalkan Bima, lalu mengiyakan ajakannya. Hari demi hari batas fisik semakin dilanggar...\n\nKonsekuensi Buruk: Laras dihantui rasa cemas, panik, dan hilang fokus belajar. Nilai ujian sekolah anjlok drastis. Ketika gosip menyebar di sekolah, Laras merasa malu dan terisolasi. Bima justru mulai menjauh dan bersikap dingin. Rasa penyesalan yang dalam membekas di hati Laras.",
                        teacherGuidance = "Peringatan Bu Selvi (Guru BK):\n'Perhatikan konsekuensi ini, anak-anakku! Kebablasan dalam pacaran di usia sekolah tidak pernah berakhir bahagia. Dampaknya sangat fatal: trauma emosional, putus sekolah, risiko kehamilan yang merusak masa depan, hingga jeratan sanksi sosial. Remaja bijak tahu kapan harus berkata TIDAK!'"
                    )
                ),
                ComicPanel(
                    speaker = "Bu Selvi",
                    roleBadge = "Guru BK Sahabat",
                    text = "Ingat ya anak-anakku kelas 9: Cinta yang tulus itu MENJAGA, bukan MERUSAK. Pacaran sehat adalah saling menginspirasi untuk lulus dengan nilai terbaik dan masuk SMA impian!",
                    bubbleType = BubbleType.TEACHER_ADVICE,
                    soundEffect = "*WEJANGAN BK*"
                )
            )
        ),
        ComicChapter(
            id = 2,
            title = "Episode 2: Jerat Ilusi Slot & Asap Beracun",
            subtitle = "Tolak Keras Judi Online dan Vape / Rokok Elektrik!",
            themeTag = "Anti-Judol & Rokok/Vape",
            summary = "Doni diajak teman nongkrongnya mencoba putar spin slot berkedok game sambil mencoba hisap vape rasa buah yang katanya bikin tenang dan keren.",
            coverResId = null,
            panels = listOf(
                ComicPanel(
                    speaker = "Narator",
                    roleBadge = "Di Belakang Kantin Luar",
                    text = "Doni melihat dua temannya sedang asyik menatap layar ponsel yang berbunyi kerlap-kerlip dengan suara koin jatuh, diiringi kepulan uap tebal beraroma mangga...",
                    bubbleType = BubbleType.NARRATOR
                ),
                ComicPanel(
                    speaker = "Riko",
                    roleBadge = "Teman Nongkrong",
                    text = "Woy Don! Sini coba spin sekali, modal dua puluh ribu bisa pecah jadi satu juta! Nih sambil hisap pod rasa mangga, dingin seger biar nggak tegang!",
                    bubbleType = BubbleType.SPEECH_LEFT,
                    soundEffect = "*KLIK-KLIK-JACKPOT?*"
                ),
                ComicPanel(
                    speaker = "Doni",
                    roleBadge = "Batin Doni",
                    text = "Uang di dompetku ini sisa tabungan beli buku latihan UNBK... Tapi katanya vape wangi buah nggak bahaya? Dan kalau jackpot lumayan bisa beli sepatu...",
                    bubbleType = BubbleType.THOUGHT
                ),
                ComicPanel(
                    speaker = "Dilema Doni",
                    roleBadge = "PILIHAN BERCABANG",
                    text = "Apakah Doni akan terjerumus ke pusaran judol dan adiksi vape?",
                    bubbleType = BubbleType.NARRATOR,
                    isDecisionPoint = true,
                    choiceA = ComicChoice(
                        title = "PILIHAN POSITIF: TOLAK DAN TINGGALKAN LOKASI",
                        description = "Menolak ajakan judol dan hisapan vape, memilih menabung dan menjaga paru-paru.",
                        consequenceType = ConsequenceType.POSITIVE,
                        storyResult = "Doni tersenyum tegas: 'Nggak bro, makasih. Bu Selvi Guru BK udah jelasin kalau slot itu judi manipulatif yang bikin bangkrut, dan vape ada nikotin kimia perusak paru-paru. Tabunganku mau buat masa depanku!'\n\nHasil: Doni pulang dengan tabungan utuh dan badan sehat bugar. Seminggu kemudian terbukti teman-temannya yang ikut judol habis uang jutaan, terlilit utang, dan terbatuk-batuk kronis.",
                        teacherGuidance = "Nasihat Bu Selvi (Guru BK):\n'Langkah cerdas, Doni! Judi online dirancang secara matematis agar bandar selalu menang dan pemain ketagihan hingga mencuri atau berutang. Vape juga bukan mainan aman, nikotinnya merusak otak remaja! Katakan TIDAK demi kesehatan dan masa depanmu!'"
                    ),
                    choiceB = ComicChoice(
                        title = "PILIHAN NEGATIF: COBA DEPOSIT DAN HISAP VAPE",
                        description = "Terbujuk rasa penasaran dan gengsi pergaulan yang salah kaprah.",
                        consequenceType = ConsequenceType.NEGATIVE,
                        storyResult = "Doni mencoba deposit 20 ribu, menang 50 ribu, lalu ketagihan hingga uang tabungan 300 ribu habis dalam 15 menit. Karena stres kalah, ia terus menghisap vape teman hingga kecanduan batuk parah.\n\nKonsekuensi Buruk: Doni nekat mengambil uang belanja ibunya karena dikejar rasa penasaran ingin 'balik modal'. Di sekolah ia tertangkap basah razia kantin membawa vape, orang tuanya dipanggil ke sekolah dengan penuh air mata malu.",
                        teacherGuidance = "Peringatan Bu Selvi (Guru BK):\n'Jangan pernah remehkan jerat judol dan vape! Sekali masuk lingkaran adiksi, kehancuran ekonomi keluarga dan catatan kriminal akan membayangi. Jika kalian tahu teman yang terjerat, segera ajak ke Ruang BK untuk kita selamatkan bersama!'"
                    )
                ),
                ComicPanel(
                    speaker = "Bu Selvi",
                    roleBadge = "Guru BK Sahabat",
                    text = "Uang jajan dari keringat orang tua itu amanah berkah, jangan diumpankan ke bandar judol dan jangan dibakar jadi asap racun di paru-paru kalian!",
                    bubbleType = BubbleType.TEACHER_ADVICE,
                    soundEffect = "*REM PENGAMAN*"
                )
            )
        ),
        ComicChapter(
            id = 3,
            title = "Episode 3: Kabur Lewat Pagar vs Hak Meraih Impian",
            subtitle = "Hindari Bolos Pelajaran, Junjung Tanggung Jawab Pelajar!",
            themeTag = "Disiplin & Hak Pendidikan",
            summary = "Pelajaran Bahasa Inggris siang hari terasa berat bagi Farhan. Teman sekelasnya mengajak cabut lewat pagar belakang sekolah untuk main playstation di rental.",
            coverResId = null,
            panels = listOf(
                ComicPanel(
                    speaker = "Narator",
                    roleBadge = "Jam Ke-5 Setelah Istirahat",
                    text = "Bel berbunyi menandakan pergantian jam pelajaran. Farhan tampak lesu karena belum terlalu menguasai tugas presentasi yang akan dibahas...",
                    bubbleType = BubbleType.NARRATOR
                ),
                ComicPanel(
                    speaker = "Alif",
                    roleBadge = "Siswa Kelas 9C",
                    text = "Farhan! Ngapain pusing dengerin guru di kelas gerah begini? Ayok loncat pagar belakang, kita mabar PS sampai sore!",
                    bubbleType = BubbleType.SPEECH_LEFT,
                    soundEffect = "*BISIKAN PENGGODA*"
                ),
                ComicPanel(
                    speaker = "Farhan",
                    roleBadge = "Batin Farhan",
                    text = "Ibu pagi-pagi udah bangun jam 4 jualan kue demi bayar seragam dan transportasiku sekolah... Kalau aku bolos, apa jadinya perjuangan Ibu?",
                    bubbleType = BubbleType.THOUGHT
                ),
                ComicPanel(
                    speaker = "Keputusan Farhan",
                    roleBadge = "PILIHAN BERCABANG",
                    text = "Farhan dihadapkan pada pilihan antara pelarian semu atau tanggung jawab belajar:",
                    bubbleType = BubbleType.NARRATOR,
                    isDecisionPoint = true,
                    choiceA = ComicChoice(
                        title = "PILIHAN POSITIF: TETAP DI KELAS DAN MEMINTA BANTUAN",
                        description = "Bertanggung jawab atas kewajiban belajar dan menghargai hak pendidikan.",
                        consequenceType = ConsequenceType.POSITIVE,
                        storyResult = "Farhan menggelengkan kepala: 'Sorry Lif, aku nggak mau bolos. Orang tuaku kerja keras biar aku bisa sekolah di sini. Aku mau masuk kelas dan nanya ke guru kalau belum paham.'\n\nHasil: Di kelas, guru menjelaskan materi dengan sabar hingga Farhan mengerti. Farhan mendapat nilai 88 di ulangan harian dan bisa mendaftar ke SMK Favorit impiannya.",
                        teacherGuidance = "Nasihat Bu Selvi (Guru BK):\n'Luar biasa integritasmu, Farhan! Belajar memang butuh lelah dan sabar, tapi buah keberhasilannya sangat manis. Jangan pernah barter hak istimewamu mengenyam pendidikan hanya demi beberapa jam kesenangan semu di luar sekolah!'"
                    ),
                    choiceB = ComicChoice(
                        title = "PILIHAN NEGATIF: IKUT LONCAT PAGAR DAN BOLOS",
                        description = "Mengabaikan kewajiban dan membohongi orang tua.",
                        consequenceType = ConsequenceType.NEGATIVE,
                        storyResult = "Farhan ikut memanjat pagar, namun celananya tersangkut kawat berduri dan ia terjatuh. Petugas keamanan sekolah memergoki mereka di warung rental game.\n\nKonsekuensi Buruk: Farhan diskors 3 hari, nilainya kosong pada ujian penting, dan ibunya menangis terisak saat menandatangani surat peringatan di Ruang BK.",
                        teacherGuidance = "Peringatan Bu Selvi (Guru BK):\n'Bolos sekolah adalah awal dari sikap lari dari tanggung jawab hidup. Ingat, ada doa dan tetesan keringat orang tua di setiap langkah kakimu ke sekolah. Hormati pengorbanan mereka!'"
                    )
                ),
                ComicPanel(
                    speaker = "Bu Selvi",
                    roleBadge = "Guru BK Sahabat",
                    text = "Sekolah adalah jembatan emas menuju masa depan. Rawat jembatan itu dengan disiplin, kejujuran, dan rasa tanggung jawab penuh!",
                    bubbleType = BubbleType.TEACHER_ADVICE,
                    soundEffect = "*SEMANGAT BELAJAR*"
                )
            )
        ),
        ComicChapter(
            id = 4,
            title = "Episode 4: Jejak Digital: Bukan Tempat Ejekan & Foto Terbuka",
            subtitle = "Etika Medsos: No Foto Seksi, No Stiker Aib, No Kata Kasar!",
            themeTag = "Etika Digital & Anti-Bullying",
            summary = "Di grup obrolan kelas, foto salah satu siswi yang tersandung difoto diam-diam dan hendak diedit jadi stiker ejekan dengan kata kasar.",
            coverResId = null,
            panels = listOf(
                ComicPanel(
                    speaker = "Narator",
                    roleBadge = "Di Dunia Maya Remaja",
                    text = "Dunia digital bergerak cepat dalam genggaman tangan. Sebuah foto lucu atau chat sepele bisa berujung luka mendalam jika tidak dibekali nurani dan etika...",
                    bubbleType = BubbleType.NARRATOR
                ),
                ComicPanel(
                    speaker = "Kevin",
                    roleBadge = "Anggota Grup WA",
                    text = "Hahaha liat nih foto si Rani waktu jatuh kemarin, roknya kotor! Gue buatin stiker WhatsApp trus kasih tulisan kasar ya, pasti rame nih!",
                    bubbleType = BubbleType.SPEECH_LEFT,
                    soundEffect = "*KLIK-SHARE*"
                ),
                ComicPanel(
                    speaker = "Nisa",
                    roleBadge = "Batin Nisa",
                    text = "Kasihan Rani... dia pasti nangis dan malu banget kalau stiker ejekan itu tersebar luas. Ini kan cyberbullying!",
                    bubbleType = BubbleType.THOUGHT
                ),
                ComicPanel(
                    speaker = "Keputusan Nisa",
                    roleBadge = "PILIHAN BERCABANG",
                    text = "Bagaimana respons Nisa terhadap ancaman cyberbullying di grup kelas?",
                    bubbleType = BubbleType.NARRATOR,
                    isDecisionPoint = true,
                    choiceA = ComicChoice(
                        title = "PILIHAN POSITIF: TEGUR DENGAN SANTUN & LINDUNGI TEMAN",
                        description = "Menghentikan perundungan digital dan mengingatkan bahaya UU ITE serta etika.",
                        consequenceType = ConsequenceType.POSITIVE,
                        storyResult = "Nisa mengetik di grup: 'Teman-teman, stop yuk. Nggak sopan bikin stiker aib teman apalagi pakai kata kasar. Gimana kalau kita yang diposisikan seperti Rani? Hapus ya Kevin.'\n\nHasil: Anggota grup tersadar dan Kevin meminta maaf serta menghapus stiker tersebut. Rani terselamatkan dari depresi dan suasana kelas tetap rukun harmonis.",
                        teacherGuidance = "Nasihat Bu Selvi (Guru BK):\n'Keren sekali, Nisa! Menjadi pembela kebaikan (upstander) di media sosial butuh keberanian moral. Hindari juga mengunggah foto pribadi yang seksi/terbuka karena jejak digital abadi dan rawan disalahgunakan oknum jahat!'"
                    ),
                    choiceB = ComicChoice(
                        title = "PILIHAN NEGATIF: IKUT MENERUSKAN DAN MENERTAWAKAN",
                        description = "Membiarkan bullying digital terjadi bahkan menyebarkannya lebih luas.",
                        consequenceType = ConsequenceType.NEGATIVE,
                        storyResult = "Stiker beredar ke luar sekolah. Rani merasa sangat terpukul, menangis histeris, dan menolak masuk sekolah selama seminggu karena trauma berat.\n\nKonsekuensi Buruk: Orang tua Rani membawa kasus ini ke ranah hukum atas dugaan pencemaran nama baik dan UU ITE. Pelaku pembuat dan penyebar dipanggil pihak berwajib dan reputasi sekolah tercemar.",
                        teacherGuidance = "Peringatan Bu Selvi (Guru BK):\n'Ingat pepatah modern: Jarimu adalah harimaumu! Mengirim kata kasar, menyebar foto tanpa izin, dan mengunggah konten yang tidak pantas memiliki konsekuensi hukum nyata. Bijaklah bermedia sosial!'"
                    )
                ),
                ComicPanel(
                    speaker = "Bu Selvi",
                    roleBadge = "Guru BK Sahabat",
                    text = "Gunakan ponsel pintar kalian untuk karya, prestasi, dan menebar kebaikan. Hargai privasi orang lain sebagaimana kalian ingin dihormati!",
                    bubbleType = BubbleType.TEACHER_ADVICE,
                    soundEffect = "*BIJAK BERMEDSOS*"
                )
            )
        )
    )
}
