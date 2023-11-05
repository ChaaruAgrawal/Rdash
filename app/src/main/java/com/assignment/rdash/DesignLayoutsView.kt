package com.assignment.rdash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.assignment.rdash.data.DesignLayout
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

@Composable
fun DesignLayoutsView(designLayoutsViewModel: DesignLayoutsViewModel = hiltViewModel()) {
    val designLayoutsList = designLayoutsViewModel.designLayouts.collectAsState().value
    Column(modifier = Modifier
        .background(Color.White)) {
        TopBar()
        Column(modifier = Modifier.wrapContentHeight().padding(16.dp)) {
            SectionView("2D Layout/Adaptation", designLayoutsList.filter { it.section == "2D" })
            SectionView("3D Layout/Adaptation", designLayoutsList.filter { it.section == "3D" })
            SectionView("Production Files/ Artworks", designLayoutsList.filter { it.section == "PROD" })
        }
    }
}

@Composable
fun TopBar() {
    Column(modifier = Modifier.wrapContentHeight().fillMaxWidth().padding(16.dp)) {
        Text(text = "Design Layouts", fontWeight = FontWeight.Bold, fontSize = TextUnit(14f, TextUnitType.Sp))
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Text(text = "CLIENT ", fontSize = TextUnit(12f, TextUnitType.Sp))
            Text(text = "Bridgestone", fontWeight = FontWeight.W500, fontSize = TextUnit(12f, TextUnitType.Sp))
            Text(text = " | ", fontSize = TextUnit(10f, TextUnitType.Sp))
            Text(text = "JOB ID ", fontSize = TextUnit(12f, TextUnitType.Sp))
            Text(text = "BRID1337", fontWeight = FontWeight.W500, fontSize = TextUnit(12f, TextUnitType.Sp))
        }
    }
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(4.dp)
        .background(
            brush = Brush.verticalGradient(
                colors = listOf(
                    Color.Black.copy(alpha = 0.1f),
                    Color.Transparent,
                )
            )
        )
    )
}

@Composable
fun SectionView(
    sectionTitle: String,
    designLayoutsList: List<DesignLayout>
) {
    val collapsedState = remember { mutableStateOf(false) }
    Column {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Row(horizontalArrangement = Arrangement.spacedBy(2.dp), verticalAlignment = Alignment.Bottom) {
                Text(
                    text = sectionTitle,
                    fontSize = TextUnit(16f, TextUnitType.Sp),
                    fontWeight = FontWeight.ExtraBold
                )
                Surface(shape = RoundedCornerShape(4.dp), color = Color(0xFFEBEBEB)) {
                    Text(text = "${designLayoutsList.size} Files", fontSize = TextUnit(12f, TextUnitType.Sp), color = Color(0xFF3D3D52), modifier = Modifier.padding(2.dp), fontWeight = FontWeight.Bold)
                }
            }
            Icon(
                painter =
                if (collapsedState.value) painterResource(id = R.drawable.collpased_arrow)
                else painterResource(id = R.drawable.expanded_arrow),
                contentDescription = "expandSection",
                modifier = Modifier
                    .width(16.dp)
                    .height(16.dp)
                    .clickable { collapsedState.value = collapsedState.value.not() }
            )
        }
        if (collapsedState.value.not())
            LazyColumn(modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(designLayoutsList) {
                    DesignLayoutListItem(designLayout = it)
                }
            }
    }
}

@Composable
fun DesignLayoutListItem(designLayout: DesignLayout) {
    Row(
        modifier = Modifier
            .border(width = 1.dp, color = Color.LightGray, shape = RoundedCornerShape(7.dp))
            .background(Color.White)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(painter = painterResource(id = if (designLayout.type == "DOC") R.drawable.pdf_icon else R.drawable.dwg_icon), contentDescription = "fileType", modifier = Modifier.padding(12.dp))
            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                Text(text = designLayout.name, fontWeight = FontWeight.Bold, fontSize = TextUnit(14f, TextUnitType.Sp))
                Row {
                    Surface(shape = RoundedCornerShape(4.dp), color = Color(0xFFFFE3E4)) {
                        Text(text = "V${designLayout.version}", fontSize = TextUnit(8f, TextUnitType.Sp), color = Color.Red, modifier = Modifier.padding(2.dp), fontWeight = FontWeight.Bold)
                    }
                    Text(text = "Uploaded On: ${convertDateTime(designLayout.uploaded_at)}", color = Color(0xFF667085), fontSize = TextUnit(10f, TextUnitType.Sp), modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp))
                }
            }
        }
        Icon(painter = painterResource(id = R.drawable.three_dots_icon), contentDescription = "designLayoutDetails", modifier = Modifier.padding(12.dp))
    }
}

fun convertDateTime(inputDateTime: String): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSXXX", Locale.getDefault())
    val outputFormat = SimpleDateFormat("d MMM, yy h:mma", Locale.getDefault())

    val date = inputFormat.parse(inputDateTime)
    outputFormat.timeZone = TimeZone.getTimeZone("GMT")

    return outputFormat.format(date)
}