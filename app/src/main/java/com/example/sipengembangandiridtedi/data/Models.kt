package com.example.sipengembangandiridtedi.data

enum class StatusPengajuan(val label: String) {
    DIPERIKSA("Diperiksa"),
    REVISI("Revisi"),
    DISETUJUI("Disetujui"),
    DIAJUKAN("Diajukan"),
    SELESAI("Selesai"),
    DITOLAK("Ditolak");

    companion object {
        /** Mapper status dari API backend. */
        fun fromApi(v: String?): StatusPengajuan = when (v) {
            "ditinjau_sekretaris", "ditinjau_kepala_departemen" -> DIPERIKSA
            "revisi" -> REVISI
            "disetujui" -> DISETUJUI
            "diajukan_ke_fakultas" -> DIAJUKAN
            "selesai" -> SELESAI
            "ditolak" -> DITOLAK
            else -> DIPERIKSA
        }
    }
}

data class Profil(val nama: String, val prodi: String, val role: String)

data class DanaInfo(val total: Long, val terpakai: Long) {
    val sisa: Long get() = total - terpakai
    val persenTerpakai: Int get() = if (total == 0L) 0 else ((terpakai * 100) / total).toInt()
    val persenSisa: Int get() = 100 - persenTerpakai
}

data class Pengajuan(
    val id: Int,
    val judul: String,
    val kategori: String,
    val tanggal: String,            // contoh: "12 September 2026"
    val nominalDiajukan: Long,
    val nominalDisetujui: Long?,
    val deskripsi: String,
    val status: StatusPengajuan
) {
    /** "12 September 2026" -> "12 Sep 2026" */
    val tanggalSingkat: String
        get() {
            val p = tanggal.split(" ")
            return if (p.size == 3) "${p[0]} ${p[1].take(3)} ${p[2]}" else tanggal
        }
}

data class JenisKegiatan(
    val nama: String,
    val deskripsi: String,
    val dokumen: List<String>
)

data class FaqItem(val tanya: String, val jawab: String)

data class Notifikasi(
    val id: Int,
    val judul: String,
    val pesan: String,
    val waktu: String,
    val dibaca: Boolean,
    val pengajuanId: Int? = null
)

/** Data dummy sesuai mockup. TODO API: ganti dengan repository yang memanggil REST API. */
object FakeData {
    val profil = Profil(nama = "Ahmad Subakti", prodi = "Teknologi Rekayasa Perangkat Lunak", role = "Dosen")
    val dana = DanaInfo(total = 10_000_000, terpakai = 4_000_000)
    const val pengajuanAktif = 4
    const val notifBelumDibaca = 2

    private val dokumenUmum = listOf(
        "Surat Tugas",
        "Brosur / informasi biaya yang dipublikasikan oleh penyelenggaraan kegiatan",
        "Kwitansi dari penyelenggara"
    )
    private const val deskUmum = "Program sertifikasi kompetensi yang sesuai dengan bidang keahlian pengusul."

    val jenisKegiatan = listOf(
        "Program sertifikasi profesi keinsinyuran",
        "Program sertifikasi kompetensi",
        "Keanggotaan organisasi profesi",
        "Tes kemampuan bahasa Inggris TOEFL iBT/IELTS/TOEIC",
        "Pembelian perangkat lunak atau tools sesuai bidang",
        "Program pembelajaran daring jarak jauh atau MOOC",
        "Pembelian buku elektronik sesuai bidang",
        "Program kunjungan ke mitra/industri",
        "Program keikutsertaan kegiatan konferensi/seminar"
    ).map { JenisKegiatan(it, deskUmum, dokumenUmum) }

    val riwayat = listOf(
        Pengajuan(1, "Program Sertifikasi Kompetensi", "Program sertifikasi kompetensi", "12 September 2026", 2_500_000, null, "Program Sertifikasi Kompetensi adalah ajang yang...", StatusPengajuan.DIPERIKSA),
        Pengajuan(2, "Program pembelajaran jarak jauh atau MOOC", "Program pembelajaran daring jarak jauh atau MOOC", "12 September 2026", 2_500_000, 2_000_000, "Pembelajaran daring jarak jauh...", StatusPengajuan.DISETUJUI),
        Pengajuan(3, "Program Sertifikasi Kompetensi", "Program sertifikasi kompetensi", "12 September 2026", 2_500_000, null, "Program Sertifikasi Kompetensi adalah ajang yang...", StatusPengajuan.DIAJUKAN),
        Pengajuan(4, "Program pembelajaran jarak jauh atau MOOC", "Program pembelajaran daring jarak jauh atau MOOC", "12 September 2026", 2_500_000, null, "Pembelajaran daring jarak jauh...", StatusPengajuan.DITOLAK)
    )

    val draf = List(4) {
        Pengajuan(100 + it, "Program Sertifikasi Kompetensi", "Program sertifikasi kompetensi", "12 September 2026", 2_500_000, null, "Program Sertifikasi Kompetensi adalah ajang yang...", StatusPengajuan.DIPERIKSA)
    }

    val faq = List(8) {
        FaqItem(
            "Siapa yang dapat mengajukan dana bantuan pengembangan diri?",
            "Dosen aktif Departemen Teknik Elektro dan Informatika, Sekolah Vokasi, Universitas Gadjah Mada."
        )
    }

    val notifikasi = listOf(
        Notifikasi(1, "Pengajuan Disetujui", "Pengajuan dana bantuan sertifikasi telah disetujui.", "2 jam yang lalu", false, 2),
        Notifikasi(2, "Catatan Revisi", "Mohon perbaiki dokumen SPJ pada pengajuan #1.", "1 hari yang lalu", false, 1),
        Notifikasi(3, "Pengajuan Diterima", "Pengajuan baru Anda telah diterima dan sedang diperiksa.", "3 hari yang lalu", true, 3)
    )

    fun detail(id: Int): Pengajuan? = (riwayat + draf).firstOrNull { it.id == id }
}
