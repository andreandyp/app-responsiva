package com.andreandyp.responsiveapp.ui.utils

import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Preview
import com.andreandyp.responsiveapp.repository.models.Beer

object ComposePreviews {
    val previewBeer = Beer(
        id = 0,
        name = "Berliner Weisse With yuzu - B-sides",
        tagline = "An Epic Fusion Of Old Belgian, American New Wave, And Scotch Whisky.",
        description = "A light, crisp and bitter IPA brewed with English and Americans hops.",
        firstBrewed = "09/2007",
        imageUrl = "",
        foodPairing = "Spicy chicken tikka masala\nGrilled chicken quesadilla"
    )
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
annotation class AllThemesPreviews
