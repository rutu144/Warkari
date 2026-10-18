package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.example.data.model.EventServiceProvider
import com.example.data.model.VendorBusiness
import com.example.ui.components.BookingEnquiryDialog
import com.example.ui.components.PrototypeNotice
import com.example.ui.components.VerificationBadge
import com.example.ui.theme.*

@Composable
fun MarketplaceAndServicesScreens(
    vendors: List<VendorBusiness>,
    serviceProviders: List<EventServiceProvider>,
    onCallClick: (String) -> Unit,
    onSubmitQuotation: (targetTitle: String, targetType: String, name: String, phone: String, count: Int, date: String, notes: String) -> Unit
) {
    var selectedTab by remember { mutableStateOf(0) }
    var quotationTarget by remember { mutableStateOf<EventServiceProvider?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(IvoryBackground)
    ) {
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
                            Icon(Icons.Default.Store, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Temporary Stalls", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        }
                    }
                )
                Tab(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    text = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Build, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Event Services", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        }
                    }
                )
            }
        }

        if (selectedTab == 0) {
            // Temporary Stalls Tab
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp),
                modifier = Modifier.padding(bottom = 70.dp)
            ) {
                item {
                    Text(
                        text = "Pilgrimage Stalls & Local Festival Businesses",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                }

                items(vendors) { vendor ->
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
                                        text = vendor.name,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = TextPrimary
                                    )
                                    Text(
                                        text = "${vendor.category} • ${vendor.stallNumber}",
                                        fontSize = 11.sp,
                                        color = TextSecondary
                                    )
                                    Text(
                                        text = "${vendor.eventAssociated} (${vendor.location})",
                                        fontSize = 10.sp,
                                        color = MaroonPrimary
                                    )
                                }

                                if (vendor.isSponsored) {
                                    Surface(
                                        shape = RoundedCornerShape(4.dp),
                                        color = GoldAccent.copy(alpha = 0.2f)
                                    ) {
                                        Text(
                                            text = "SPONSORED",
                                            fontSize = 9.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = TextPrimary,
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            // Products
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                vendor.products.take(3).forEach { prod ->
                                    Surface(
                                        shape = RoundedCornerShape(4.dp),
                                        color = IvorySurfaceVariant
                                    ) {
                                        Text(
                                            text = prod,
                                            fontSize = 9.sp,
                                            color = TextSecondary,
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            // Offer banner
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = SaffronContainer.copy(alpha = 0.4f),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row(
                                    modifier = Modifier.padding(8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(Icons.Default.LocalOffer, contentDescription = null, tint = SaffronPrimary, modifier = Modifier.size(14.dp))
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = vendor.offerText,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = MaroonDark
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Dates: ${vendor.dates}",
                                    fontSize = 10.sp,
                                    color = TextMuted
                                )

                                Button(
                                    onClick = { onCallClick(vendor.contactPhone) },
                                    colors = ButtonDefaults.buttonColors(containerColor = SaffronPrimary),
                                    shape = RoundedCornerShape(8.dp),
                                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp)
                                ) {
                                    Icon(Icons.Default.Call, contentDescription = null, modifier = Modifier.size(14.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("Contact Stall", fontSize = 11.sp)
                                }
                            }
                        }
                    }
                }

                item {
                    PrototypeNotice(
                        message = "Local vendor listings support community livelihoods during yatras. Sponsored tags indicate verified partner placements."
                    )
                }
            }
        } else {
            // Event Services Tab
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp),
                modifier = Modifier.padding(bottom = 70.dp)
            ) {
                item {
                    Text(
                        text = "Mandap, Sound, Lights & Catering Providers",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                }

                items(serviceProviders) { sp ->
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
                                        text = sp.name,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = TextPrimary
                                    )
                                    Text(
                                        text = "${sp.serviceCategory} • Area: ${sp.serviceArea}",
                                        fontSize = 11.sp,
                                        color = TextSecondary
                                    )
                                }
                                VerificationBadge(isVerified = sp.isVerified)
                            }

                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = sp.description,
                                fontSize = 11.sp,
                                color = TextPrimary,
                                lineHeight = 16.sp
                            )

                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Estimate: ${sp.startingPriceEstimate}",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaroonPrimary
                            )
                            Text(
                                text = "Experience: ${sp.pastEventsHandled}",
                                fontSize = 10.sp,
                                color = TextMuted
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Button(
                                    onClick = { quotationTarget = sp },
                                    colors = ButtonDefaults.buttonColors(containerColor = SaffronPrimary),
                                    shape = RoundedCornerShape(8.dp),
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text("Request Quotation", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                }

                                OutlinedButton(
                                    onClick = { onCallClick(sp.contactPhone) },
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
                        message = "Quotations and provider contracts are conducted directly between event committees and service suppliers."
                    )
                }
            }
        }
    }

    // Quotation Enquiry Dialog
    quotationTarget?.let { sp ->
        BookingEnquiryDialog(
            targetTitle = sp.name,
            targetType = sp.serviceCategory,
            onDismiss = { quotationTarget = null },
            onSubmit = { name, phone, count, date, notes ->
                onSubmitQuotation(sp.name, sp.serviceCategory, name, phone, count, date, notes)
            }
        )
    }
}
