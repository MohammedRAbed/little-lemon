package com.example.littlelemon

import android.widget.ImageView
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView
import com.bumptech.glide.Glide
import androidx.compose.ui.Modifier

@Composable
fun GlideImage(
    imageUrl: String,
    modifier: Modifier = Modifier
) {
    AndroidView(
        modifier = modifier,
        factory = { context ->
            ImageView(context).apply {
                scaleType = ImageView.ScaleType.FIT_XY
            }
        },
        update = { imageView ->
            // Use Glide to load the image
            Glide.with(imageView.context)
                .load(imageUrl)
                .into(imageView)
        }
    )
}