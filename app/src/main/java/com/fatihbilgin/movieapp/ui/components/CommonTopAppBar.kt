package com.fatihbilgin.movieapp.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import com.fatihbilgin.movieapp.R
import com.fatihbilgin.movieapp.ui.theme.BackGroundColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommonTopAppBar(
    title: String, // Üst çubuk başlığı
    navController: NavController, // Navigasyon işlemleri için NavController
    onBackClick: () -> Unit = { navController.popBackStack() }, // Geri tuşuna basıldığında çalışacak varsayılan işlem
    actions: @Composable (RowScope.() -> Unit)? = null, // Üst çubuğa eklenebilecek opsiyonel aksiyonlar (butonlar vb.)
    backgroundColor: Color = BackGroundColor, // Üst çubuğun arka plan rengi
    isTitleCentered: Boolean = true // Başlık ortalanacak mı?
) {
    TopAppBar(
        title = {
            // Başlık ortalanmış mı kontrol edilir
            if (isTitleCentered) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center // Başlık ortalanır
                ) {
                    Text(
                        text = title, // Başlık metni
                        color = Color.White, // Metin rengi
                        fontWeight = FontWeight.Bold // Metin kalınlığı
                    )
                }
            } else {
                // Başlık sola hizalanır
                Text(
                    text = title,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        },
        navigationIcon = {
            // Geri butonu
            IconButton(onClick = onBackClick) { // Geri butonuna tıklandığında çalışacak işlem
                Icon(
                    painter = painterResource(id = R.drawable.back), // Geri simgesi
                    contentDescription = "Back", // Erişilebilirlik açıklaması
                    tint = Color.White // Simgenin rengi
                )
            }
        },
        actions = {
            // Üst çubuğa eklenebilecek aksiyonlar (örneğin, ikonlar veya butonlar)
            actions?.invoke(this)
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = backgroundColor) // Üst çubuğun arka plan rengi
    )
}