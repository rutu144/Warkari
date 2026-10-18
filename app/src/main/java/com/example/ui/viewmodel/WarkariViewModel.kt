package com.example.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.model.*
import com.example.data.repository.WarkariRepository
import com.example.data.repository.WarkariSampleRepositoryImpl
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class WarkariViewModel(
    private val repository: WarkariRepository = WarkariSampleRepositoryImpl()
) : ViewModel() {

    // Filter states
    val searchQuery = MutableStateFlow("")
    val selectedCategory = MutableStateFlow<EventCategory?>(null)
    val selectedCity = MutableStateFlow("All Maharashtra")
    val onlyVerified = MutableStateFlow(false)
    val selectedMapFilter = MutableStateFlow<LocationType?>(null)

    // User & core domain flows
    val currentUser: StateFlow<User> = repository.getCurrentUser()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), User())

    val liveStreams: StateFlow<List<LiveStream>> = repository.getLiveStreams()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val posts: StateFlow<List<Post>> = repository.getPosts()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val accommodations: StateFlow<List<Accommodation>> = repository.getAccommodations()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val foodAndServices: StateFlow<List<FoodOrEssentialService>> = repository.getFoodAndServices()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val vendors: StateFlow<List<VendorBusiness>> = repository.getVendors()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val serviceProviders: StateFlow<List<EventServiceProvider>> = repository.getServiceProviders()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Active selections
    val selectedEvent = MutableStateFlow<Event?>(null)
    val selectedLiveStream = MutableStateFlow<LiveStream?>(null)
    val snackbarMessage = MutableStateFlow<String?>(null)

    // Filtered Events Flow
    val filteredEvents: StateFlow<List<Event>> = combine(
        repository.getEvents(),
        searchQuery,
        selectedCategory,
        selectedCity,
        onlyVerified
    ) { allEvents, query, cat, city, verifiedOnly ->
        allEvents.filter { event ->
            val matchesQuery = query.isBlank() ||
                    event.titleEn.contains(query, ignoreCase = true) ||
                    event.titleMr.contains(query, ignoreCase = true) ||
                    event.city.contains(query, ignoreCase = true) ||
                    event.organizerName.contains(query, ignoreCase = true) ||
                    event.venue.contains(query, ignoreCase = true)

            val matchesCat = cat == null || event.category == cat

            val matchesCity = city == "All Maharashtra" ||
                    event.city.contains(city, ignoreCase = true) ||
                    (city == "Pandharpur" && event.isPalkhiRoute)

            val matchesVerified = !verifiedOnly || event.isVerified

            matchesQuery && matchesCat && matchesCity && matchesVerified
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Filtered Map Locations Flow
    val filteredLocations: StateFlow<List<LocationPoint>> = combine(
        repository.getLocations(),
        selectedMapFilter,
        selectedCity
    ) { allLocations, filterType, city ->
        allLocations.filter { loc ->
            val matchesType = filterType == null || loc.type == filterType
            val matchesCity = city == "All Maharashtra" || loc.city.contains(city, ignoreCase = true)
            matchesType && matchesCity
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        // Pre-select first live stream as active
        viewModelScope.launch {
            repository.getLiveStreams().collect { streams ->
                if (selectedLiveStream.value == null && streams.isNotEmpty()) {
                    selectedLiveStream.value = streams.first()
                }
            }
        }
    }

    // Actions
    fun setSearchQuery(query: String) {
        searchQuery.value = query
    }

    fun setSelectedCategory(category: EventCategory?) {
        selectedCategory.value = category
    }

    fun setSelectedCity(city: String) {
        selectedCity.value = city
    }

    fun setOnlyVerified(verified: Boolean) {
        onlyVerified.value = verified
    }

    fun setMapFilter(type: LocationType?) {
        selectedMapFilter.value = type
    }

    fun selectEvent(event: Event?) {
        selectedEvent.value = event
    }

    fun selectLiveStream(stream: LiveStream?) {
        selectedLiveStream.value = stream
    }

    fun toggleSaveEvent(eventId: String) {
        repository.toggleSaveEvent(eventId)
        val isSaved = currentUser.value.savedEventIds.contains(eventId)
        snackbarMessage.value = if (!isSaved) "Event saved to your pilgrimage schedule" else "Event removed from saved"
    }

    fun toggleFollowOrg(orgId: String) {
        repository.toggleFollowOrg(orgId)
        val isFollowed = currentUser.value.followedOrgIds.contains(orgId)
        snackbarMessage.value = if (!isFollowed) "Following Sansthan updates" else "Unfollowed Sansthan"
    }

    fun toggleLikePost(postId: String) {
        repository.toggleLikePost(postId)
    }

    fun switchRole(role: UserRole) {
        repository.switchUserRole(role)
        snackbarMessage.value = "Switched to ${role.label} mode"
    }

    fun switchLanguage(language: Language) {
        repository.switchLanguage(language)
        snackbarMessage.value = "Language changed to ${language.label}"
    }

    fun submitBookingEnquiry(
        targetTitle: String,
        targetType: String,
        name: String,
        phone: String,
        pilgrimsCount: Int,
        visitDate: String,
        notes: String
    ) {
        val enquiry = BookingEnquiry(
            id = "enq_${System.currentTimeMillis()}",
            targetTitle = targetTitle,
            targetType = targetType,
            userFullName = name,
            phone = phone,
            pilgrimsCount = pilgrimsCount,
            visitDate = visitDate,
            specialNotes = notes
        )
        repository.addBookingEnquiry(enquiry)
        snackbarMessage.value = "Booking inquiry submitted for $targetTitle (Prototype demo status)"
    }

    fun publishAnnouncement(eventId: String, title: String, message: String, isUrgent: Boolean) {
        repository.addAnnouncement(eventId, title, message, isUrgent)
        snackbarMessage.value = "Announcement broadcasted successfully to all pilgrims"
    }

    fun createOrganizerEvent(
        titleEn: String,
        titleMr: String,
        descEn: String,
        descMr: String,
        category: EventCategory,
        city: String,
        venue: String,
        startDate: String,
        endDate: String,
        contactPhone: String
    ) {
        val newEvent = Event(
            id = "event_${System.currentTimeMillis()}",
            titleEn = titleEn,
            titleMr = if (titleMr.isNotBlank()) titleMr else titleEn,
            descriptionEn = descEn,
            descriptionMr = if (descMr.isNotBlank()) descMr else descEn,
            category = category,
            city = city,
            venue = venue,
            address = "$venue, $city",
            lat = 18.5204,
            lng = 73.8567,
            startDate = startDate,
            endDate = endDate,
            startTime = "06:00 AM",
            endTime = "10:00 PM",
            organizerName = currentUser.value.name + " Committee",
            isVerified = false,
            lastUpdated = "Just now",
            schedule = listOf(
                ScheduleItem("ns1", "07:00 AM", "Mangala Aarti", "मंगला आरती", "Morning pooja", venue),
                ScheduleItem("ns2", "06:00 PM", "Bhajan & Kirtan", "भजन व कीर्तन", "Evening spiritual session", venue)
            ),
            announcements = emptyList(),
            liveStreamAvailable = false,
            contactPhone = contactPhone
        )
        repository.createEvent(newEvent)
        snackbarMessage.value = "Event '$titleEn' submitted for review and published locally"
    }

    fun createPost(textEn: String, textMr: String, tag: String) {
        repository.addPost(textEn, textMr, tag)
        snackbarMessage.value = "Devotional post shared to community feed"
    }

    fun clearSnackbar() {
        snackbarMessage.value = null
    }

    fun showMessage(msg: String) {
        snackbarMessage.value = msg
    }
}
