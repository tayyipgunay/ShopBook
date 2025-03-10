package com.tayyipgunay.screens

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import com.tayyipgunay.model.Item
import com.tayyipgunay.shopbook.R

@Composable
fun DetailsScreen(item: Item, deleteFunction: () -> Unit) {

    Box(
        modifier = Modifier.fillMaxSize()
            .background(color = MaterialTheme.colorScheme.primaryContainer),
        contentAlignment = Alignment.Center
    )
   {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            val imageBitmap = item?.itemImage?.let { byteArray ->
                BitmapFactory.decodeByteArray(byteArray,0,byteArray.size)?.asImageBitmap()
            }
            Image(bitmap = imageBitmap?: ImageBitmap.imageResource(R.drawable.img),
                contentDescription = "Item Image")


            Text(
                text = item.itemName, modifier = Modifier.padding(2.dp),
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                style = MaterialTheme.typography.displaySmall
            )



            Text(
                text = item.storename!!, modifier = Modifier.padding(2.dp),
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                style = MaterialTheme.typography.displaySmall
            )
            Text(
                text = item.itemPrice!!, modifier = Modifier.padding(2.dp),
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                style = MaterialTheme.typography.displaySmall
            )
            Button(onClick = {
                   deleteFunction()
            }
            ) {
                Text(text = "Delete")
            }


        }
    }


}