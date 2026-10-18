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
import com.example.data.model.Language
import com.example.data.model.User
import com.example.data.model.UserRole
import com.example.ui.components.PrototypeNotice
import com.example.ui.theme.*

@Composable
fun ProfileScreen(
    user: User,
    savedEvents: List<Event>,
    onRoleChange: (UserRole) -> Unit,
    onLanguageChange: (Language) -> Unit,
    onEventClick: (Event) -> Unit,
    onEmergencyCall: (String) -> Unit
) {
    var notificationsEnabled by remember { mutableStateOf(user.notificationsEnabled) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(IvoryBackground)
            .padding(bottom = 70.dp),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Devotee User Profile Card
        item {
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = IvorySurface),
                elevation = CardDefaults.cardElevation(2.dp),
                border = BorderStroke(1.dp, Color(0xFFE2D6C6))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            shape = CircleShape,
                            color = SaffronPrimary,
                            modifier = Modifier.size(54.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(
                                    text = user.name.take(1),
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = Color.White
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(14.dp))
                        Column {
                            Text(
                                text = user.name,
                                fontSize = 17.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary
                            )
                            Text(
                                text = user.phone,
                                fontSize = 12.sp,
                                color = TextSecondary
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Surface(
                                shape = RoundedCornerShape(4.dp),
                                color = SaffronContainer
                            ) {
                                Text(
                                    text = user.role.label,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaroonDark,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }
                    }
                }
            }
        }

        // Active Role Switcher
        item {
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = IvorySurface),
                border = BorderStroke(1.dp, Color(0xFFE2D6C6))
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = "App Mode / भूमिकेची निवड",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                    Text(
                        text = "Switch perspective between Devotee, Organizer, Vendor, or Service Provider",
                        fontSize = 11.sp,
                        color = TextSecondary
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    UserRole.values().forEach { role ->
                        val isSelected = user.role == role
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = if (isSelected) SaffronContainer.copy(alpha = 0.5f) else IvorySurfaceVariant,
                            border = if (isSelected) BorderStroke(1.5.dp, SaffronPrimary) else null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                                .clickable { onRoleChange(role) }
                        ) {
                            Row(
                                modifier = Modifier.padding(10.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column {
                                    Text(
                                        text = role.label,
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = TextPrimary
                                    )
                                    Text(
                                        text = role.marathiLabel,
                                        fontSize = 11.sp,
                                        color = MaroonPrimary
                                    )
                                }
                                if (isSelected) {
                                    Icon(Icons.Default.CheckCircle, contentDescription = null, tint = SaffronPrimary)
                                }
                            }
                        }
                    }
                }
            }
        }

        // Language Selector Card
        item {
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = IvorySurface),
                border = BorderStroke(1.dp, Color(0xFFE2D6C6))
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = "Language / भाषा",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Language.values().forEach { lang ->
                            val isSelected = user.preferredLanguage == lang
                            FilterChip(
                                selected = isSelected,
                                onClick = { onLanguageChange(lang) },
                                label = { Text(lang.label) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = SaffronPrimary,
                                    selectedLabelColor = Color.White
                                ),
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
            }
        }

        // Saved Pilgrimage Events
        item {
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = IvorySurface),
                border = BorderStroke(1.dp, Color(0xFFE2D6C6))
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Saved Events & Yatras (${savedEvents.size})",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )
                        Icon(Icons.Default.Bookmark, contentDescription = null, tint = SaffronPrimary)
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    if (savedEvents.isEmpty()) {
                        Text(
                            text = "No saved events yet. Bookmark any Wari, Ganpati or Kumbh event to access it here.",
                            fontSize = 11.sp,
                            color = TextSecondary
                        )
                    } else {
                        savedEvents.forEach { ev ->
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = IvorySurfaceVariant,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                                    .clickable { onEventClick(ev) }
                            ) {
                                Row(
                                    modifier = Modifier.padding(8.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(text = ev.titleEn, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                                        Text(text = "${ev.startDate} • ${ev.city}", fontSize = 10.sp, color = TextSecondary)
                                    }
                                    Icon(Icons.Default.ChevronRight, contentDescription = null, tint = TextMuted)
                                }
                            }
                        }
                    }
                }
            }
        }

        // Notification Settings
        item {
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = IvorySurface),
                border = BorderStroke(1.dp, Color(0xFFE2D6C6))
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = "Notification Preferences",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                    Spacer(modifier = Modifier.height(6.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Aarti & Emergency Alerts", fontSize = 12.sp, fontWeight = FontWeight.Medium, color = TextPrimary)
                            Text("Receive official alerts from Mandals and Palkhi desks", fontSize = 10.sp, color = TextSecondary)
                        }
                        Switch(
                            checked = notificationsEnabled,
                            onCheckedChange = { notificationsEnabled = it },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color.White,
                                checkedTrackColor = SaffronPrimary
                            )
                        )
                    }
                }
            }
        }

        // Safety & Emergency Help Numbers
        item {
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF8E1)),
                border = BorderStroke(1.dp, GoldAccent.copy(alpha = 0.5f))
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.HealthAndSafety, contentDescription = null, tint = MaroonPrimary)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Pilgrim Safety & Emergency Helplines",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaroonPrimary
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("• 108: Government Emergency Ambulance Service (24x7)", fontSize = 11.sp, color = TextPrimary)
                    Text("• 112: Maharashtra Police Integrated Helpline", fontSize = 11.sp, color = TextPrimary)
                    Text("• 1091: Women Pilgrim Safety Helpline", fontSize = 11.sp, color = TextPrimary)
                    Text("• 1800-233-0000: Ashadhi Wari Pilgrim Control Room", fontSize = 11.sp, color = TextPrimary)
                }
            }
        }

        // Privacy & Neutrality Commitment
        item {
            PrototypeNotice(
                message = "Privacy Policy: Warkari does not require users to declare religious identity. Preferences are stored locally for discovering cultural and community events."
            )
        }
    }
}
