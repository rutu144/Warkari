package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import com.example.data.model.Event
import com.example.data.model.ScheduleItem
import com.example.ui.components.PrototypeNotice
import com.example.ui.components.VerificationBadge
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventDetailScreen(
    event: Event,
    isSaved: Boolean,
    isFollowed: Boolean,
    onBackClick: () -> Unit,
    onSaveClick: () -> Unit,
    onFollowOrgClick: () -> Unit,
    onShareClick: () -> Unit,
    onGetDirectionsClick: () -> Unit,
    onCallOrganizerClick: () -> Unit,
    onWatchLiveClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = event.titleEn,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                actions = {
                    IconButton(onClick = onShareClick) {
                        Icon(Icons.Default.Share, contentDescription = "Share", tint = Color.White)
                    }
                    IconButton(onClick = onSaveClick) {
                        Icon(
                            imageVector = if (isSaved) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                            contentDescription = "Save",
                            tint = if (isSaved) GoldAccent else Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = SaffronPrimary,
                    titleContentColor = Color.White
                )
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(IvoryBackground)
                .padding(innerPadding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Hero Title Card
            item {
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = IvorySurface),
                    elevation = CardDefaults.cardElevation(2.dp),
                    border = BorderStroke(1.dp, Color(0xFFE2D6C6))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(
                                shape = RoundedCornerShape(4.dp),
                                color = MaroonPrimary
                            ) {
                                Text(
                                    text = event.category.englishName,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                )
                            }
                            VerificationBadge(isVerified = event.isVerified)
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = event.titleEn,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = TextPrimary
                        )
                        Text(
                            text = event.titleMr,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Medium,
                            color = MaroonPrimary
                        )

                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Last updated: ${event.lastUpdated}",
                            fontSize = 11.sp,
                            color = TextMuted
                        )

                        Spacer(modifier = Modifier.height(12.dp))
                        HorizontalDivider(color = Color(0xFFEAE3D9))
                        Spacer(modifier = Modifier.height(12.dp))

                        // Key Info Grid
                        DetailInfoRow(Icons.Default.Event, "Dates", "${event.startDate} — ${event.endDate}")
                        DetailInfoRow(Icons.Default.Schedule, "Timings", "${event.startTime} - ${event.endTime}")
                        DetailInfoRow(Icons.Default.Place, "Venue", event.venue)
                        DetailInfoRow(Icons.Default.LocationOn, "Full Route / Address", event.address)

                        if (event.dindiNumber != null) {
                            DetailInfoRow(Icons.Default.Flag, "Dindi Coordination", event.dindiNumber)
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        // Action Buttons: Directions, Call, Live
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Button(
                                onClick = onGetDirectionsClick,
                                modifier = Modifier.weight(1f),
                                colors = ButtonDefaults.buttonColors(containerColor = SaffronPrimary),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Icon(Icons.Default.Directions, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Directions", fontSize = 12.sp)
                            }

                            OutlinedButton(
                                onClick = onCallOrganizerClick,
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Icon(Icons.Default.Call, contentDescription = null, modifier = Modifier.size(16.dp), tint = TextPrimary)
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Contact Desk", fontSize = 12.sp, color = TextPrimary)
                            }
                        }

                        if (event.liveStreamAvailable) {
                            Spacer(modifier = Modifier.height(8.dp))
                            Button(
                                onClick = onWatchLiveClick,
                                modifier = Modifier.fillMaxWidth(),
                                colors = ButtonDefaults.buttonColors(containerColor = LiveRed),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Icon(Icons.Default.LiveTv, contentDescription = null, modifier = Modifier.size(18.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Watch Official Live Darshan Feed", fontSize = 13.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }

            // Organizer Card
            item {
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = IvorySurface),
                    border = BorderStroke(1.dp, Color(0xFFE2D6C6))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                            Surface(
                                shape = CircleShape,
                                color = SaffronContainer,
                                modifier = Modifier.size(40.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(Icons.Default.VerifiedUser, contentDescription = null, tint = SaffronPrimary)
                                }
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = "Organized By",
                                    fontSize = 10.sp,
                                    color = TextSecondary
                                )
                                Text(
                                    text = event.organizerName,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = TextPrimary
                                )
                            }
                        }

                        Button(
                            onClick = onFollowOrgClick,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (isFollowed) Color(0xFFE0E0E0) else MaroonPrimary,
                                contentColor = if (isFollowed) TextPrimary else Color.White
                            ),
                            shape = RoundedCornerShape(16.dp),
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp)
                        ) {
                            Text(if (isFollowed) "Following" else "+ Follow", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            // Description Section
            item {
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = IvorySurface),
                    border = BorderStroke(1.dp, Color(0xFFE2D6C6))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "About Event / स्वरूप व माहिती",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = event.descriptionEn,
                            fontSize = 13.sp,
                            color = TextPrimary,
                            lineHeight = 18.sp
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = event.descriptionMr,
                            fontSize = 13.sp,
                            color = MaroonPrimary,
                            lineHeight = 18.sp
                        )
                    }
                }
            }

            // Urgent Official Announcements
            if (event.announcements.isNotEmpty()) {
                item {
                    Text(
                        text = "Official Sansthan Announcements",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                }

                items(event.announcements) { announcement ->
                    Card(
                        shape = RoundedCornerShape(8.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (announcement.isUrgent) Color(0xFFFFEBEE) else IvorySurfaceVariant
                        ),
                        border = if (announcement.isUrgent) BorderStroke(1.dp, UrgentRed) else null,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.Top) {
                            Icon(
                                imageVector = if (announcement.isUrgent) Icons.Default.Campaign else Icons.Default.Announcement,
                                contentDescription = null,
                                tint = if (announcement.isUrgent) UrgentRed else SaffronPrimary,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = announcement.title,
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (announcement.isUrgent) UrgentRed else TextPrimary
                                    )
                                    Text(
                                        text = announcement.timestamp,
                                        fontSize = 10.sp,
                                        color = TextMuted
                                    )
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = announcement.message,
                                    fontSize = 12.sp,
                                    color = TextPrimary
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = "Broadcast by: ${announcement.authorName}",
                                    fontSize = 10.sp,
                                    color = TextSecondary
                                )
                            }
                        }
                    }
                }
            }

            // Day-wise Schedule Timeline
            if (event.schedule.isNotEmpty()) {
                item {
                    Text(
                        text = "Ritual & Program Schedule",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                }

                items(event.schedule) { item ->
                    ScheduleRow(item)
                }
            }

            // Prototype Disclaimer
            item {
                PrototypeNotice(
                    message = "Official route tracking, GPS updates and darshan pass tokens are sample data for architectural demonstration."
                )
            }
        }
    }
}

@Composable
fun DetailInfoRow(icon: androidx.compose.ui.graphics.vector.ImageVector, label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.Top
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = SaffronPrimary,
            modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(
                text = label,
                fontSize = 10.sp,
                color = TextSecondary
            )
            Text(
                text = value,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = TextPrimary
            )
        }
    }
}

@Composable
fun ScheduleRow(item: ScheduleItem) {
    Card(
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = IvorySurface),
        border = BorderStroke(1.dp, Color(0xFFEFE6DA)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            Surface(
                shape = RoundedCornerShape(6.dp),
                color = SaffronContainer,
                modifier = Modifier.width(72.dp)
            ) {
                Box(contentAlignment = Alignment.Center, modifier = Modifier.padding(6.dp)) {
                    Text(
                        text = item.time,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaroonDark
                    )
                }
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.titleEn,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
                Text(
                    text = item.titleMr,
                    fontSize = 11.sp,
                    color = MaroonPrimary
                )
                Text(
                    text = "${item.description} • ${item.locationName}",
                    fontSize = 10.sp,
                    color = TextSecondary
                )
            }
        }
    }
}
