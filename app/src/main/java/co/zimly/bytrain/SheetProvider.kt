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
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch

typealias SheetContent = @Composable ColumnScope.(SheetController) -> Unit

interface SheetController {
    fun present(content: SheetContent)
    fun collapse()

    @Composable
    fun BackHandler()
}

val LocalSheetController =
    compositionLocalOf<SheetController> { error("No SheetController found!") }

// Store sheet content in an object outside the composable so that it persists across
// screen rotations. (`rememberSaveable` doesn't work due to the complex data type.)
// The property should ideally be stored with `MutableLiveData` so that the view
// can observe when it changes, but an internal compiler bug prevents doing this.
private object SheetContentWrapper {
    var value: SheetContent = {}
}

@ExperimentalMaterialApi
@Composable
fun SheetProvider(content: @Composable () -> Unit) {
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberBottomSheetScaffoldState()
    val sheetState = scaffoldState.bottomSheetState
    val sheetContent = SheetContentWrapper.value

    // Used to prevent clearing the sheet while it's expanding but not fully
    // expanded, and to enable pressing the back button during this period.
    var isExpanding by remember { mutableStateOf(false) }

    // Used once a tap-initiated collapse has already begun:
    // - to prevent interruption from a gesture
    // - to restore the original back handler
    var isCollapsingByTap by remember { mutableStateOf(false) }

    val sheetController by remember(sheetState, isExpanding, isCollapsingByTap) {
        derivedStateOf {
            object : SheetController {
                override fun present(content: SheetContent) {
                    isExpanding = true
                    SheetContentWrapper.value = content

                    scope.launch {
                        try {
                            sheetState.expand()
                        } catch (e: CancellationException) {
                            isExpanding = false
                        }
                    }
                }

                override fun collapse() {
                    // Ignore if already collapsing.
                    if (sheetState.direction != 1f) {
                        isCollapsingByTap = true
                        scope.launch { sheetState.collapse() }
                    }
                }

                @Composable
                override fun BackHandler() {
                    // Enable when expanding/expanded + the sheet isn't already collapsing by tap.
                    // If the user is swiping away sheet with a gesture but it's not yet at the
                    // bottom, this back handler should remain enabled. This is to prevent going
                    // back to a previous page before the sheet is actually collapsed.
                    BackHandler(
                        enabled = (isExpanding || sheetState.isExpanded) && !isCollapsingByTap,
                        onBack = ::collapse,
                    )
                }
            }
        }
    }

    if (sheetState.isExpanded) {
        isExpanding = false
    } else {
        isCollapsingByTap = false
    }

    // Free up memory once the sheet is collapsed. Check for both `direction`
    // and `isCollapsed` in case of a collapse during initial expansion.
    if (!isExpanding && sheetState.isCollapsed && sheetState.direction == 0f) {
        SheetContentWrapper.value = {}
    }

    // `sheetState.isExpanded` only updates when the sheet is finished animating.
    // `blurContent` is similar to `sheetState.isExpanded` but updates as soon as the
    // animation starts, in order to animate the background content simultaneously.
    val blurContent = when (sheetState.direction) {
        // The sheet is moving down.
        1f -> false
        // The sheet is moving up, or may be moving down if collapsed during initial
        // expansion. In case of this, use `isExpanding` to determine whether to blur.
        -1f -> isExpanding
        // The sheet has stopped moving.
        else -> sheetState.isExpanded
    }

    // 0.875 = 7/8
    val blurAmount by animateFloatAsState(if (blurContent) 0.875f else 1f)

    BottomSheetScaffold(
        sheetContent = {
            Column(
                Modifier.fillMaxHeight(),
                content = { sheetContent(sheetController) })
        },
        scaffoldState = scaffoldState,
        sheetPeekHeight = 0.dp,
        // Prevent gesture dismissal during expansion and collapsing by tap.
        sheetGesturesEnabled = sheetState.isExpanded && !isCollapsingByTap,
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
            CompositionLocalProvider(LocalSheetController provides sheetController) {
                content()
            }
        }
    }
}
