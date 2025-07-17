package dev.himanshu.letsnavigate

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ListScreen(
    modifier: Modifier = Modifier, onClick: (String) -> Unit
) {
    LazyColumn(modifier.fillMaxSize()) {

        repeat(50) {
            item {
                Text(
                    modifier = Modifier
                        .clickable {
                            onClick("This is item number ${it}")
                        }
                        .background(if (it % 2 == 0) Color.Red else Color.Green)
                        .padding(12.dp)
                        .fillMaxWidth(),
                    text = "This is item number ${it}"
                )
            }
        }

    }


}