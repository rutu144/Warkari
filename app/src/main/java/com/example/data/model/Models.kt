package com.example.data.model

enum class UserRole(val label: String, val marathiLabel: String) {
    PILGRIM("Devotee / Pilgrim", "वारकरी / भाविक"),
    ORGANIZER("Event Organizer / Mandal", "सोहळा / मंडळ आयोजक"),
    VENDOR("Local Stall & Vendor", "स्थानिक व्यावसायिक / दुकानदार"),
    SERVICE_PROVIDER("Event Service Provider", "कार्यक्रम सेवा पुरवठादार")
}

enum class Language(val code: String, val label: String) {
    ENGLISH("en", "English"),
    MARATHI("mr", "मराठी"),
    HINDI("hi", "हिन्दी")
}

enum class EventCategory(val englishName: String, val marathiName: String) {
    WARI_PALKHI("Wari & Palkhi", "वारी व पालखी"),
    GANPATI_MANDAL("Ganpati Mandal", "गणेशोत्सव मंडळ"),
    KUMBH_MELA("Kumbh Mela", "कुंभमेळा"),
    TEMPLE_YATRA("Temple Yatra", "मंदिर जत्रा व सोहळा"),
    PRAVACHAN_KIRTAN("Kirtan & Pravachan", "कीर्तन व प्रवचन"),
    MAHAPRASAD_BHANDARA("Bhandara & Annadaan", "महाप्रसाद व अन्नदान"),
    CULTURAL_PROGRAM("Cultural Sangeet", "सांस्कृतिक व संगीत")
}

data class ScheduleItem(
    val id: String,
    val time: String,
    val titleEn: String,
    val titleMr: String,
    val description: String,
    val locationName: String
)

data class Announcement(
    val id: String,
    val title: String,
    val message: String,
    val timestamp: String,
    val isUrgent: Boolean,
    val authorName: String
)

data class Event(
    val id: String,
    val titleEn: String,
    val titleMr: String,
    val descriptionEn: String,
    val descriptionMr: String,
    val category: EventCategory,
    val city: String,
    val venue: String,
    val address: String,
    val lat: Double,
    val lng: Double,
    val startDate: String,
    val endDate: String,
    val startTime: String,
    val endTime: String,
    val organizerName: String,
    val isVerified: Boolean,
    val lastUpdated: String,
    val schedule: List<ScheduleItem>,
    val announcements: List<Announcement>,
    val liveStreamAvailable: Boolean,
    val liveStreamId: String? = null,
    val isFeatured: Boolean = false,
    val savesCount: Int = 120,
    val viewsCount: Int = 1450,
    val isPalkhiRoute: Boolean = false,
    val dindiNumber: String? = null,
    val contactPhone: String = "1800-233-0000"
)

data class Post(
    val id: String,
    val authorName: String,
    val authorRole: String,
    val isVerified: Boolean,
    val eventTag: String,
    val textEn: String,
    val textMr: String,
    val timestamp: String,
    val likesCount: Int,
    val commentsCount: Int,
    val mediaDescription: String,
    val isLiked: Boolean = false,
    val isSaved: Boolean = false
)

data class LiveStream(
    val id: String,
    val templeName: String,
    val eventName: String,
    val organizerName: String,
    val streamTitle: String,
    val streamUrl: String,
    val isLive: Boolean,
    val viewerCountSample: Int,
    val scheduleNotice: String,
    val resolutionTag: String = "Official 1080p HD",
    val isAuthorized: Boolean = true,
    val audioChant: String = "राम कृष्ण हरी"
)

enum class LocationType(val labelEn: String, val labelMr: String) {
    TEMPLE_MANDAL("Temple / Mandal", "मंदिर / मंडळ"),
    WARI_ROUTE_POINT("Wari Palkhi Stop", "पालखी मुक्काम तळ"),
    BHANDARA_MAHAPRASAD("Free Bhandara / Food", "मोफत महाप्रसाद / अन्नछत्र"),
    ACCOMMODATION("Dharamshala / Stay", "धर्मशाळा / भक्तनिवास"),
    FOOD_STALL("Food & Tea Stall", "खाद्यपदार्थ व चहा स्टॉल"),
    MEDICAL_EMERGENCY("24x7 Medical Tent", "तातडीची वैद्यकीय मदत"),
    DRINKING_WATER("Drinking Water Tanker", "पिण्याच्या पाण्याचे टँकर"),
    PUBLIC_TOILET("Bio-Toilet Facility", "स्वच्छतागृह सोय"),
    TRANSPORT_HUB("Pilgrim Bus / Train", "बसस्थानक / रेल्वे"),
    TEMPORARY_STALL("Puja & Cultural Stall", "पूजा साहित्य व दुकान")
}

data class LocationPoint(
    val id: String,
    val nameEn: String,
    val nameMr: String,
    val type: LocationType,
    val address: String,
    val city: String,
    val lat: Double,
    val lng: Double,
    val distanceKm: Double,
    val isFreeService: Boolean,
    val contactPhone: String,
    val operatingHours: String,
    val crowdLevel: String, // "Low", "Moderate", "High", "Very High"
    val isVerified: Boolean
)

enum class AccommodationType(val title: String) {
    DHARAMSHALA("Dharamshala (धर्मशाळा)"),
    ASHRAM("Ashram Bhakt Niwas (आश्रम)"),
    GUEST_HOUSE("Pilgrim Guest House"),
    HOTEL("Hotel / Lodge"),
    COMMUNITY_HALL("Community Pandal / Hall"),
    TEMPORARY_TENT("Palkhi Temporary Shelter")
}

data class Accommodation(
    val id: String,
    val name: String,
    val type: AccommodationType,
    val locationName: String,
    val city: String,
    val pricePerNight: String,
    val facilities: List<String>,
    val contactPhone: String,
    val availableRooms: Int,
    val isVerified: Boolean,
    val rulesNote: String
)

data class FoodOrEssentialService(
    val id: String,
    val name: String,
    val category: String,
    val isFree: Boolean,
    val location: String,
    val city: String,
    val operatingHours: String,
    val description: String,
    val contactPhone: String,
    val emergencyLevel: String = "Normal"
)

data class VendorBusiness(
    val id: String,
    val name: String,
    val category: String,
    val eventAssociated: String,
    val stallNumber: String,
    val location: String,
    val dates: String,
    val hours: String,
    val products: List<String>,
    val contactPhone: String,
    val offerText: String,
    val isSponsored: Boolean = false,
    val isVerified: Boolean = true
)

data class EventServiceProvider(
    val id: String,
    val name: String,
    val serviceCategory: String, // Pandal, Sound, Lighting, Catering, etc.
    val serviceArea: String,
    val startingPriceEstimate: String,
    val description: String,
    val contactPhone: String,
    val isAvailable: Boolean,
    val isVerified: Boolean,
    val pastEventsHandled: String
)

data class BookingEnquiry(
    val id: String,
    val targetTitle: String,
    val targetType: String,
    val userFullName: String,
    val phone: String,
    val pilgrimsCount: Int,
    val visitDate: String,
    val specialNotes: String,
    val status: String = "Request Received (Sample Prototype Mode)"
)

data class User(
    val id: String = "user_001",
    val name: String = "Santosh Kadam",
    val email: String = "santosh.warkari@gmail.com",
    val phone: String = "+91 98220 12345",
    val role: UserRole = UserRole.PILGRIM,
    val preferredLanguage: Language = Language.ENGLISH,
    val savedEventIds: Set<String> = setOf("wari_01", "ganpati_01"),
    val followedOrgIds: Set<String> = setOf("org_vitthal_pandharpur", "org_dagdusheth_pune"),
    val preferredCategories: Set<EventCategory> = setOf(EventCategory.WARI_PALKHI, EventCategory.GANPATI_MANDAL),
    val notificationsEnabled: Boolean = true
)
