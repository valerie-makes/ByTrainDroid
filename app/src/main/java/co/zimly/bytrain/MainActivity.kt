package co.zimly.bytrain

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import co.zimly.bytrain.theme.ByTrainTheme

class MainActivity : ComponentActivity() {
    @ExperimentalAnimationApi
    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ByTrainTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    ByTrainApp()
                }
            }
        }
    }
}

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun ByTrainApp() {
    SheetProvider {
        AppScaffold()
    }
}
