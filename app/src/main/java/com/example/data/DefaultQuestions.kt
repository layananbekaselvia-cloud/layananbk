package com.example.data

import com.example.model.QuizQuestion

object DefaultQuestions {
    fun getList(): List<QuizQuestion> = listOf(
        QuizQuestion(
            id = 1,
            question = "Laras (14 tahun) menyukai teman sekelasnya. Pasangannya meminta untuk sering berduaan di tempat sepi sepulang sekolah dan menuntut bukti rasa sayang secara fisik. Tindakan paling bijak dan tepat yang mencerminkan batasan sehat adalah...",
            options = listOf(
                "Menuruti karena takut diputuskan dan dijauhi teman pergaulan",
                "Menolak dengan tegas, menetapkan batasan diri, dan memprioritaskan rasa hormat serta masa depan",
                "Mengiyakan sekali saja asalkan tidak ada orang lain yang melihat",
                "Menghindar tanpa memberi penjelasan apapun sama sekali"
            ),
            correctIndex = 1,
            explanation = "Hubungan sehat di usia SMP harus dilandasi rasa saling menghormati batas privasi dan tubuh. Menolak dengan asertif membuktikan kematangan emosional dan menjaga harga diri serta cita-cita.",
            category = "Pacaran Sehat & Batasan Diri"
        ),
        QuizQuestion(
            id = 2,
            question = "Rian diajak teman satu gengnya untuk mencoba 'game putar roda' di sebuah situs yang menjanjikan uang berlipat dengan modal uang jajan harian. Prinsip kritis yang wajib dipahami seorang pelajar adalah...",
            options = listOf(
                "Mencoba sedikit dulu menggunakan uang sisa SPP agar tidak penasaran",
                "Ikut bermain asalkan temannya bersedia menjamin keuntungan",
                "Menyadari bahwa itu adalah judi online yang dirancang mencandukan dan menguras finansial serta melanggar hukum",
                "Mengunduh aplikasinya tetapi hanya memainkan versi demo tanpa uang asli"
            ),
            correctIndex = 2,
            explanation = "Judi online berkedok game dirancang secara psikologis untuk menciptakan ilusi kemenangan semu dan kecanduan akut, memicu pencurian uang orang tua dan kehancuran masa depan.",
            category = "Bahaya Judi Online"
        ),
        QuizQuestion(
            id = 3,
            question = "Seorang siswa beranggapan menghisap vape rasa buah bersama teman di toilet sekolah adalah gaya hidup keren yang aman dibanding rokok tembakau. Analisis fakta kesehatan dan psikososial yang benar adalah...",
            options = listOf(
                "Vape tetap mengandung nikotin adiktif, bahan kimia karsinogenik perusak paru-paru, dan melanggar tata tertib sekolah",
                "Vape sama sekali tidak mengandung zat adiktif karena baunya manis harum",
                "Vape hanya berbahaya jika dihisap di dalam kelas, tetapi aman di luar sekolah",
                "Vape membantu konsentrasi belajar bagi siswa yang sedang stres menghadapi ujian"
            ),
            correctIndex = 0,
            explanation = "Uap vape (e-cigarette) mengandung nikotin konsentrat tinggi, logam berat, dan partikel halus yang merusak paru-paru dan memicu ketergantungan saraf di masa remaja.",
            category = "Bahaya Vape & Rokok"
        ),
        QuizQuestion(
            id = 4,
            question = "Dodi merasa bosan dengan pelajaran Matematika lalu diajak teman sebangkunya melompati pagar belakang sekolah untuk nongkrong di warung kopi. Ditinjau dari hak dan tanggung jawab pelajar, dampak jangka panjang tindakan Dodi adalah...",
            options = listOf(
                "Meningkatkan rasa solidaritas pertemanan yang positif",
                "Mengabaikan hak dirinya atas pendidikan bermutu, merusak reputasi diri, dan merugikan jerih payah pengorbanan orang tua",
                "Memberi jeda mental sejenak agar otak lebih segar saat pelajaran berikutnya",
                "Membuktikan bahwa dirinya mandiri dan tidak terikat aturan kaku sekolah"
            ),
            correctIndex = 1,
            explanation = "Pendidikan adalah hak fundamental yang diperjuangkan dengan pengorbanan orang tua. Bolos adalah bentuk pengingkaran tanggung jawab yang menciptakan kebiasaan melarikan diri dari tantangan hidup.",
            category = "Disiplin & Hak Pendidikan"
        ),
        QuizQuestion(
            id = 5,
            question = "Ketika pacar meminta kiriman foto pribadi yang terbuka/seksi melalui pesan pribadi dengan alasan 'bukti cinta rahasia', sikap protektif yang wajib diambil adalah...",
            options = listOf(
                "Mengirimkannya dengan syarat foto tersebut harus segera dihapus setelah dilihat",
                "Mengirim bagian tertentu saja yang menurutnya tidak terlalu terbuka",
                "Menolak tegas, menyimpan bukti jika ada paksaan/ancaman, dan melaporkan ke Guru BK atau orang tua",
                "Membiarkan pesan tersebut tanpa membalas namun tetap berpacaran seperti biasa"
            ),
            correctIndex = 2,
            explanation = "Foto digital sekali terkirim memiliki jejak selamanya dan sangat rentan menjadi alat pemerasan (sextortion), pencemaran nama baik, serta pelanggaran UU ITE.",
            category = "Etika Digital & Privasi"
        ),
        QuizQuestion(
            id = 6,
            question = "Di grup WhatsApp kelas 9, beberapa siswa memotret teman sekelas yang tertidur dengan pose lucu lalu menjadikannya stiker ejekan dan ditambahi kata-kata kasar. Sikap bijak anggota grup yang beretika adalah...",
            options = listOf(
                "Ikut tertawa dan mengirimkan emoji tertawa agar dianggap kompak",
                "Meneruskan stiker tersebut ke grup media sosial lain",
                "Tidak ikut menyebarkan, menegur secara santun bahwa hal itu melukai martabat teman, atau melapor ke wali kelas/BK",
                "Membalas dengan membuat stiker aib siswa pembuat stiker pertama"
            ),
            correctIndex = 2,
            explanation = "Mengubah foto teman menjadi stiker ejekan dan menyertakan umpatan adalah bentuk cyberbullying yang dapat meruntuhkan rasa percaya diri korban dan berkonsekuensi sanksi serius.",
            category = "Etika Medsos & Anti-Bullying"
        ),
        QuizQuestion(
            id = 7,
            question = "Hubungan pacaran yang sehat pada usia remaja SMP seharusnya berorientasi pada...",
            options = listOf(
                "Saling mengontrol akun media sosial pasangan dan membatasi teman lain",
                "Saling memotivasi belajar, menghormati cita-cita, serta menjaga batas pergaulan susila",
                "Menghabiskan waktu berdua setiap hari tanpa memedulikan tugas sekolah dan keluarga",
                "Saling menuntut hadiah mahal sebagai bukti pengorbanan cinta"
            ),
            correctIndex = 1,
            explanation = "Karakteristik hubungan pacaran yang sehat (healthy relationship) adalah mutual respect, tidak posesif, menjaga kehormatan diri, dan menjadi pemicu semangat berprestasi.",
            category = "Pacaran Sehat & Batasan Diri"
        ),
        QuizQuestion(
            id = 8,
            question = "Seorang siswa SMP tergiur promosi akun 'influencer' yang memamerkan barang mewah hasil kemenangan judi slot. Cara berpikir kritis paling tepat dalam mengevaluasi konten tersebut adalah...",
            options = listOf(
                "Mempercayai bahwa judi slot memang bisa membuat kaya raya dalam semalam",
                "Menyadari bahwa itu adalah skenario endorsement pemasaran terselubung dan sistem judi selalu memenangkan bandar",
                "Meminjam uang teman untuk mencoba trik yang diajarkan influencer tersebut",
                "Menganggapnya peluang investasi yang layak dicoba oleh remaja"
            ),
            correctIndex = 1,
            explanation = "Promosi judi online oleh oknum influencer adalah manipulasi algoritma dan promosi berbayar. Faktanya, algoritma judi diatur agar pemain selalu rugi secara sistemik.",
            category = "Bahaya Judi Online"
        ),
        QuizQuestion(
            id = 9,
            question = "Ketika kamu merasa emosi atau tersinggung saat berdiskusi di grup medsos, tindakan pengendalian diri digital terbaik adalah...",
            options = listOf(
                "Mengetik makian dan kata kasar dengan huruf kapital penuh",
                "Mengambil jeda (pause) menenangkan diri, tidak membalas dengan amarah, dan memilih komunikasi asertif",
                "Mengajak teman lain untuk bersama-sama menyerang akun orang tersebut (mobbing)",
                "Menyebarkan identitas pribadi (doxing) orang yang berselisih"
            ),
            correctIndex = 1,
            explanation = "Kematangan literasi digital tercermin dari kemampuan menahan diri (digital pause) sebelum mengetik respons yang berpotensi memicu konflik dan melanggar etika kesantunan.",
            category = "Etika Digital & Privasi"
        ),
        QuizQuestion(
            id = 10,
            question = "Mengapa remaja yang sering bolos sekolah berisiko tinggi terjerumus ke perilaku menyimpang lainnya (seperti tawuran, rokok/vape, dan judol)?",
            options = listOf(
                "Karena saat di luar pengawasan sekolah dan keluarga, remaja rentan terpengaruh lingkungan yang tidak terarah",
                "Karena bolos sekolah membuat siswa otomatis berteman dengan orang dewasa yang kaya",
                "Karena guru di sekolah tidak peduli jika muridnya tidak masuk kelas",
                "Karena nongkrong di luar jam pelajaran membuat wawasan siswa bertambah luas"
            ),
            correctIndex = 0,
            explanation = "Bolos sekolah memutus keterikatan positif siswa dengan lingkungan pendidikan terstruktur, membuka peluang paparan pada pergaulan berisiko tanpa filter nilai moral.",
            category = "Disiplin & Hak Pendidikan"
        ),
        QuizQuestion(
            id = 11,
            question = "Apa makna sejati dari 'Consent' (persetujuan) dan 'Batasan Pribadi' dalam interaksi antar-teman maupun lawan jenis di usia remaja?",
            options = listOf(
                "Setuju karena merasa terpaksa atau tidak enak hati menolak",
                "Setiap individu memiliki kedaulatan mutlak atas tubuh dan privasinya, dan kata 'TIDAK' harus dihormati tanpa kompromi",
                "Batasan pribadi hanya berlaku di dalam lingkungan rumah dan tidak berlaku di sekolah",
                "Persetujuan otomatis dianggap ada apabila orang tersebut diam saja"
            ),
            correctIndex = 1,
            explanation = "Prinsip batasan pribadi menegaskan bahwa keheningan bukanlah persetujuan. Setiap orang berhak merasa aman, dan penolakan harus dihormati secara mutlak.",
            category = "Pacaran Sehat & Batasan Diri"
        ),
        QuizQuestion(
            id = 12,
            question = "Banyak remaja terjerat vape karena terjebak 'Peer Pressure' (tekanan teman sebaya) dengan tuduhan 'penakut/tidak gaul'. Strategi penolakan asertif paling efektif adalah...",
            options = listOf(
                "Marah-marah lalu memukul teman yang mengajak",
                "Menyatakan prinsip tegas dengan tenang: 'Maaf, aku sayang paru-paruku dan punya target atlet/sekolah masa depan'",
                "Mencoba sekali lalu pura-pura batuk agar tidak ditawari lagi",
                "Menjual vape milik teman agar mereka berhenti"
            ),
            correctIndex = 1,
            explanation = "Asertivitas adalah keberanian mempertahankan prinsip pribadi dengan santun namun teguh, tanpa agresi fisik maupun sikap mengorbankan nilai diri demi diterima kelompok.",
            category = "Bahaya Vape & Rokok"
        ),
        QuizQuestion(
            id = 13,
            question = "Jika kamu melihat teman dekatmu menunjukkan tanda-tanda kecanduan judi online (sering pinjam uang, murung, menjual barang pribadi), langkah bantuan BK terbaik adalah...",
            options = listOf(
                "Meminjamkan uang terus-menerus agar dia tidak stres",
                "Mengejek dan membongkar aibnya di media sosial",
                "Mendampingi dengan empati dan mengajaknya berkonsultasi ke Ruang BK (Bu Selvi) untuk mendapatkan bimbingan solusi pemulihan",
                "Mendiamkan saja karena itu masalah pribadi keluarganya"
            ),
            correctIndex = 2,
            explanation = "Ruang BK bukan tempat menghakimi, melainkan sarana pemulihan dan bimbingan psikologis yang aman untuk menyelamatkan masa depan peserta didik dari jerat adiksi.",
            category = "Bahaya Judi Online"
        ),
        QuizQuestion(
            id = 14,
            question = "Dalam perspektif Undang-Undang Perlindungan Anak dan UU ITE, memproduksi atau mentransmisikan konten intim anak di bawah umur dapat berakibat...",
            options = listOf(
                "Tidak ada akibat hukum selama dilakukan dengan pacar sendiri",
                "Hanya dikenai teguran lisan oleh ketua OSIS",
                "Konsekuensi pidana serius dan jejak digital permanen yang menghancurkan masa depan",
                "Mendapatkan apresiasi popularitas di ranah digital"
            ),
            correctIndex = 2,
            explanation = "Penyebaran konten asusila atau foto sensitif di bawah umur memiliki sanksi pidana berat dalam hukum positif Indonesia dan menyebabkan trauma psikologis mendalam.",
            category = "Etika Digital & Privasi"
        ),
        QuizQuestion(
            id = 15,
            question = "Pernyataan berikut yang paling tepat merefleksikan nilai luhur seorang pelajar kelas 9 yang siap menyongsong masa depan cerah adalah...",
            options = listOf(
                "Remaja keren adalah mereka yang berani melanggar aturan dan bebas berekspresi tanpa batas",
                "Fokus utama masa SMP adalah membangun fondasi karakter, menjaga kehormatan diri, menjauhi narkoba/judol, dan giat menuntut ilmu",
                "Nilai akademik adalah segalanya bahkan jika harus menghalalkan segala cara dan merundung teman",
                "Menuruti semua tren internet yang viral tanpa perlu memikirkan etika dan norma kesopanan"
            ),
            correctIndex = 1,
            explanation = "Generasi hebat adalah generasi yang berkarakter kuat, beretika santun, menjaga harga diri, serta mampu memilih lingkungan dan pergaulan yang sehat untuk menggapai mimpi.",
            category = "Disiplin & Hak Pendidikan"
        )
    )
}
