package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.LocationPoint
import com.example.data.model.LocationType
import com.example.ui.components.PrototypeNotice
import com.example.ui.theme.*

@Composable
fun MapScreen(
    locations: List<LocationPoint>,
    selectedType: LocationType?,
    onTypeSelect: (LocationType?) -> Unit,
    onLocationCall: (String) -> Unit,
    onGetDirections: (LocationPoint) -> Unit
) {
    var selectedLocation by remember { mutableStateOf<LocationPoint?>(locations.firstOrNull()) }
    var isRadarView by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(IvoryBackground)
    ) {
        // Top Filter Bar
        Surface(color = IvorySurface, shadowElevation = 2.dp) {
            Column(modifier = Modifier.padding(12.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Pilgrimage Route & Nearby Seva",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )
                        Text(
                            text = "Locate Bhandara, Dharamshalas, Water & Medical Tents",
                            fontSize = 11.sp,
                            color = TextSecondary
                        )
                    }

                    // View Switcher (Radar vs List)
                    IconButton(onClick = { isRadarView = !isRadarView }) {
                        Icon(
                            imageVector = if (isRadarView) Icons.Default.List else Icons.Default.Radar,
                            contentDescription = "Switch View",
                            tint = SaffronPrimary
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Location Type Chips
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    item {
                        FilterChip(
                            selected = selectedType == null,
                            onClick = { onTypeSelect(null) },
                            label = { Text("All Points") },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = SaffronPrimary,
                                selectedLabelColor = Color.White
                            )
                        )
                    }
                    items(LocationType.values()) { type ->
                        FilterChip(
                            selected = selectedType == type,
                            onClick = { onTypeSelect(type) },
                            label = { Text(type.labelEn) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = SaffronPrimary,
                                selectedLabelColor = Color.White
                            )
                        )
                    }
                }
            }
        }

        if (isRadarView) {
            // Interactive Radar / Compass Map Simulation
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .background(Color(0xFF231C16))
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                // Radar Rings
                Box(
                    modifier = Modifier
                        .size(280.dp)
                        .border(1.dp, Color(0xFF5D4037).copy(alpha = 0.5f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(190.dp)
                            .border(1.dp, Color(0xFF8D6E63).copy(alpha = 0.5f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .size(100.dp)
                                .border(1.dp, SaffronPrimary.copy(alpha = 0.6f), CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            // Center User Pulse
                            Surface(
                                shape = CircleShape,
                                color = SaffronPrimary,
                                modifier = Modifier.size(16.dp)
                            ) {}
                            Text(
                                text = "You (GPS)",
                                fontSize = 9.sp,
                                color = GoldAccent,
                                modifier = Modifier.padding(top = 26.dp)
                            )
                        }
                    }
                }

                // Range Labels
                Text("500m", fontSize = 9.sp, color = TextMuted, modifier = Modifier.align(Alignment.TopCenter).padding(top = 80.dp))
                Text("1.5km", fontSize = 9.sp, color = TextMuted, modifier = Modifier.align(Alignment.TopCenter).padding(top = 35.dp))
                Text("5km", fontSize = 9.sp, color = TextMuted, modifier = Modifier.align(Alignment.TopCenter).padding(top = 8.dp))

                // Simulated Interactive Pins placed around the radar
                locations.take(6).forEachIndexed { index, loc ->
                    val alignment = when (index % 6) {
                        0 -> Alignment.TopStart
                        1 -> Alignment.TopEnd
                        2 -> Alignment.BottomStart
                        3 -> Alignment.BottomEnd
                        4 -> Alignment.CenterStart
                        else -> Alignment.CenterEnd
                    }

                    val isSelected = selectedLocation?.id == loc.id

                    Surface(
                        shape = RoundedCornerShape(14.dp),
                        color = if (isSelected) GoldAccent else MaroonPrimary,
                        border = BorderStroke(1.5.dp, if (isSelected) Color.White else Color.Transparent),
                        modifier = Modifier
                            .align(alignment)
                            .padding(16.dp)
                            .clickable { selectedLocation = loc }
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Icon(
                                imageVector = when (loc.type) {
                                    LocationType.TEMPLE_MANDAL -> Icons.Default.Place
                                    LocationType.BHANDARA_MAHAPRASAD -> Icons.Default.Restaurant
                                    LocationType.MEDICAL_EMERGENCY -> Icons.Default.LocalHospital
                                    LocationType.DRINKING_WATER -> Icons.Default.WaterDrop
                                    LocationType.PUBLIC_TOILET -> Icons.Default.Wc
                                    LocationType.ACCOMMODATION -> Icons.Default.Hotel
                                    else -> Icons.Default.Store
                                },
                                contentDescription = null,
                                tint = if (isSelected) MaroonDark else Color.White,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "${loc.distanceKm}km",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isSelected) MaroonDark else Color.White
                            )
                        }
                    }
                }
            }
        } else {
            // Alternative List View
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(locations) { loc ->
                    val isSelected = selectedLocation?.id == loc.id
                    Card(
                        onClick = { selectedLocation = loc },
                        shape = RoundedCornerShape(10.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (isSelected) SaffronContainer.copy(alpha = 0.5f) else IvorySurface
                        ),
                        border = BorderStroke(1.dp, if (isSelected) SaffronPrimary else Color(0xFFE2D6C6))
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(
                                shape = CircleShape,
                                color = if (loc.isFreeService) Color(0xFFE8F5E9) else SaffronContainer,
                                modifier = Modifier.size(36.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(
                                        imageVector = when (loc.type) {
                                            LocationType.MEDICAL_EMERGENCY -> Icons.Default.LocalHospital
                                            LocationType.DRINKING_WATER -> Icons.Default.WaterDrop
                                            LocationType.BHANDARA_MAHAPRASAD -> Icons.Default.Restaurant
                                            LocationType.ACCOMMODATION -> Icons.Default.Hotel
                                            else -> Icons.Default.Place
                                        },
                                        contentDescription = null,
                                        tint = if (loc.isFreeService) VerifiedGreen else SaffronPrimary,
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = loc.nameEn,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = TextPrimary
                                )
                                Text(
                                    text = "${loc.type.labelEn} • ${loc.address}",
                                    fontSize = 11.sp,
                                    color = TextSecondary
                                )
                            }
                            Text(
                                text = "${loc.distanceKm} km",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = SaffronPrimary
                            )
                        }
                    }
                }
            }
        }

        // Bottom Selected Location Bottom Card
        selectedLocation?.let { loc ->
            Card(
                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
                colors = CardDefaults.cardColors(containerColor = IvorySurface),
                elevation = CardDefaults.cardElevation(6.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 70.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = loc.nameEn,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary
                            )
                            Text(
                                text = loc.nameMr,
                                fontSize = 12.sp,
                                color = MaroonPrimary
                            )
                        }
                        if (loc.isFreeService) {
                            Surface(
                                shape = RoundedCornerShape(4.dp),
                                color = Color(0xFFE8F5E9)
                            ) {
                                Text(
                                    text = "FREE SEVA",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = VerifiedGreen,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "${loc.address} (${loc.distanceKm} km away)",
                        fontSize = 12.sp,
                        color = TextSecondary
                    )
                    Text(
                        text = "Hours: ${loc.operatingHours} • Crowd Status: ${loc.crowdLevel}",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium,
                        color = if (loc.crowdLevel == "Very High") UrgentRed else TextPrimary
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = { onGetDirections(loc) },
                            colors = ButtonDefaults.buttonColors(containerColor = SaffronPrimary),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(Icons.Default.Directions, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Get Directions", fontSize = 12.sp)
                        }

                        OutlinedButton(
                            onClick = { onLocationCall(loc.contactPhone) },
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(Icons.Default.Call, contentDescription = null, modifier = Modifier.size(16.dp), tint = TextPrimary)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Call Help Desk", fontSize = 12.sp, color = TextPrimary)
                        }
                    }
                }
            }
        }
    }
}
