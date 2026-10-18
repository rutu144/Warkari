package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.LiveStream
import com.example.ui.components.PrototypeNotice
import com.example.ui.components.VerificationBadge
import com.example.ui.theme.*

@Composable
fun LiveDarshanScreen(
    liveStreams: List<LiveStream>,
    selectedStream: LiveStream?,
    onStreamSelect: (LiveStream) -> Unit,
    onChantClick: () -> Unit
) {
    var chantCounter by remember { mutableStateOf(108) }
    val activeStream = selectedStream ?: liveStreams.firstOrNull()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(IvoryBackground)
            .padding(bottom = 70.dp),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Top Header
        item {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.LiveTv,
                        contentDescription = "Live",
                        tint = LiveRed,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Authorized Live Darshan",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                }
                Text(
                    text = "Direct broadcast feeds from verified temples and Mandals in Maharashtra",
                    fontSize = 11.sp,
                    color = TextSecondary
                )
            }
        }

        // Main Player Viewport Card
        if (activeStream != null) {
            item {
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.Black),
                    elevation = CardDefaults.cardElevation(4.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column {
                        // Video Screen Area
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(210.dp)
                                .background(Color(0xFF14100D)),
                            contentAlignment = Alignment.Center
                        ) {
                            // Video Graphic / Mandala Motif
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.padding(16.dp)
                            ) {
                                Surface(
                                    shape = CircleShape,
                                    color = GoldAccent.copy(alpha = 0.2f),
                                    modifier = Modifier.size(68.dp)
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Text("🕉️", fontSize = 32.sp)
                                    }
                                }
                                Spacer(modifier = Modifier.height(10.dp))
                                Text(
                                    text = activeStream.templeName,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                                Text(
                                    text = activeStream.streamTitle,
                                    fontSize = 12.sp,
                                    color = GoldAccent
                                )
                            }

                            // Top Status Badges
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .align(Alignment.TopStart)
                                    .padding(10.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Surface(
                                    shape = RoundedCornerShape(4.dp),
                                    color = if (activeStream.isLive) LiveRed else Color.Gray
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.FiberManualRecord,
                                            contentDescription = null,
                                            tint = Color.White,
                                            modifier = Modifier.size(10.dp)
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text(
                                            text = if (activeStream.isLive) "LIVE" else "OFFLINE",
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color.White
                                        )
                                    }
                                }

                                Surface(
                                    shape = RoundedCornerShape(4.dp),
                                    color = Color.Black.copy(alpha = 0.6f)
                                ) {
                                    Text(
                                        text = activeStream.resolutionTag,
                                        fontSize = 10.sp,
                                        color = Color.White,
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                    )
                                }
                            }

                            // Bottom Viewers Count
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .align(Alignment.BottomStart)
                                    .background(Color.Black.copy(alpha = 0.5f))
                                    .padding(horizontal = 10.dp, vertical = 6.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.Visibility,
                                        contentDescription = null,
                                        tint = Color.White,
                                        modifier = Modifier.size(14.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = if (activeStream.isLive) "${activeStream.viewerCountSample} devotees viewing" else "Stream currently paused",
                                        fontSize = 11.sp,
                                        color = Color.White
                                    )
                                }
                                Text(
                                    text = "Official Media Cell",
                                    fontSize = 10.sp,
                                    color = GoldAccent
                                )
                            }
                        }

                        // Player Controls & Devotee Chant Row
                        Surface(color = DarkSurface) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column {
                                        Text(
                                            text = "Devotional Chant:",
                                            fontSize = 10.sp,
                                            color = Color.White.copy(alpha = 0.7f)
                                        )
                                        Text(
                                            text = activeStream.audioChant,
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = GoldAccent
                                        )
                                    }

                                    Button(
                                        onClick = {
                                            chantCounter++
                                            onChantClick()
                                        },
                                        colors = ButtonDefaults.buttonColors(containerColor = SaffronPrimary),
                                        shape = RoundedCornerShape(16.dp)
                                    ) {
                                        Icon(Icons.Default.VolunteerActivism, contentDescription = null, modifier = Modifier.size(16.dp))
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text("Chant ($chantCounter)", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // Stream Schedule & Ritual Timings
        item {
            Card(
                shape = RoundedCornerShape(10.dp),
                colors = CardDefaults.cardColors(containerColor = IvorySurface),
                border = BorderStroke(1.dp, Color(0xFFE2D6C6))
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = "Temple Aarti & Darshan Schedule",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = activeStream?.scheduleNotice ?: "Daily schedule maintained by trust.",
                        fontSize = 12.sp,
                        color = MaroonPrimary,
                        lineHeight = 17.sp
                    )
                }
            }
        }

        // Available Temple Feeds
        item {
            Text(
                text = "Available Authorized Streams",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )
        }

        items(liveStreams) { stream ->
            val isSelected = activeStream?.id == stream.id
            Card(
                onClick = { onStreamSelect(stream) },
                shape = RoundedCornerShape(10.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (isSelected) SaffronContainer.copy(alpha = 0.5f) else IvorySurface
                ),
                border = if (isSelected) BorderStroke(1.5.dp, SaffronPrimary) else null,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        shape = CircleShape,
                        color = if (stream.isLive) LiveRed else Color.Gray,
                        modifier = Modifier.size(38.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.LiveTv,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = stream.templeName,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )
                        Text(
                            text = "${stream.eventName} • ${stream.organizerName}",
                            fontSize = 11.sp,
                            color = TextSecondary
                        )
                        Text(
                            text = if (stream.isLive) "${stream.viewerCountSample} watching live" else "Next aarti scheduled",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Medium,
                            color = if (stream.isLive) LiveRed else TextMuted
                        )
                    }
                    if (isSelected) {
                        Icon(Icons.Default.CheckCircle, contentDescription = "Active", tint = SaffronPrimary)
                    }
                }
            }
        }

        // Authorized Streaming Notice
        item {
            PrototypeNotice(
                message = "Live Darshan streams are authorized feeds provided by respective temple sansthans. Third-party scraping or unauthorized commercial redistribution is strictly prohibited."
            )
        }
    }
}
