package sk.devprog.firmy.ui.screen.detail.widget

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.GoogleMapOptions
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import sk.devprog.firmy.R
import sk.devprog.firmy.data.model.MapCoordinates
import sk.devprog.firmy.ui.screen.detail.model.CompanyDetailItemUiModel
import sk.devprog.firmy.ui.screen.detail.model.CompanyDetailSectionUiModel
import sk.devprog.firmy.ui.theme.AppTheme
import sk.devprog.firmy.util.toTextResource

@Composable
fun BasicCompanyInfoTab(
    items: List<CompanyDetailSectionUiModel>,
    name: String,
    coordinates: MapCoordinates?
) {
    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        MapContent(coordinates)

        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.padding(16.dp)
        ) {
            items.forEach { section ->
                DetailSection(section)
            }
        }

        ExternalLink(
            title = stringResource(id = R.string.detail_link_search_debtor),
            url = "https://dlznik.zoznam.sk/rychle-vyhladavanie?q=${Uri.encode(name)}"
        )
        ExternalLink(
            title = stringResource(id = R.string.detail_link_search_google),
            url = "https://google.com/search?q=${Uri.encode("\"$name\"")}",
            modifier = Modifier.padding(bottom = 16.dp)
        )
    }
}

@Composable
private fun ExternalLink(title: String, url: String, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    TextButton(modifier = modifier, onClick = {
        context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }) {
        Icon(
            painter = painterResource(id = R.drawable.ic_outline_open_in_new),
            modifier = Modifier.size(ButtonDefaults.IconSize),
            contentDescription = null
        )
        Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
        Text(title)
    }
}

@Composable
private fun MapContent(coordinates: MapCoordinates?) {
    if (coordinates != null) {
        val latLng = remember { LatLng(coordinates.latitude, coordinates.longitude) }

        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(latLng, 15f)
        }

        var loaded by remember { mutableStateOf(false) }
        val alphaAnim by animateFloatAsState(targetValue = if (loaded) 1f else 0f)
        GoogleMap(
            cameraPositionState = cameraPositionState,
            googleMapOptionsFactory = { GoogleMapOptions().liteMode(true) },
            onMapLoaded = { loaded = true },
            modifier = Modifier
                .fillMaxWidth()
                .alpha(alphaAnim)
                .height(200.dp)
        ) {
            Marker(
                state = rememberMarkerState(position = latLng)
            )
        }
    }
}

@Preview
@Composable
private fun BasicCompanyInfoTabPreview() {
    val items = listOf(
        CompanyDetailSectionUiModel(
            "Title".toTextResource(),
            listOf(CompanyDetailItemUiModel(AnnotatedString("Test")))
        )
    )
    AppTheme {
        BasicCompanyInfoTab(
            items = items,
            coordinates = MapCoordinates(0.0, 0.0),
            name = "",
        )
    }
}
