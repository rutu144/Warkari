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
import com.example.data.model.Accommodation
import com.example.data.model.FoodOrEssentialService
import com.example.ui.components.BookingEnquiryDialog
import com.example.ui.components.PrototypeNotice
import com.example.ui.components.VerificationBadge
import com.example.ui.theme.*

@Composable
fun StayAndFoodScreens(
    accommodations: List<Accommodation>,
    foodAndServices: List<FoodOrEssentialService>,
    onCallClick: (String) -> Unit,
    onSubmitEnquiry: (targetTitle: String, targetType: String, name: String, phone: String, count: Int, date: String, notes: String) -> Unit
) {
    var selectedTab by remember { mutableStateOf(0) }
    var enquiryTarget by remember { mutableStateOf<Accommodation?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(IvoryBackground)
    ) {
        // Tab Header
        Surface(color = IvorySurface, shadowElevation = 2.dp) {
            TabRow(
                selectedTabIndex = selectedTab,
                containerColor = IvorySurface,
                contentColor = SaffronPrimary
            ) {
                Tab(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    text = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Hotel, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Accommodations", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        }
                    }
                )
                Tab(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    text = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Restaurant, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Food & Essentials", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        }
                    }
                )
            }
        }

        if (selectedTab == 0) {
            // Accommodations Tab
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp),
                modifier = Modifier.padding(bottom = 70.dp)
            ) {
                item {
                    Text(
                        text = "Dharamshalas, Ashrams & Pilgrim Lodges",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                }

                items(accommodations) { acc ->
                    Card(
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = IvorySurface),
                        elevation = CardDefaults.cardElevation(2.dp),
                        border = BorderStroke(1.dp, Color(0xFFE2D6C6)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.Top
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = acc.name,
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = TextPrimary
                                    )
                                    Text(
                                        text = "${acc.type.title} • ${acc.locationName}",
                                        fontSize = 11.sp,
                                        color = TextSecondary
                                    )
                                }
                                VerificationBadge(isVerified = acc.isVerified)
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = acc.pricePerNight,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = MaroonPrimary
                                )
                                Surface(
                                    shape = RoundedCornerShape(4.dp),
                                    color = if (acc.availableRooms > 0) Color(0xFFE8F5E9) else Color(0xFFFFEBEE)
                                ) {
                                    Text(
                                        text = if (acc.availableRooms > 0) "${acc.availableRooms} beds available" else "Full / Waitlist",
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (acc.availableRooms > 0) VerifiedGreen else UrgentRed,
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            // Facilities chips
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                acc.facilities.take(3).forEach { fac ->
                                    Surface(
                                        shape = RoundedCornerShape(4.dp),
                                        color = IvorySurfaceVariant
                                    ) {
                                        Text(
                                            text = fac,
                                            fontSize = 9.sp,
                                            color = TextSecondary,
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = "Rule: ${acc.rulesNote}",
                                fontSize = 10.sp,
                                color = TextMuted,
                                lineHeight = 14.sp
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Button(
                                    onClick = { enquiryTarget = acc },
                                    colors = ButtonDefaults.buttonColors(containerColor = SaffronPrimary),
                                    shape = RoundedCornerShape(8.dp),
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text("Enquire / Book Bed", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                }

                                OutlinedButton(
                                    onClick = { onCallClick(acc.contactPhone) },
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Icon(Icons.Default.Call, contentDescription = "Call", tint = TextPrimary, modifier = Modifier.size(14.dp))
                                }
                            }
                        }
                    }
                }

                item {
                    PrototypeNotice(
                        message = "Booking enquiry is a local simulation. No booking or bed allocation is confirmed without official Sansthan receipt."
                    )
                }
            }
        } else {
            // Food & Essentials Tab
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp),
                modifier = Modifier.padding(bottom = 70.dp)
            ) {
                item {
                    Text(
                        text = "Free Bhandara, Community Kitchens & Emergency Seva",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                }

                items(foodAndServices) { item ->
                    Card(
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = IvorySurface),
                        elevation = CardDefaults.cardElevation(2.dp),
                        border = BorderStroke(1.dp, Color(0xFFE2D6C6)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.Top
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = item.name,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = TextPrimary
                                    )
                                    Text(
                                        text = "${item.category} • ${item.location}",
                                        fontSize = 11.sp,
                                        color = TextSecondary
                                    )
                                }

                                Surface(
                                    shape = RoundedCornerShape(4.dp),
                                    color = if (item.isFree) Color(0xFFE8F5E9) else SaffronContainer
                                ) {
                                    Text(
                                        text = if (item.isFree) "FREE SEVA" else "COMMERCIAL",
                                        fontSize = 9.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (item.isFree) VerifiedGreen else MaroonDark,
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = item.description,
                                fontSize = 11.sp,
                                color = TextPrimary,
                                lineHeight = 16.sp
                            )

                            Spacer(modifier = Modifier.height(6.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Hours: ${item.operatingHours}",
                                    fontSize = 10.sp,
                                    color = TextSecondary
                                )

                                TextButton(
                                    onClick = { onCallClick(item.contactPhone) }
                                ) {
                                    Icon(Icons.Default.Call, contentDescription = null, modifier = Modifier.size(14.dp), tint = SaffronPrimary)
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("Help Desk", fontSize = 11.sp, color = SaffronPrimary, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }
                }

                item {
                    PrototypeNotice(
                        message = "Free Mahaprasad and Bhandara points are open to all devotees without discrimination. Commercial stalls display standard rates."
                    )
                }
            }
        }
    }

    // Enquiry Dialog
    enquiryTarget?.let { acc ->
        BookingEnquiryDialog(
            targetTitle = acc.name,
            targetType = acc.type.title,
            onDismiss = { enquiryTarget = null },
            onSubmit = { name, phone, count, date, notes ->
                onSubmitEnquiry(acc.name, acc.type.title, name, phone, count, date, notes)
            }
        )
    }
}
