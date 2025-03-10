package com.tayyipgunay.screens

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import coil.compose.rememberAsyncImagePainter
import com.tayyipgunay.model.Item
import com.tayyipgunay.shopbook.R
import java.io.ByteArrayOutputStream


@Composable
fun AddItemScreen(saveFunction: (item: Item) -> Unit) {
    val itemName = remember { mutableStateOf("") }
    val storeName = remember { mutableStateOf("") }
    val itemPrice = remember { mutableStateOf("") }
    val selectedImageUri = remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current
    Box(
        modifier = Modifier.fillMaxSize()
            .background(color = MaterialTheme.colorScheme.primaryContainer),
        contentAlignment = Alignment.Center
    )
    {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            imagePicker(onImageSelected = { uri ->
                selectedImageUri.value = uri

            }
            )




            TextField(
                value = itemName.value,
                onValueChange = {
                    itemName.value = it
                },
                placeholder = {
                    Text(text = "Enter Item Name")
                }, colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    errorContainerColor = Color.Transparent

                )
            )
            Spacer(modifier = Modifier.width(10.dp))
            TextField(
                value = storeName.value,
                onValueChange = {
                    storeName.value = it
                },
                placeholder = {
                    Text(text = "Enter Store Name")
                }, colors  = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    errorContainerColor = Color.Transparent)
            )

            Spacer(modifier = Modifier.width(10.dp))
            TextField(
                value = itemPrice.value,
                onValueChange = {
                    itemPrice.value = it
                },
                placeholder = {
                    Text(text = "Enter Item Price")
                },colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    errorContainerColor = Color.Transparent
                )
            )

            Button(
                onClick = {
                    val imageByteArray = selectedImageUri?.let {
                        resizeImage(
                            context = context,
                            uri = it, maxWidth = 600, maxHeight = 400)
                    } ?: ByteArray(0)
                    //save function
                    val item = Item(
                        itemName.value,
                        storeName.value,
                        itemImage=imageByteArray,
                        itemPrice.value
                    )

                    saveFunction(item)
                }, modifier = Modifier.padding(10.dp)
            )
            {
                Text(text = "Save")
            }


        }


    }
}@Composable
fun imagePicker(onImageSelected: (Uri) -> Unit) {
    val selectedImageUri = remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current

    val galleryLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        selectedImageUri.value = uri
        uri?.let { onImageSelected(it) } // Seçilen resim state güncellemesi
    }

    val permissionLauncher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) {
            galleryLauncher.launch("image/*")
        } else {
            Toast.makeText(context, "Permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    val permission = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
        Manifest.permission.READ_MEDIA_IMAGES
    } else {
        Manifest.permission.READ_EXTERNAL_STORAGE
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        selectedImageUri.value?.let {
            Image(
                painter = rememberAsyncImagePainter(model = it),
                contentDescription = "Selected Image",
                modifier = Modifier.size(200.dp, 200.dp),
                alignment = Alignment.Center,
                contentScale = ContentScale.Crop
            )
        } ?: Image(
            painter = painterResource(id = R.drawable.img),
            contentDescription = "Default Image",
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .size(300.dp, 200.dp)
                .clickable {
                    if (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED) {
                        galleryLauncher.launch("image/*")
                    } else {
                        permissionLauncher.launch(permission)
                    }
                }
        )
    }
}




fun resizeImage(context: Context, uri: MutableState<Uri?>, maxWidth: Int, maxHeight: Int) : ByteArray? {
    return try {
        val inputStream = context.contentResolver.openInputStream(uri.value!!)
        val originalBitmap = BitmapFactory.decodeStream(inputStream)

        val ratio = originalBitmap.width.toFloat() / originalBitmap.height.toFloat()

        var width = maxWidth
        var height = (width / ratio).toInt()

        if (height > maxHeight) {
            height = maxHeight
            width = (height * ratio).toInt()
        }

        val resizedBitmap = Bitmap.createScaledBitmap(originalBitmap,width,height,false)

        val byteArrayOutputStream = ByteArrayOutputStream()
        resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 100,byteArrayOutputStream)
        byteArrayOutputStream.toByteArray()
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}
