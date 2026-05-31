package com.relapps.localstreaming.auth.presentation.onboarding

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocal
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import io.flutter.embedding.android.FlutterActivity;

import com.relapps.localstreaming.R
import com.relapps.localstreaming.common.presentation.sharedComponents.ObsidianButton
import com.relapps.localstreaming.ui.theme.LocalAppDimensions
import com.relapps.localstreaming.ui.theme.ObsidianBase
import com.relapps.localstreaming.ui.theme.ObsidianHighest
import com.relapps.localstreaming.ui.theme.ObsidianTheme
import com.relapps.localstreaming.ui.theme.PrimaryCoral
@Preview(showBackground = true, )
@Composable
fun PreviewOnboardingScreen() {
    OnboardingScreen(
        onNavigateToCheckers = {},
        onNavigateToLogin = {})
}


@Composable
fun OnboardingScreen(
    onNavigateToCheckers: () -> Unit,
    onNavigateToLogin: () -> Unit) {
    val dim = ObsidianTheme.dimensions
    val pagerState = rememberPagerState(pageCount = { 3 })
    val context = LocalContext.current

    Box(modifier = Modifier.fillMaxSize().background(ObsidianBase)) {
        // Background Gallery
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            Image(
                painter = painterResource(id = R.drawable.smoke_placeholder), // Replace with your gallery images
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize().drawWithContent {
                    drawContent()
                    // The "Cinema Noir" Fade to Black
                    drawRect(
                        Brush.verticalGradient(
                            colors = listOf(Color.Transparent, ObsidianBase),
                            startY = 300f
                        )
                    )
                }
            )
        }

        // Overlay Content
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(horizontal = dim.screenHorizontal)
                .padding(bottom = dim.paddingExtraLarge)
                .fillMaxWidth()
        ) {
            Text(
                text = "Step Into the\nSpotlight.",
                style = MaterialTheme.typography.displayMedium
            )

            Spacer(modifier = Modifier.height(dim.paddingMedium))

            Text(
                text = "Stream the world's most captivating stories in stunning 4K HDR. Curated cinematic excellence at your fingertips.",
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(dim.paddingExtraLarge))

            // Pager Dots
            Row(modifier = Modifier.padding(bottom = dim.paddingLarge)) {
                repeat(3) { index ->
                    val isActive = pagerState.currentPage == index
                    Box(
                        modifier = Modifier
                            .size(width = if(isActive) 24.dp else 8.dp, height = 4.dp)
                            .clip(CircleShape)
                            .background(if (isActive) PrimaryCoral else ObsidianHighest)
                    )
                    Spacer(modifier = Modifier.width(dim.paddingSmall))
                }
            }

            ObsidianButton(
                text = "Get Started",
                onClick = {onNavigateToLogin()}
            )
            Spacer(modifier = Modifier.height(dim.paddingMedium))
            ObsidianButton(
                text = "Play checkers",
                onClick = {onNavigateToCheckers()}
            )
            Spacer(modifier = Modifier.height(dim.paddingMedium))
            ObsidianButton(
                text = "Open Flutter module",
                onClick = {

                    context.startActivity(
                        FlutterActivity
                            .withCachedEngine("warm_flutter_engine")
                            .build(context)
                    )
                }
            )
            Spacer(modifier = Modifier.height(dim.paddingMedium))
        }
    }
}