# EpicBooks

EpicBooks adalah aplikasi mobile yang menyediakan layanan pencarian dan informasi mengenai berbagai buku dari berbagai genre dan penulis. Aplikasi ini didesain untuk memudahkan pengguna dalam mencari dan menemukan buku-buku yang mereka inginkan. Dengan fitur pencarian, pengguna dapat dengan mudah menemukan buku yang mereka cari. Selain itu, EpicBooks juga menyediakan informasi lengkap mengenai setiap buku, termasuk detail seperti penulis, penerbit, tanggal terbit, deskripsi, dan jumlah halaman. Pengguna juga dapat melihat preview dari buku yang mereka minati.

# Fitur

1. Login dan Registrasi: Untuk pertama kali menggunakan aplikasi, pengguna dapat melakukan registrasi dengan membuat akun baru. Mereka perlu memasukkan username dan password, yang akan digunakan untuk login ke dalam aplikasi di masa mendatang. Setelah registrasi berhasil, informasi akun pengguna akan disimpan menggunakan SharedPreferences. Pengguna dapat masuk ke dalam aplikasi menggunakan informasi login yang telah mereka daftarkan sebelumnya.
2. Preview: Pengguna dapat melihat preview dari buku yang mereka minati.
3. Pencarian Buku: Pengguna dapat dengan mudah mencari buku berdasarkan judul bukunya. Fitur pencarian ini memungkinkan pengguna menemukan buku yang mereka inginkan dengan cepat dan akurat.
4. Profile: Profil Pengguna merupakan bagian dari EpicBooks yang memungkinkan pengguna untuk melihat informasi pribadi mereka. Di dalamnya termasuk nama pengguna, foto profil, dan informasi genre buku kesukaan.
5. Logout: Fitur Logout pada EpicBooks adalah fitur yang memungkinkan pengguna untuk keluar dari akun mereka.

# Penggunaan

1. Registrasi: Membuat akun baru dengan mengisi username dan password.
2. Login: Masuk ke aplikasi menggunakan akun yang telah dibuat pada saat registrasi.
3. Akses Buku: Dengan menekan judul buku, pengguna dapat melihat detail buku termasuk judul, penerbit, halaman, tanggal terbit, dan deskripsi. Pengguna juga dapat melihat preview buku yang mereka pilih.
4. Pencarian: Masuk ke halaman pencarian untuk mencari judul buku.
5. Logout: Menekan tombol logout dibagian kanan atas toolbar.

# Teknologi yang digunakan

1. Android Studio: EpicBooks dikembangkan menggunakan Android Studio
2. Retrofit: Retrofit digunakan dalam EpicBooks untuk mengelola permintaan jaringan ke server backend. Ini digunakan untuk pengambilan API.
3. SharedPreferences: EpicBooks menggunakan SharedPreferences untuk menyimpan data pengguna secara lokal pada perangkat Android. SharedPreferences digunakan untuk menyimpan informasi seperti data login pengguna dan data registrasi.

# API

https://www.googleapis.com/books/v1/volumes?q=search+terms

