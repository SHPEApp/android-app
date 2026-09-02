package com.unt.shpe.design

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun SHPECard(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(14.dp))
            .background(Color.White)
            .padding(14.dp),
        content = content
    )
}

@Composable
fun SHPESectionTitle(
    title: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = title.uppercase(),
        style = MaterialTheme.typography.labelMedium,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = modifier
    )
}

@Composable
fun SHPEPrimaryButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(vertical = 14.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Brand.green,
            contentColor = Color.White
        ),
        shape = RoundedCornerShape(14.dp),
        content = content
    )
}

@Composable
fun SHPELoadingSpinner(
    modifier: Modifier = Modifier,
    label: String? = null,
    tint: Color = Brand.green,
    horizontalAlignment: Alignment.Horizontal = Alignment.CenterHorizontally
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp, horizontalAlignment)
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(20.dp),
            color = tint,
            strokeWidth = 2.dp
        )
        if (label != null) {
            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall,
                color = if (tint == Color.White) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun SHPELoadingOverlay(
    isVisible: Boolean,
    modifier: Modifier = Modifier,
    tint: Color = Color.White,
    backgroundOpacity: Float = 0.3f
) {
    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(Color.Gray.copy(alpha = backgroundOpacity)),
            contentAlignment = Alignment.Center
        ) {
            Surface(
                modifier = Modifier.size(80.dp),
                shape = RoundedCornerShape(18.dp),
                color = Color.Black.copy(alpha = 0.75f),
                shadowElevation = 12.dp
            ) {
                Box(contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(40.dp)
                            .padding(10.dp),
                        color = tint,
                        strokeWidth = 3.dp
                    )
                }
            }
        }
    }
}

@Composable
fun SHPEListRow(
    icon: ImageVector,
    title: String,
    accessibilityID: String,
    modifier: Modifier = Modifier,
    isDestructive: Boolean = false,
    action: (() -> Unit)? = null
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .then(
                if (action != null) Modifier.clickable(onClick = action) else Modifier
            )
            .testTag(accessibilityID)
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 17.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = if (isDestructive) Color.Red else Brand.green,
                modifier = Modifier.width(26.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = title,
                color = if (isDestructive) Color.Red else Brand.ink,
                modifier = Modifier.weight(1f)
            )
            if (!isDestructive) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    tint = MaterialTheme.colorScheme.outlineVariant
                )
            }
        }
        HorizontalDivider(
            modifier = Modifier.padding(start = 58.dp),
            thickness = 1.dp,
            color = Color.Gray.copy(alpha = 0.12f)
        )
    }
}

@Composable
fun <Item> SHPETableView(
    items: List<Item>,
    modifier: Modifier = Modifier,
    content: @Composable (Item) -> Unit
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        color = Color.White
    ) {
        Column {
            items.forEach { item ->
                content(item)
            }
        }
    }
}
