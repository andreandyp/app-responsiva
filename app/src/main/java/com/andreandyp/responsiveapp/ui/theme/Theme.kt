package com.andreandyp.responsiveapp.ui.theme

import androidx.compose.runtime.Composable
import com.google.android.material.composethemeadapter.MdcTheme

@Composable
fun ResponsiveAppTheme(
    content: @Composable () -> Unit
) {
    MdcTheme(
        content = content
    )
}
