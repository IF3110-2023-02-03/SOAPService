# SOAPService

## Deskripsi

Layanan web SOAP ini menyediakan fungsi terkait operasi media sosial, termasuk memelihara hubungan pengikut dan pencatatan aksi sistem.

## Skema Basis Data

Layanan web menggunakan tabel basis data berikut:

- `following`: Menyimpan hubungan pengikut antar pengguna.
  - `creatorID` (VARCHAR(50)): Pengenal unik untuk pengguna yang diikuti.
  - `followerID` (VARCHAR(50)): Pengenal unik untuk pengguna pengikut.
  - `creatorName` (VARCHAR(100)): Nama pengguna yang diikuti.
  - `followerName` (VARCHAR(100)): Nama pengikut.
  - `creatorUsername` (VARCHAR(50)): Nama pengguna yang diikuti.
  - `followerUsername` (VARCHAR(50)): Nama pengguna pengikut.
  - `status` (VARCHAR(10)): Status dari hubungan pengikut (misalnya, DISETUJUI, MENUNGGU).

- `log`: Mencatat aksi sistem untuk tujuan audit.
  - `ID` (INT): Pengenal unik yang bertambah otomatis untuk setiap entri log.
  - `description` (VARCHAR(256)): Deskripsi dari aksi yang dilakukan.
  - `IP` (VARCHAR(50)): Alamat IP dari mana permintaan dibuat.
  - `endpoint` (VARCHAR(50)): Endpoint yang diakses.
  - `requestedAt` (DATETIME): Tanggal dan waktu ketika permintaan dibuat.

## Endpoint API

- `requestFollow`: Meminta untuk mengikuti pengguna.
- `confirmFollow`: Mengkonfirmasi permintaan pengikut.
- `getFollowersByID`: Mendapatkan pengikut berdasarkan ID pengguna.
- `getFollowersCountByID`: Mendapatkan jumlah pengikut berdasarkan ID pengguna.
- `getFollowingsByID`: Mendapatkan daftar pengguna yang diikuti berdasarkan ID pengikut.
- `getFollowingsCountByID`: Mendapatkan jumlah pengguna yang diikuti berdasarkan ID pengikut.
- `getPendingFollowingsByID`: Mendapatkan daftar permintaan mengikuti yang tertunda berdasarkan ID pengguna.
- `getPendingFollowingsCountByID`: Mendapatkan jumlah permintaan mengikuti yang tertunda berdasarkan ID pengguna.
- `isFollowed`: Memeriksa apakah seorang pengguna telah mengikuti pengguna lain.
- `getContent`: Mendapatkan konten berdasarkan ID pengikut.
- `getContentCreators`: Mendapatkan pembuat konten berdasarkan filter dan ID.
- `updateFollowerUsername`: Memperbarui nama pengguna pengikut.
- `updateCreatorUsername`: Memperbarui nama pengguna pembuat konten.
- `updateFollowerName`: Memperbarui nama pengikut.
- `updateCreatorName`: Memperbarui nama pembuat konten.

---

| NIM       | Task                                        |
|-----------|---------------------------------------------|
| 13521058  | Semua berkaitan dengan update akun pengguna |
| 13521065  | Semua berkaitan dengan following pengguna   |
| 13521162  | Semua berkaitan dengan content              |
