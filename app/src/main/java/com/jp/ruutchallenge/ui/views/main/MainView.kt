package com.jp.ruutchallenge.ui.views.main

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jp.ruutchallenge.R
import com.jp.ruutchallenge.model.MetaData
import com.jp.ruutchallenge.model.SerieInfo
import com.jp.ruutchallenge.extensions.getTimeFromDate
import com.jp.ruutchallenge.ui.views.comun.TopBarAV

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainView(
    goToLogin: () -> Unit,
    goToProfile: () -> Unit,
) {
    val viewModel: MainViewModel = hiltViewModel()
    val metadata by viewModel.metaData.collectAsState()
    val serieInfo by viewModel.serieInfo.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val isInit by viewModel.init.collectAsState()
    val context = LocalContext.current
    LaunchedEffect(isInit) {
        viewModel.isUserLogged(goToLogin)
    }
    viewModel.getUser()
    viewModel.getData()

    Scaffold(
        topBar = {
            TopBarAV(
                title = stringResource(id = R.string.alpha_vantage),
                showProfileAction = true,
                onProfileActionClick = {
                    goToProfile()
                }
            )
        },
        content = { padding ->
            if (serieInfo.isEmpty()) return@Scaffold
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    Text(text = metadata.information,)
                    Spacer(modifier = Modifier.padding(top = 16.dp))
                    Metadata(metadata)
                    Spacer(modifier = Modifier.padding(top = 8.dp))
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(id = R.string.last_refreshed) + metadata.lastRefreshed,
                        textAlign = TextAlign.Right,
                        style = MaterialTheme.typography.bodySmall,
                    )
                    Spacer(modifier = Modifier.padding(top = 16.dp))
                    SerieHeaders()
                    SeriesScroll(serieInfo)
                }
            }
        }
    )

    LaunchedEffect(errorMessage) {
        if (errorMessage.isNotEmpty()) {
            Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
        }
    }
}

@Composable
private fun Metadata(data: MetaData) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier
                        .height(100.dp)
                        .fillMaxWidth(0.2f),
                    painter = painterResource(id = R.drawable.ic_money),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp)
                ) {
                    Text(
                        text = data.symbol,
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Bold,
                    )
                    Text(
                        text = "${data.outputSize} - ${data.timezone}",
                        style = MaterialTheme.typography.headlineSmall,
                    )
                }
            }
        }
    }
}

@Composable
private fun SeriesScroll(series: List<SerieInfo>) {
    ElevatedCard(
        modifier = Modifier.fillMaxSize(),
    ) {
        LazyColumn {
            items(series) { serie ->
                SerieRow(serie = serie)
            }
        }
    }
}

@Composable
private fun SerieHeaders() {
    Row(
        modifier = Modifier.height(50.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        Row (
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Text(
                text = stringResource(id = R.string.header_time),
                fontWeight = FontWeight.Bold,
            )
        }
        Row (
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Text(
                text = stringResource(id = R.string.header_open),
                fontWeight = FontWeight.Bold,
            )
        }
        Row (
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Text(
                text = stringResource(id = R.string.header_high),
                fontWeight = FontWeight.Bold,
            )
        }
        Row (
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Text(
                text = stringResource(id = R.string.header_low),
                fontWeight = FontWeight.Bold,
            )
        }
    }
}

@Composable
private fun SerieRow(serie: SerieInfo) {
    Row(
        modifier = Modifier.height(50.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        Row (
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            CellDate(serie.date.getTimeFromDate())
        }
        Row (
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Cell(serie.high)
        }
        Row (
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Cell(serie.low)
        }
        Row (
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Cell(serie.close)
        }
    }
}

@Composable
private fun CellDate(date: String) {
    Text(
        text = date,
        style = MaterialTheme.typography.bodyLarge,
        fontWeight = FontWeight.Bold,
    )
}

@Composable
private fun Cell(pair: Pair<Double, Byte>) {
    val text = pair.first.toString()
    val value = pair.second
    val color = when (value) {
        (-1).toByte() -> {
            Color.Red
        }
        (1).toByte() -> {
            Color.Green
        }
        else -> {
            Color.Black
        }
    }
    Text(
        text = text,
        style = MaterialTheme.typography.bodyLarge,
        color = color
    )
    Image(
        modifier = Modifier
            .height(24.dp)
            .width(24.dp)
            .padding(start = 4.dp),
        painter = painterResource(id =
            when (value) {
                (-1).toByte() -> {
                    R.drawable.ic_down
                }
                (1).toByte() -> {
                    R.drawable.ic_up
                }
                else -> {
                    R.drawable.ic_neutral
                }
            }
        ),
        colorFilter = ColorFilter.tint(color),
        contentDescription = null,
        contentScale = ContentScale.Fit,
    )
}