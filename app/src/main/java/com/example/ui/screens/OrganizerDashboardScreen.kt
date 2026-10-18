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
import com.example.data.model.Event
import com.example.data.model.EventCategory
import com.example.ui.components.PrototypeNotice
import com.example.ui.components.VerificationBadge
import com.example.ui.theme.*

@Composable
fun OrganizerDashboardScreen(
    managedEvents: List<Event>,
    onBroadcastAnnouncement: (eventId: String, title: String, message: String, isUrgent: Boolean) -> Unit,
    onCreateEvent: (titleEn: String, titleMr: String, descEn: String, descMr: String, category: EventCategory, city: String, venue: String, start: String, end: String, phone: String) -> Unit
) {
    var showCreateEventModal by remember { mutableStateOf(false) }
    var broadcastTargetEvent by remember { mutableStateOf<Event?>(null) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(IvoryBackground)
            .padding(bottom = 70.dp),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Organizer Profile Banner
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
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Surface(
                                shape = CircleShape,
                                color = SaffronPrimary,
                                modifier = Modifier.size(44.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(Icons.Default.AccountBalance, contentDescription = null, tint = Color.White)
                                }
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = "Shri Vitthal Mandir & Sansthan Core",
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = TextPrimary
                                )
                                Text(
                                    text = "Organizing Committee • ID: MH-PDR-1892",
                                    fontSize = 11.sp,
                                    color = TextSecondary
                                )
                            }
                        }
                        VerificationBadge(isVerified = true)
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Action Buttons: Create Event & Broadcast
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = { showCreateEventModal = true },
                            colors = ButtonDefaults.buttonColors(containerColor = SaffronPrimary),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Publish Event", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }

                        Button(
                            onClick = { broadcastTargetEvent = managedEvents.firstOrNull() },
                            colors = ButtonDefaults.buttonColors(containerColor = UrgentRed),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(Icons.Default.Campaign, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Broadcast Alert", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }

        // Analytics Cards
        item {
            Column {
                Text(
                    text = "Pilgrim Engagement & Darshan Reach",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    MetricCard(
                        value = "184,200+",
                        label = "Pilgrims Reached",
                        color = SaffronPrimary,
                        modifier = Modifier.weight(1f)
                    )
                    MetricCard(
                        value = "98,500",
                        label = "Live Stream Views",
                        color = MaroonPrimary,
                        modifier = Modifier.weight(1f)
                    )
                    MetricCard(
                        value = "327",
                        label = "Dindis Managed",
                        color = Color(0xFF2E7D32),
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        // Managed Events
        item {
            Text(
                text = "Your Published Events & Yatras (${managedEvents.size})",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )
        }

        items(managedEvents) { event ->
            Card(
                shape = RoundedCornerShape(10.dp),
                colors = CardDefaults.cardColors(containerColor = IvorySurface),
                border = BorderStroke(1.dp, Color(0xFFE2D6C6)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = event.titleEn,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary,
                            modifier = Modifier.weight(1f)
                        )
                        Surface(
                            shape = RoundedCornerShape(4.dp),
                            color = Color(0xFFE8F5E9)
                        ) {
                            Text(
                                text = "ACTIVE",
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                color = VerifiedGreen,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "${event.category.englishName} • ${event.venue}, ${event.city}",
                        fontSize = 11.sp,
                        color = TextSecondary
                    )
                    Text(
                        text = "Dates: ${event.startDate} - ${event.endDate}",
                        fontSize = 11.sp,
                        color = MaroonPrimary
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "${event.announcements.size} announcements • ${event.schedule.size} schedule items",
                            fontSize = 10.sp,
                            color = TextMuted
                        )

                        TextButton(
                            onClick = { broadcastTargetEvent = event }
                        ) {
                            Icon(Icons.Default.Campaign, contentDescription = null, modifier = Modifier.size(14.dp), tint = UrgentRed)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Send Alert", fontSize = 11.sp, color = UrgentRed, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }

        item {
            PrototypeNotice(
                message = "Official organizer privileges require government temple trust or charity commissioner registration verification."
            )
        }
    }

    // Broadcast Announcement Modal
    broadcastTargetEvent?.let { target ->
        var title by remember { mutableStateOf("") }
        var message by remember { mutableStateOf("") }
        var isUrgent by remember { mutableStateOf(false) }

        AlertDialog(
            onDismissRequest = { broadcastTargetEvent = null },
            title = {
                Column {
                    Text("Broadcast Pilgrim Alert", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Text(target.titleEn, fontSize = 11.sp, color = SaffronPrimary, maxLines = 1)
                }
            },
            text = {
                Column(modifier = Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = title,
                        onValueChange = { title = it },
                        label = { Text("Announcement Headline *") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = message,
                        onValueChange = { message = it },
                        label = { Text("Details (Directions, queue, water, etc.) *") },
                        maxLines = 3,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(
                            checked = isUrgent,
                            onCheckedChange = { isUrgent = it },
                            colors = CheckboxDefaults.colors(checkedColor = UrgentRed)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Mark as High Priority / Emergency Alert", fontSize = 11.sp, color = if (isUrgent) UrgentRed else TextPrimary)
                    }
                }
            },
            confirmButton = {
                Button(
                    colors = ButtonDefaults.buttonColors(containerColor = if (isUrgent) UrgentRed else SaffronPrimary),
                    onClick = {
                        if (title.isNotBlank() && message.isNotBlank()) {
                            onBroadcastAnnouncement(target.id, title, message, isUrgent)
                            broadcastTargetEvent = null
                        }
                    }
                ) {
                    Text("Broadcast Alert")
                }
            },
            dismissButton = {
                TextButton(onClick = { broadcastTargetEvent = null }) {
                    Text("Cancel")
                }
            }
        )
    }

    // Create Event Modal
    if (showCreateEventModal) {
        var titleEn by remember { mutableStateOf("") }
        var titleMr by remember { mutableStateOf("") }
        var descEn by remember { mutableStateOf("") }
        var city by remember { mutableStateOf("Pandharpur") }
        var venue by remember { mutableStateOf("") }
        var category by remember { mutableStateOf(EventCategory.WARI_PALKHI) }
        var start by remember { mutableStateOf("July 2026") }
        var end by remember { mutableStateOf("July 2026") }
        var phone by remember { mutableStateOf("+91 98220 12345") }

        AlertDialog(
            onDismissRequest = { showCreateEventModal = false },
            title = { Text("Publish New Cultural Event", fontWeight = FontWeight.Bold, fontSize = 16.sp) },
            text = {
                Column(modifier = Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = titleEn,
                        onValueChange = { titleEn = it },
                        label = { Text("Event Title (English) *") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    OutlinedTextField(
                        value = titleMr,
                        onValueChange = { titleMr = it },
                        label = { Text("कार्यक्रमाचे नाव (मराठीत)") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    OutlinedTextField(
                        value = venue,
                        onValueChange = { venue = it },
                        label = { Text("Venue / Ground *") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    OutlinedTextField(
                        value = descEn,
                        onValueChange = { descEn = it },
                        label = { Text("Description & Devotee Guidelines") },
                        maxLines = 2,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    colors = ButtonDefaults.buttonColors(containerColor = SaffronPrimary),
                    onClick = {
                        if (titleEn.isNotBlank() && venue.isNotBlank()) {
                            onCreateEvent(titleEn, titleMr, descEn, descEn, category, city, venue, start, end, phone)
                            showCreateEventModal = false
                        }
                    }
                ) {
                    Text("Submit Event")
                }
            },
            dismissButton = {
                TextButton(onClick = { showCreateEventModal = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
fun MetricCard(value: String, label: String, color: Color, modifier: Modifier = Modifier) {
    Card(
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = IvorySurface),
        border = BorderStroke(1.dp, Color(0xFFE2D6C6)),
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = value, fontSize = 16.sp, fontWeight = FontWeight.ExtraBold, color = color)
            Text(text = label, fontSize = 10.sp, color = TextSecondary, maxLines = 1)
        }
    }
}
