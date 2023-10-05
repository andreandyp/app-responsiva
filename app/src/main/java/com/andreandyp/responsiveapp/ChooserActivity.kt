package com.andreandyp.responsiveapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.andreandyp.responsiveapp.compose1.BeerComposeFragmentsActivity
import com.andreandyp.responsiveapp.ui.theme.ResponsiveAppTheme

class ChooserActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val context = this

        setContent {
            ResponsiveAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.SpaceEvenly,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Button(onClick = {
                            val intent = Intent(context, BeerListActivity::class.java)
                            startActivity(intent)
                        }) {
                            Text(stringResource(id = R.string.old_option))
                        }

                        Button(onClick = {
                            val intent = Intent(context, BeerComposeFragmentsActivity::class.java)
                            startActivity(intent)
                        }) {
                            Text(stringResource(id = R.string.fragment_compose_option))
                        }
                    }
                }
            }
        }
    }
}
