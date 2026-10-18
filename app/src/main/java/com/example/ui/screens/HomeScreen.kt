package com.example.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.*
import com.example.ui.components.EventCard
import com.example.ui.components.PrototypeNotice
import com.example.ui.theme.*

@Composable
fun HomeScreen(
    events: List<Event>,
    liveStreams: List<LiveStream>,
    savedEventIds: Set<String>,
    selectedCategory: EventCategory?,
    selectedCity: String,
    onCategorySelected: (EventCategory?) -> Unit,
    onEventClick: (Event) -> Unit,
    onSaveEventClick: (String) -> Unit,
    onLiveStreamClick: (LiveStream) -> Unit,
    onNavigateToDiscover: () -> Unit,
    onNavigateToMap: () -> Unit,
    onNavigateToStayFood: () -> Unit,
    onNavigateToMarketplace: () -> Unit,
    onNavigateToSocial: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .background(IvoryBackground)
            .padding(bottom = 80.dp),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Devotional Welcome Banner
        item {
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = SaffronContainer.copy(alpha = 0.5f)),
                border = BorderStroke(1.dp, SaffronPrimary.copy(alpha = 0.3f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        shape = CircleShape,
                        color = SaffronPrimary,
                        modifier = Modifier.size(42.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text("🚩", fontSize = 20.sp)
                        }
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "राम कृष्ण हरी • जय हरी विठ्ठल",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaroonPrimary
                        )
                        Text(
                            text = "Welcome to Warkari: Your Maharashtra pilgrimage & cultural festival companion.",
                            fontSize = 11.sp,
                            color = TextPrimary
                        )
                    }
                }
            }
        }

        // Live Darshan Carousel
        if (liveStreams.isNotEmpty()) {
            item {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Surface(
                                shape = CircleShape,
                                color = LiveRed,
                                modifier = Modifier.size(8.dp)
                            ) {}
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Authorized Live Darshan",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary
                            )
                        }
                        Text(
                            text = "All Streams",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = SaffronPrimary,
                            modifier = Modifier.clickable { onLiveStreamClick(liveStreams.first()) }
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(liveStreams) { stream ->
                            Card(
                                onClick = { onLiveStreamClick(stream) },
                                shape = RoundedCornerShape(12.dp),
                                colors = CardDefaults.cardColors(containerColor = IvorySurface),
                                elevation = CardDefaults.cardElevation(2.dp),
                                border = BorderStroke(1.dp, Color(0xFFE2D6C6)),
                                modifier = Modifier.width(220.dp)
                            ) {
                                Column {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(80.dp)
                                            .background(MaroonDark)
                                            .padding(8.dp)
                                    ) {
                                        Surface(
                                            shape = RoundedCornerShape(4.dp),
                                            color = LiveRed,
                                            modifier = Modifier.align(Alignment.TopStart)
                                        ) {
                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                                            ) {
                                                Icon(
                                                    imageVector = Icons.Default.LiveTv,
                                                    contentDescription = null,
                                                    tint = Color.White,
                                                    modifier = Modifier.size(10.dp)
                                                )
                                                Spacer(modifier = Modifier.width(3.dp))
                                                Text(
                                                    text = if (stream.isLive) "LIVE" else "UPCOMING",
                                                    fontSize = 8.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    color = Color.White
                                                )
                                            }
                                        }

                                        Column(modifier = Modifier.align(Alignment.BottomStart)) {
                                            Text(
                                                text = stream.templeName,
                                                fontSize = 12.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = Color.White,
                                                maxLines = 1
                                            )
                                            Text(
                                                text = stream.audioChant,
                                                fontSize = 9.sp,
                                                color = GoldAccent,
                                                maxLines = 1
                                            )
                                        }
                                    }

                                    Column(modifier = Modifier.padding(10.dp)) {
                                        Text(
                                            text = stream.eventName,
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.SemiBold,
                                            maxLines = 1,
                                            color = TextPrimary
                                        )
                                        Text(
                                            text = if (stream.isLive) "${stream.viewerCountSample} devotees viewing" else stream.scheduleNotice,
                                            fontSize = 10.sp,
                                            color = TextSecondary,
                                            maxLines = 1
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // Quick Category Selector Chips
        item {
            Column {
                Text(
                    text = "Event Categories",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
                Spacer(modifier = Modifier.height(8.dp))

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    item {
                        FilterChip(
                            selected = selectedCategory == null,
                            onClick = { onCategorySelected(null) },
                            label = { Text("All Events") },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = SaffronPrimary,
                                selectedLabelColor = Color.White
                            )
                        )
                    }
                    items(EventCategory.values()) { category ->
                        FilterChip(
                            selected = selectedCategory == category,
                            onClick = { onCategorySelected(category) },
                            label = { Text(category.englishName) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = SaffronPrimary,
                                selectedLabelColor = Color.White
                            )
                        )
                    }
                }
            }
        }

        // Quick Service Links (Stay, Food, Map, Stalls)
        item {
            Column {
                Text(
                    text = "Pilgrim Services & Directory",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    ServiceQuickButton(
                        icon = Icons.Default.Place,
                        title = "Pilgrim Map",
                        subtitle = "Radar & Stops",
                        color = SaffronPrimary,
                        modifier = Modifier.weight(1f),
                        onClick = onNavigateToMap
                    )
                    ServiceQuickButton(
                        icon = Icons.Default.Hotel,
                        title = "Stay & Food",
                        subtitle = "Dharamshala",
                        color = MaroonPrimary,
                        modifier = Modifier.weight(1f),
                        onClick = onNavigateToStayFood
                    )
                    ServiceQuickButton(
                        icon = Icons.Default.Store,
                        title = "Marketplace",
                        subtitle = "Stalls & Seva",
                        color = Color(0xFF2E7D32),
                        modifier = Modifier.weight(1f),
                        onClick = onNavigateToMarketplace
                    )
                    ServiceQuickButton(
                        icon = Icons.Default.Favorite,
                        title = "Social Feed",
                        subtitle = "Community",
                        color = Color(0xFF6D214F),
                        modifier = Modifier.weight(1f),
                        onClick = onNavigateToSocial
                    )
                }
            }
        }

        // Emergency Helplines Quick Strip
        item {
            Card(
                shape = RoundedCornerShape(10.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF3E0)),
                border = BorderStroke(1.dp, SaffronLight)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.LocalHospital,
                            contentDescription = "Medical",
                            tint = UrgentRed,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Emergency 24x7: Dial 108 (Ambulance) • 112 (Police) • 1800-233-0000 (Wari Desk)",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Medium,
                            color = TextPrimary
                        )
                    }
                }
            }
        }

        // Featured & Upcoming Events Header
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Upcoming Programs in $selectedCity",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
                Text(
                    text = "See All (${events.size})",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = SaffronPrimary,
                    modifier = Modifier.clickable { onNavigateToDiscover() }
                )
            }
        }

        // Events List
        if (events.isEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No events match the selected filters in $selectedCity.",
                        fontSize = 13.sp,
                        color = TextSecondary
                    )
                }
            }
        } else {
            items(events.take(4)) { event ->
                EventCard(
                    event = event,
                    isSaved = savedEventIds.contains(event.id),
                    onSaveClick = { onSaveEventClick(event.id) },
                    onClick = { onEventClick(event) }
                )
            }
        }

        // Prototype Disclaimer Notice
        item {
            PrototypeNotice(
                message = "Warkari is a non-commercial prototype. Event timings, dharamsala availability, and stream previews are based on authentic Maharashtra cultural schedules."
            )
        }
    }
}

@Composable
fun ServiceQuickButton(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    subtitle: String,
    color: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = IvorySurface),
        border = BorderStroke(1.dp, Color(0xFFE2D6C6)),
        elevation = CardDefaults.cardElevation(1.dp),
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Surface(
                shape = CircleShape,
                color = color.copy(alpha = 0.15f),
                modifier = Modifier.size(32.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(imageVector = icon, contentDescription = title, tint = color, modifier = Modifier.size(16.dp))
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = title,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = subtitle,
                fontSize = 9.sp,
                color = TextSecondary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
