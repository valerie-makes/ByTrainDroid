package co.zimly.bytrain

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

interface SheetManager {
    fun present(content: @Composable ColumnScope.() -> Unit)
    fun collapse()
}

val LocalSheetManager = compositionLocalOf<SheetManager> { error("No SheetManager found!") }

// Store sheet content in an object outside the composable so that it persists across
// screen rotations. (`rememberSaveable` doesn't work due to the complex data type.)
// The property should ideally be stored with `MutableLiveData` so that the view
// can observe when it changes, but an internal compiler bug prevents doing this.
private object SheetContent {
    var content: @Composable ColumnScope.() -> Unit = {}
}

@ExperimentalMaterialApi
@Composable
fun SheetProvider(content: @Composable () -> Unit) {
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberBottomSheetScaffoldState()
    val sheetState = scaffoldState.bottomSheetState
    val sheetContent = SheetContent.content
    var isExpanding by remember { mutableStateOf(false) }

    val sheetManager by remember {
        derivedStateOf {
            object : SheetManager {
                override fun present(content: @Composable ColumnScope.() -> Unit) {
                    isExpanding = true
                    SheetContent.content = content
                    scope.launch { sheetState.expand() }
                }

                override fun collapse() {
                    scope.launch { sheetState.collapse() }
                }
            }
        }
    }

    if (sheetState.isExpanded) {
        isExpanding = false
    }

    // free up memory once the sheet is collapsed
    if (sheetState.isCollapsed && !isExpanding) {
        SheetContent.content = {}
    }

    // `sheetState.isExpanded` only updates when the sheet is finished animating.
    // `blurContent` is similar to `sheetState.isExpanded` but updates as soon as the
    // animation starts, in order to animate the background content simultaneously.
    val blurContent = when (sheetState.direction) {
        // sheet is moving down
        1f -> false
        // sheet is moving up
        -1f -> true
        // sheet has reached the top/bottom
        else -> sheetState.isExpanded
    }

    // 0.875 = 7/8
    val blurAmount by animateFloatAsState(if (blurContent) 0.875f else 1f)

    BackHandler(sheetState.isExpanded) {
        scope.launch {
            sheetState.collapse()
        }
    }

    BottomSheetScaffold(
        sheetContent = { Column(Modifier.fillMaxHeight(), content = sheetContent) },
        scaffoldState = scaffoldState,
        sheetPeekHeight = 0.dp,
    ) { innerPadding ->
        Box(
            Modifier
                .background(
                    if (isSystemInDarkTheme()) {
                        MaterialTheme.colors.background
                    } else {
                        MaterialTheme.colors.primaryVariant
                    }
                )
                .scale(blurAmount)
                .alpha(blurAmount)
                .padding(innerPadding)
        ) {
            CompositionLocalProvider(LocalSheetManager provides sheetManager) {
                content()
            }
        }
    }
}
