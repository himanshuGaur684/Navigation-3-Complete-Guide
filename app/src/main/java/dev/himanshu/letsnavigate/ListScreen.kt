package dev.himanshu.letsnavigate

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ListScreen(modifier: Modifier = Modifier, onClick: (String) -> Unit) {
    LazyColumn {
        repeat(10) {
            item {
                Text(
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .background(color = if (it % 2 == 0) Color.Yellow else Color.Green)
                        .padding(12.dp)
                        .fillMaxWidth()
                        .clickable {
                            onClick("This is item $it")
                        }, text = "This is item $it"
                )
            }
        }
    }
}