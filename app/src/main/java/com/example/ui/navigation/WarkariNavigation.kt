package com.example.ui.navigation

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.data.model.*
import com.example.ui.components.WarkariHeader
import com.example.ui.screens.*
import com.example.ui.theme.*
import com.example.ui.viewmodel.WarkariViewModel
import kotlinx.coroutines.launch

enum class MainDestination(val title: String, val icon: androidx.compose.ui.graphics.vector.ImageVector) {
    HOME("Home", Icons.Default.Home),
    DISCOVER("Discover", Icons.Default.Search),
    MAP("Map", Icons.Default.Place),
    LIVE("Live", Icons.Default.LiveTv),
    PROFILE("Profile", Icons.Default.Person)
}

enum class SubDestination {
    NONE,
    EVENT_DETAIL,
    STAY_AND_FOOD,
    MARKETPLACE,
    SOCIAL_FEED,
    ORGANIZER_DASHBOARD
}

@Composable
fun WarkariMainScaffold(
    viewModel: WarkariViewModel = viewModel()
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    // State flows
    val user by viewModel.currentUser.collectAsStateWithLifecycle()
    val events by viewModel.filteredEvents.collectAsStateWithLifecycle()
    val allEvents by viewModel.filteredEvents.collectAsStateWithLifecycle()
    val liveStreams by viewModel.liveStreams.collectAsStateWithLifecycle()
    val posts by viewModel.posts.collectAsStateWithLifecycle()
    val locations by viewModel.filteredLocations.collectAsStateWithLifecycle()
    val accommodations by viewModel.accommodations.collectAsStateWithLifecycle()
    val foodAndServices by viewModel.foodAndServices.collectAsStateWithLifecycle()
    val vendors by viewModel.vendors.collectAsStateWithLifecycle()
    val serviceProviders by viewModel.serviceProviders.collectAsStateWithLifecycle()

    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val selectedCategory by viewModel.selectedCategory.collectAsStateWithLifecycle()
    val selectedCity by viewModel.selectedCity.collectAsStateWithLifecycle()
    val onlyVerified by viewModel.onlyVerified.collectAsStateWithLifecycle()
    val selectedMapFilter by viewModel.selectedMapFilter.collectAsStateWithLifecycle()
    val selectedEvent by viewModel.selectedEvent.collectAsStateWithLifecycle()
    val selectedLiveStream by viewModel.selectedLiveStream.collectAsStateWithLifecycle()
    val snackbarMessage by viewModel.snackbarMessage.collectAsStateWithLifecycle()

    // Navigation states
    var currentTab by remember { mutableStateOf(MainDestination.HOME) }
    var currentSubDestination by remember { mutableStateOf(SubDestination.NONE) }

    // Listen to snackbar messages
    LaunchedEffect(snackbarMessage) {
        snackbarMessage?.let { msg ->
            snackbarHostState.showSnackbar(msg)
            viewModel.clearSnackbar()
        }
    }

    // Back handler for sub-destinations
    BackHandler(enabled = currentSubDestination != SubDestination.NONE || currentTab != MainDestination.HOME) {
        if (currentSubDestination != SubDestination.NONE) {
            currentSubDestination = SubDestination.NONE
        } else {
            currentTab = MainDestination.HOME
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            if (currentSubDestination == SubDestination.NONE) {
                WarkariHeader(
                    selectedCity = selectedCity,
                    onCitySelected = { viewModel.setSelectedCity(it) },
                    currentLanguage = user.preferredLanguage,
                    onLanguageClick = {
                        val nextLang = when (user.preferredLanguage) {
                            Language.ENGLISH -> Language.MARATHI
                            Language.MARATHI -> Language.HINDI
                            Language.HINDI -> Language.ENGLISH
                        }
                        viewModel.switchLanguage(nextLang)
                    },
                    currentRole = user.role,
                    onRoleClick = {
                        val nextRole = when (user.role) {
                            UserRole.PILGRIM -> UserRole.ORGANIZER
                            UserRole.ORGANIZER -> UserRole.VENDOR
                            UserRole.VENDOR -> UserRole.SERVICE_PROVIDER
                            UserRole.SERVICE_PROVIDER -> UserRole.PILGRIM
                        }
                        viewModel.switchRole(nextRole)
                    }
                )
            }
        },
        bottomBar = {
            NavigationBar(
                containerColor = IvorySurface,
                tonalElevation = 8.dp
            ) {
                MainDestination.values().forEach { destination ->
                    val isSelected = currentTab == destination && currentSubDestination == SubDestination.NONE
                    NavigationBarItem(
                        selected = isSelected,
                        onClick = {
                            currentTab = destination
                            currentSubDestination = SubDestination.NONE
                        },
                        icon = {
                            Icon(
                                imageVector = destination.icon,
                                contentDescription = destination.title
                            )
                        },
                        label = {
                            Text(
                                text = destination.title,
                                fontSize = 11.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = SaffronPrimary,
                            selectedTextColor = SaffronPrimary,
                            indicatorColor = SaffronContainer,
                            unselectedIconColor = TextSecondary,
                            unselectedTextColor = TextSecondary
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when {
                currentSubDestination == SubDestination.EVENT_DETAIL && selectedEvent != null -> {
                    EventDetailScreen(
                        event = selectedEvent!!,
                        isSaved = user.savedEventIds.contains(selectedEvent!!.id),
                        isFollowed = user.followedOrgIds.contains(selectedEvent!!.organizerName),
                        onBackClick = { currentSubDestination = SubDestination.NONE },
                        onSaveClick = { viewModel.toggleSaveEvent(selectedEvent!!.id) },
                        onFollowOrgClick = { viewModel.toggleFollowOrg(selectedEvent!!.organizerName) },
                        onShareClick = {
                            viewModel.showMessage("Sharing ${selectedEvent!!.titleEn} with devotees...")
                        },
                        onGetDirectionsClick = {
                            try {
                                val uri = Uri.parse("geo:${selectedEvent!!.lat},${selectedEvent!!.lng}?q=${Uri.encode(selectedEvent!!.venue)}")
                                val intent = Intent(Intent.ACTION_VIEW, uri)
                                context.startActivity(intent)
                            } catch (e: Exception) {
                                viewModel.showMessage("Coordinates: ${selectedEvent!!.lat}, ${selectedEvent!!.lng} (${selectedEvent!!.venue})")
                            }
                        },
                        onCallOrganizerClick = {
                            try {
                                val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${selectedEvent!!.contactPhone}"))
                                context.startActivity(intent)
                            } catch (e: Exception) {
                                viewModel.showMessage("Organizer Helpline: ${selectedEvent!!.contactPhone}")
                            }
                        },
                        onWatchLiveClick = {
                            currentSubDestination = SubDestination.NONE
                            currentTab = MainDestination.LIVE
                        }
                    )
                }

                currentSubDestination == SubDestination.STAY_AND_FOOD -> {
                    StayAndFoodScreens(
                        accommodations = accommodations,
                        foodAndServices = foodAndServices,
                        onCallClick = { phone ->
                            try {
                                val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phone"))
                                context.startActivity(intent)
                            } catch (e: Exception) {
                                viewModel.showMessage("Helpline: $phone")
                            }
                        },
                        onSubmitEnquiry = { targetTitle, targetType, name, phone, count, date, notes ->
                            viewModel.submitBookingEnquiry(targetTitle, targetType, name, phone, count, date, notes)
                        }
                    )
                }

                currentSubDestination == SubDestination.MARKETPLACE -> {
                    MarketplaceAndServicesScreens(
                        vendors = vendors,
                        serviceProviders = serviceProviders,
                        onCallClick = { phone ->
                            try {
                                val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phone"))
                                context.startActivity(intent)
                            } catch (e: Exception) {
                                viewModel.showMessage("Helpline: $phone")
                            }
                        },
                        onSubmitQuotation = { targetTitle, targetType, name, phone, count, date, notes ->
                            viewModel.submitBookingEnquiry(targetTitle, targetType, name, phone, count, date, notes)
                        }
                    )
                }

                currentSubDestination == SubDestination.SOCIAL_FEED -> {
                    SocialFeedScreen(
                        posts = posts,
                        onLikeClick = { viewModel.toggleLikePost(it) },
                        onShareClick = {
                            viewModel.showMessage("Post link copied to clipboard")
                        },
                        onReportClick = {
                            viewModel.showMessage("Report submitted for review. Thank you for keeping Warkari safe.")
                        },
                        onCreatePost = { en, mr, tag ->
                            viewModel.createPost(en, mr, tag)
                        }
                    )
                }

                currentSubDestination == SubDestination.ORGANIZER_DASHBOARD -> {
                    OrganizerDashboardScreen(
                        managedEvents = allEvents,
                        onBroadcastAnnouncement = { eventId, title, message, isUrgent ->
                            viewModel.publishAnnouncement(eventId, title, message, isUrgent)
                        },
                        onCreateEvent = { titleEn, titleMr, descEn, descMr, category, city, venue, start, end, phone ->
                            viewModel.createOrganizerEvent(titleEn, titleMr, descEn, descMr, category, city, venue, start, end, phone)
                        }
                    )
                }

                // Primary Bottom Bar Destinations
                currentTab == MainDestination.HOME -> {
                    HomeScreen(
                        events = events,
                        liveStreams = liveStreams,
                        savedEventIds = user.savedEventIds,
                        selectedCategory = selectedCategory,
                        selectedCity = selectedCity,
                        onCategorySelected = { viewModel.setSelectedCategory(it) },
                        onEventClick = {
                            viewModel.selectEvent(it)
                            currentSubDestination = SubDestination.EVENT_DETAIL
                        },
                        onSaveEventClick = { viewModel.toggleSaveEvent(it) },
                        onLiveStreamClick = {
                            viewModel.selectLiveStream(it)
                            currentTab = MainDestination.LIVE
                        },
                        onNavigateToDiscover = { currentTab = MainDestination.DISCOVER },
                        onNavigateToMap = { currentTab = MainDestination.MAP },
                        onNavigateToStayFood = { currentSubDestination = SubDestination.STAY_AND_FOOD },
                        onNavigateToMarketplace = { currentSubDestination = SubDestination.MARKETPLACE },
                        onNavigateToSocial = { currentSubDestination = SubDestination.SOCIAL_FEED }
                    )
                }

                currentTab == MainDestination.DISCOVER -> {
                    DiscoverScreen(
                        events = events,
                        searchQuery = searchQuery,
                        onSearchQueryChange = { viewModel.setSearchQuery(it) },
                        selectedCategory = selectedCategory,
                        onCategoryChange = { viewModel.setSelectedCategory(it) },
                        onlyVerified = onlyVerified,
                        onVerifiedToggle = { viewModel.setOnlyVerified(it) },
                        savedEventIds = user.savedEventIds,
                        onSaveEventClick = { viewModel.toggleSaveEvent(it) },
                        onEventClick = {
                            viewModel.selectEvent(it)
                            currentSubDestination = SubDestination.EVENT_DETAIL
                        }
                    )
                }

                currentTab == MainDestination.MAP -> {
                    MapScreen(
                        locations = locations,
                        selectedType = selectedMapFilter,
                        onTypeSelect = { viewModel.setMapFilter(it) },
                        onLocationCall = { phone ->
                            try {
                                val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phone"))
                                context.startActivity(intent)
                            } catch (e: Exception) {
                                viewModel.showMessage("Helpline: $phone")
                            }
                        },
                        onGetDirections = { loc ->
                            try {
                                val uri = Uri.parse("geo:${loc.lat},${loc.lng}?q=${Uri.encode(loc.nameEn)}")
                                val intent = Intent(Intent.ACTION_VIEW, uri)
                                context.startActivity(intent)
                            } catch (e: Exception) {
                                viewModel.showMessage("Directions: ${loc.lat}, ${loc.lng} (${loc.nameEn})")
                            }
                        }
                    )
                }

                currentTab == MainDestination.LIVE -> {
                    LiveDarshanScreen(
                        liveStreams = liveStreams,
                        selectedStream = selectedLiveStream,
                        onStreamSelect = { viewModel.selectLiveStream(it) },
                        onChantClick = {
                            viewModel.showMessage("राम कृष्ण हरी! Harinaam chant registered.")
                        }
                    )
                }

                currentTab == MainDestination.PROFILE -> {
                    ProfileScreen(
                        user = user,
                        savedEvents = allEvents.filter { user.savedEventIds.contains(it.id) },
                        onRoleChange = { newRole ->
                            viewModel.switchRole(newRole)
                            if (newRole == UserRole.ORGANIZER) {
                                currentSubDestination = SubDestination.ORGANIZER_DASHBOARD
                            }
                        },
                        onLanguageChange = { viewModel.switchLanguage(it) },
                        onEventClick = {
                            viewModel.selectEvent(it)
                            currentSubDestination = SubDestination.EVENT_DETAIL
                        },
                        onEmergencyCall = { phone ->
                            try {
                                val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phone"))
                                context.startActivity(intent)
                            } catch (e: Exception) {
                                viewModel.showMessage("Emergency Helpline: $phone")
                            }
                        }
                    )
                }
            }
        }
    }
}
