package com.example.data.repository

import com.example.data.model.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

interface WarkariRepository {
    fun getEvents(): Flow<List<Event>>
    fun getLiveStreams(): Flow<List<LiveStream>>
    fun getPosts(): Flow<List<Post>>
    fun getLocations(): Flow<List<LocationPoint>>
    fun getAccommodations(): Flow<List<Accommodation>>
    fun getFoodAndServices(): Flow<List<FoodOrEssentialService>>
    fun getVendors(): Flow<List<VendorBusiness>>
    fun getServiceProviders(): Flow<List<EventServiceProvider>>
    fun getCurrentUser(): Flow<User>
    
    fun toggleSaveEvent(eventId: String)
    fun toggleFollowOrg(orgId: String)
    fun toggleLikePost(postId: String)
    fun switchUserRole(role: UserRole)
    fun switchLanguage(language: Language)
    fun addBookingEnquiry(enquiry: BookingEnquiry)
    fun addAnnouncement(eventId: String, title: String, message: String, isUrgent: Boolean)
    fun createEvent(event: Event)
    fun addPost(textEn: String, textMr: String, eventTag: String)
}

class WarkariSampleRepositoryImpl : WarkariRepository {

    private val sampleEvents = listOf(
        Event(
            id = "wari_01",
            titleEn = "Sant Tukaram Maharaj Palkhi Sohla (Ashadhi Wari)",
            titleMr = "जगद्गुरु संत तुकाराम महाराज पालखी सोहळा (आषाढी वारी)",
            descriptionEn = "The divine pilgrimage walking from Dehu to Pandharpur carrying the sacred Padukas. Thousands of Warkaris walk with chanting of Ram Krishna Hari, accompanied by traditional Dindi formations and tal-mridanga.",
            descriptionMr = "देहू ते पंढरपूर पायी पालखी सोहळा. लाखो वारकरी टाळ-मृदुंगाच्या गजरात आणि 'ज्ञानोबा माऊली तुकाराम' नामाच्या जयघोषात पंढरीकडे प्रस्थान करतात.",
            category = EventCategory.WARI_PALKHI,
            city = "Pandharpur & Route",
            venue = "Sant Tukaram Maharaj Sansthan Dehu to Shri Vitthal Mandir Pandharpur",
            address = "Dehu - Pune - Saswad - Lonand - Phaltan - Natepute - Malshiras - Pandharpur",
            lat = 17.6775,
            lng = 75.3278,
            startDate = "22 June 2026",
            endDate = "12 July 2026",
            startTime = "05:00 AM",
            endTime = "09:00 PM Daily",
            organizerName = "Sant Tukaram Maharaj Sansthan, Dehu",
            isVerified = true,
            lastUpdated = "2 hours ago (Official Sansthan)",
            isFeatured = true,
            isPalkhiRoute = true,
            dindiNumber = "Dindi No. 1 to 327",
            contactPhone = "02114-269222",
            schedule = listOf(
                ScheduleItem("s1", "05:00 AM", "Kakad Aarti & Paduka Snan", "काकड आरती व पादुका स्नान", "Daily morning prayers at current camp tent", "Dehu / Palkhi Pandal"),
                ScheduleItem("s2", "06:30 AM", "Prasthan (Dindi March Begins)", "प्रस्थान व दिंडी वाटचाल", "Departure in disciplined Dindi sequences", "Palkhi Marg"),
                ScheduleItem("s3", "12:30 PM", "Duparcha Vishranti & Mahaprasad", "दुपारचा विसावा व महाप्रसाद", "Free lunch Seva by local villagers", "Village ZP School Grounds"),
                ScheduleItem("s4", "07:30 PM", "Samaj Aarti & Haripath", "समाज आरती व हरिपाठ", "All Dindis assemble for collective evening prayer", "Night Halt Pandal"),
                ScheduleItem("s5", "09:00 PM", "Akhand Kirtan & Bhajan", "रात्रीचे कीर्तन व जागर", "Devotional pravachan until midnight", "Mukam Ground")
            ),
            announcements = listOf(
                Announcement("a1", "Medical Tents at Dive Ghat Section", "24x7 108 ambulances and water tankers deployed between Hadapsar and Saswad.", "Today 08:30 AM", isUrgent = true, authorName = "District Medical Administration"),
                Announcement("a2", "Dindi Arrival at Pandharpur Pradakshina Marg", "Designated queue tokens for Palkhi darshan will be distributed near Wakhari.", "Yesterday", isUrgent = false, authorName = "Vitthal Mandir Committee")
            ),
            liveStreamAvailable = true,
            liveStreamId = "live_pandharpur",
            savesCount = 3840,
            viewsCount = 49200
        ),
        Event(
            id = "wari_02",
            titleEn = "Sant Dnyaneshwar Maharaj Palkhi Sohla (Alandi to Pandharpur)",
            titleMr = "संत श्रेष्ठ ज्ञानेश्वर माऊली पालखी सोहळा (आळंदी ते पंढरपूर)",
            descriptionEn = "Sacred Palkhi procession of Mauli Dnyaneshwar Maharaj moving across Pune, Dive Ghat, Jejuri, Taradgaon, and Vakhari with historic Ringan ceremonies at Velapur and Bajirao Vihir.",
            descriptionMr = "आळंदी ते पंढरी पायी वारी. माऊलींच्या पादुकांचा नयनरम्य सोहळा, जेजुरी, लोणंद आणि वेलापूर येथील प्रसिद्ध रिंगण सोहळा.",
            category = EventCategory.WARI_PALKHI,
            city = "Alandi to Pandharpur",
            venue = "Shri Kshetra Alandi Devachi",
            address = "Alandi - Pune - Saswad - Jejuri - Pandharpur",
            lat = 18.6775,
            lng = 73.8967,
            startDate = "24 June 2026",
            endDate = "12 July 2026",
            startTime = "04:30 AM",
            endTime = "10:00 PM",
            organizerName = "Alandi Devasthan Sansthan",
            isVerified = true,
            lastUpdated = "1 hour ago",
            isFeatured = true,
            isPalkhiRoute = true,
            contactPhone = "020-27185200",
            schedule = listOf(
                ScheduleItem("ds1", "05:00 AM", "Morning Abhishek", "महापूजा व अभिषेक", "Special Snan and pooja at temple", "Alandi Sansthan"),
                ScheduleItem("ds2", "04:00 PM", "First Ashwa Ringan", "अश्व रिंगण सोहळा", "Sacred horse circular run ceremony", "Velapur Ground")
            ),
            announcements = listOf(
                Announcement("da1", "Drinking Water Supply Points", "Continuous supply tankers available every 2km along the Highway.", "Today 06:00 AM", isUrgent = false, authorName = "Palkhi Seva Samiti")
            ),
            liveStreamAvailable = true,
            liveStreamId = "live_alandi",
            savesCount = 4210,
            viewsCount = 56000
        ),
        Event(
            id = "ganpati_01",
            titleEn = "Shrimant Dagdusheth Halwai Ganpati Ganeshotsav 2026",
            titleMr = "श्रीमंत दगडूशेठ हलवाई गणपती उत्सव २०२६ (पुणे)",
            descriptionEn = "Centenary celebration featuring grand replica of Somnath Temple pandal, Atharvashirsha mass chanting by 31,000 women on Rishi Panchami, and 24x7 Mukh Darshan.",
            descriptionMr = "पुण्यातील ऐतिहासिक व मानाचा गणेशोत्सव. भव्य सोमनाथ मंदिर देखावा आणि ऋषिपंचमी रोजी ३१,००० महिलांचे अथर्वशीर्ष पठण.",
            category = EventCategory.GANPATI_MANDAL,
            city = "Pune",
            venue = "Budhwar Peth, Pune",
            address = "Ganpati Bhavan, 250, Budhwar Peth, Shivaji Road, Pune 411002",
            lat = 18.5167,
            lng = 73.8562,
            startDate = "15 September 2026",
            endDate = "25 September 2026",
            startTime = "06:00 AM",
            endTime = "12:00 AM (Midnight)",
            organizerName = "Shrimant Dagdusheth Halwai Public Ganpati Trust",
            isVerified = true,
            lastUpdated = "30 mins ago",
            isFeatured = true,
            contactPhone = "020-24479222",
            schedule = listOf(
                ScheduleItem("g1", "06:00 AM", "Mangal Aarti & Suprabhat", "मंगल आरती", "Early morning traditional aarti", "Main Sanctum"),
                ScheduleItem("g2", "06:00 AM (Day 2)", "Rishi Panchami Atharvashirsha Chanting", "ऋषिपंचमी महाअथर्वशीर्ष पठण", "Mass chanting by 31,000 women", "Kakasaheb Gadgil Pustakalaya Chowk"),
                ScheduleItem("g3", "10:00 PM", "Shej Aarti", "शेजारती", "Night divine repose aarti", "Main Sanctum")
            ),
            announcements = listOf(
                Announcement("ga1", "Separate VIP and Senior Citizen Queues", "Senior citizens (above 65) can enter via Gate 3 with valid ID card.", "Today 10:00 AM", isUrgent = false, authorName = "Trust Security")
            ),
            liveStreamAvailable = true,
            liveStreamId = "live_dagdusheth",
            savesCount = 5890,
            viewsCount = 92000
        ),
        Event(
            id = "ganpati_02",
            titleEn = "Lalbaugcha Raja Sarvajanik Ganeshotsav",
            titleMr = "लालबागचा राजा सार्वजनिक गणेशोत्सव (मुंबई)",
            descriptionEn = "World-famous wish-fulfilling deity of Mumbai with iconic Navas queue and historic immersion procession at Girgaon Chowpatty.",
            descriptionMr = "नवसाला पावणारा मुंबईचा लाडका लालबागचा राजा. चरणस्पर्श आणि मुखदर्शन रांगा आणि भव्य विसर्जन सोहळा.",
            category = EventCategory.GANPATI_MANDAL,
            city = "Mumbai",
            venue = "Lalbaug Market, GD Ambekar Marg, Mumbai",
            address = "Lalbaug Market, Parel, Mumbai 400012",
            lat = 18.9912,
            lng = 72.8397,
            startDate = "15 September 2026",
            endDate = "25 September 2026",
            startTime = "24 Hours (Open Round the Clock)",
            endTime = "24 Hours",
            organizerName = "Lalbaugcha Raja Sarvajanik Ganeshotsav Mandal",
            isVerified = true,
            lastUpdated = "10 mins ago",
            isFeatured = true,
            contactPhone = "022-24701234",
            schedule = listOf(
                ScheduleItem("l1", "12:30 PM", "Maha Aarti", "दुपारची महाआरती", "Devotees mass aarti", "Main Pandal"),
                ScheduleItem("l2", "08:30 PM", "Sandhya Aarti", "संध्या आरती", "Celebrity & citizen collective prayers", "Main Pandal")
            ),
            announcements = listOf(
                Announcement("la1", "Footwear Deposit & Lost and Found Booth", "Free token system at Cotton Green ground entrance.", "Yesterday", isUrgent = false, authorName = "Lalbaug Mandal Volunteer Core")
            ),
            liveStreamAvailable = true,
            liveStreamId = "live_lalbaug",
            savesCount = 9400,
            viewsCount = 148000
        ),
        Event(
            id = "kumbh_01",
            titleEn = "Nashik-Trimbakeshwar Kumbh Parvani & Godavari Maha Aarti",
            titleMr = "नाशिक-त्र्यंबकेश्वर कुंभपर्वणी व गोदावरी महाआरती",
            descriptionEn = "Holy dip at Ramkund and Kushavarta Kund. Akhada Peshwai procession of Sadhus and Mahants, accompanied by sacred Vedic rituals and evening Godavari Deepotsav.",
            descriptionMr = "रामकुंड व कुशावर्त तीरावर शाही स्नान पर्वणी. साधु-संतांची पेशवाई मिरवणूक आणि भव्य गोदावरी दीप प्रज्वलन.",
            category = EventCategory.KUMBH_MELA,
            city = "Nashik & Trimbakeshwar",
            venue = "Ramkund Nashik & Kushavarta Kund Trimbak",
            address = "Panchavati, Nashik / Trimbakeshwar Jyotirlinga, Maharashtra",
            lat = 19.9380,
            lng = 73.5307,
            startDate = "01 October 2026",
            endDate = "15 November 2026",
            startTime = "03:30 AM",
            endTime = "11:00 PM",
            organizerName = "Nashik Municipal Corporation & Akhada Parishad",
            isVerified = true,
            lastUpdated = "5 hours ago",
            isFeatured = true,
            contactPhone = "0253-2575631",
            schedule = listOf(
                ScheduleItem("k1", "04:00 AM", "Shahi Snan (Royal Dip)", "शाही स्नान पर्वणी", "Aura of holy dip for Akhadas and pilgrims", "Ramkund Ghat"),
                ScheduleItem("k2", "07:00 PM", "Godavari Maha Aarti", "गोदावरी महाआरती", "108 lamps river bank grand aarti", "Panchavati Ramkund")
            ),
            announcements = listOf(
                Announcement("ka1", "Special Ghat Shuttle Buses", "Free shuttle buses from Tapovan parking to Ramkund every 10 mins.", "Today 07:00 AM", isUrgent = true, authorName = "NMC Transport Cell")
            ),
            liveStreamAvailable = true,
            liveStreamId = "live_nashik",
            savesCount = 3120,
            viewsCount = 41000
        ),
        Event(
            id = "yatra_01",
            titleEn = "Shri Ambabai Mahalakshmi Kiranotsav Sohla",
            titleMr = "करवीर निवासिनी श्री अंबाबाई महालक्ष्मी किरणोत्सव सोहळा",
            descriptionEn = "Ancient architectural marvel when the rays of setting sun illuminate the feet, chest, and face of Goddess Mahalakshmi inside the inner sanctum.",
            descriptionMr = "अस्ताला जाणाऱ्या सूर्यकिरणांनी देवीच्या चरण, हृदय व मुखकमलावर होणारा सुवर्ण किरणोत्सव.",
            category = EventCategory.TEMPLE_YATRA,
            city = "Kolhapur",
            venue = "Shri Mahalakshmi Ambabai Temple",
            address = "Bhavani Mandap Road, Kolhapur 416002",
            lat = 16.7050,
            lng = 74.2433,
            startDate = "09 November 2026",
            endDate = "13 November 2026",
            startTime = "04:30 PM",
            endTime = "06:30 PM",
            organizerName = "Paschim Maharashtra Devasthan Samiti",
            isVerified = true,
            lastUpdated = "1 day ago",
            isFeatured = false,
            schedule = listOf(
                ScheduleItem("y1", "05:15 PM", "Kirana Pravesh in Garbhagriha", "सूर्यकिरण गाभारा प्रवेश", "Witnessing of rays entering main temple", "Main Sanctum")
            ),
            announcements = emptyList(),
            liveStreamAvailable = false,
            savesCount = 1870,
            viewsCount = 22400
        ),
        Event(
            id = "kirtan_01",
            titleEn = "Akhand Harinam Saptaha & Tukaram Beej Sohla",
            titleMr = "अखंड हरिनाम सप्ताह व तुकाराम बीज कीर्तन सोहळा",
            descriptionEn = "Seven days continuous devotional chanting, daily morning Kakad, afternoon Pravachan by eminent scholars, and evening Kirtan by renowned Rashtrasants.",
            descriptionMr = "सलग सात दिवस २४ तास अखंड हरिनाम पहारा, नामवंत कीर्तनकारांचे प्रवचन व कीर्तन सेवा.",
            category = EventCategory.PRAVACHAN_KIRTAN,
            city = "Dehu, Pune",
            venue = "Gatha Mandir, Indrayani River Bank, Dehu",
            address = "Dehu Gatha Mandir Parisar, Haveli, Pune",
            lat = 18.7180,
            lng = 73.7667,
            startDate = "20 March 2026",
            endDate = "27 March 2026",
            startTime = "05:00 AM",
            endTime = "10:30 PM",
            organizerName = "Dehu Gatha Mandir Trust",
            isVerified = true,
            lastUpdated = "3 days ago",
            isFeatured = false,
            schedule = listOf(
                ScheduleItem("kt1", "09:00 AM", "Sant Charitra Pravachan", "संत चरित्र प्रवचन", "Discourse on Dnyaneshwari & Tukaram Gatha", "Sabhagruh"),
                ScheduleItem("kt2", "06:00 PM", "Haripath & Sangeet Kirtan", "हरिपाठ व कीर्तन", "Traditional Varkari Kirtan", "Indrayani Stage")
            ),
            announcements = emptyList(),
            liveStreamAvailable = true,
            savesCount = 980,
            viewsCount = 13500
        ),
        Event(
            id = "bhandara_01",
            titleEn = "Mahaprasad Bhandara & Annadaan Seva (Pandharpur Wari)",
            titleMr = "आषाढी वारी महाप्रसाद अन्नदान सेवा (पंढरपूर)",
            descriptionEn = "Continuous free hot Satvik meals (Khichdi, Pithla-Bhakri, Sheera, and buttermilk) served to over 50,000 pilgrims daily during Ashadhi Ekadashi.",
            descriptionMr = "वारकरी भाविकांसाठी २४ तास अविरत मोफत सात्विक भोजन, खिचडी, पिठलं-भाकरी व ताक वाटप अन्नछत्र.",
            category = EventCategory.MAHAPRASAD_BHANDARA,
            city = "Pandharpur",
            venue = "ISCKON & Warkari Annakshetra Campus, Chandrabhaga Ghat",
            address = "Near Bhima River Bridge, Pandharpur 413304",
            lat = 17.6740,
            lng = 75.3240,
            startDate = "05 July 2026",
            endDate = "15 July 2026",
            startTime = "06:00 AM",
            endTime = "11:30 PM",
            organizerName = "Warkari Annadaan Pratishthan",
            isVerified = true,
            lastUpdated = "4 hours ago",
            isFeatured = true,
            schedule = listOf(
                ScheduleItem("b1", "07:00 AM", "Morning Poha & Tea Seva", "सकाळचा नाष्टा व चहा", "Nutritious breakfast for walking pilgrims", "Dining Hall A"),
                ScheduleItem("b2", "11:30 AM", "Afternoon Mahaprasad", "दुपारचा महाप्रसाद", "Full meal service with clean drinking water", "All 4 Dining Halls")
            ),
            announcements = listOf(
                Announcement("ba1", "Eco-Friendly Patravali Used", "No single-use plastic used. Volunteers requested for serving.", "Yesterday", isUrgent = false, authorName = "Annakshetra Seva Team")
            ),
            liveStreamAvailable = false,
            savesCount = 2450,
            viewsCount = 31000
        )
    )

    private val sampleLiveStreams = listOf(
        LiveStream(
            id = "live_pandharpur",
            templeName = "Shri Vitthal Rukmini Mandir",
            eventName = "Ashadhi Ekadashi Mahapuja & 24x7 Darshan",
            organizerName = "Shri Vitthal Rukmini Temples Committee (Official)",
            streamTitle = "Pandharpur Live Darshan - Sanctum Sanctorum",
            streamUrl = "https://example.com/streams/pandharpur_official_live",
            isLive = true,
            viewerCountSample = 14250,
            scheduleNotice = "Daily Kakad Aarti: 04:00 AM | Maha Naivedya: 12:00 PM | Shej Aarti: 11:00 PM",
            audioChant = "विठ्ठल विठ्ठल जय हरी विठ्ठल"
        ),
        LiveStream(
            id = "live_dagdusheth",
            templeName = "Shrimant Dagdusheth Halwai Ganpati",
            eventName = "Ganesh Chaturthi Utsav 2026",
            organizerName = "Dagdusheth Halwai Trust Official Media Cell",
            streamTitle = "Pune Dagdusheth Live Darshan 1080p HD",
            streamUrl = "https://example.com/streams/dagdusheth_live",
            isLive = true,
            viewerCountSample = 28400,
            scheduleNotice = "Maha Aarti at 07:30 AM and 09:00 PM Daily",
            audioChant = "गणपती बाप्पा मोरया, मंगलमूर्ती मोरया"
        ),
        LiveStream(
            id = "live_lalbaug",
            templeName = "Lalbaugcha Raja Pandal, Mumbai",
            eventName = "Navas & Charan Sparsh Darshan Live",
            organizerName = "Lalbaugcha Raja Sarvajanik Mandal Cell",
            streamTitle = "Mumbai Lalbaugcha Raja Live Telecast",
            streamUrl = "https://example.com/streams/lalbaug_live",
            isLive = true,
            viewerCountSample = 45900,
            scheduleNotice = "24-Hour Continuous Authorized Feed",
            audioChant = "बोला गणपती बाप्पा मोरया"
        ),
        LiveStream(
            id = "live_nashik",
            templeName = "Trimbakeshwar Jyotirlinga & Ramkund",
            eventName = "Godavari Maha Aarti & Sangam Darshan",
            organizerName = "Shri Trimbakeshwar Sansthan Trust",
            streamTitle = "Trimbakeshwar Shiva Darshan & Godavari Aarti",
            streamUrl = "https://example.com/streams/trimbakeshwar_live",
            isLive = false,
            viewerCountSample = 0,
            scheduleNotice = "Next Live Session begins at 06:30 PM (Evening Aarti)",
            audioChant = "हर हर महादेव, जय गोदावरी माते"
        )
    )

    private val samplePosts = listOf(
        Post(
            id = "p1",
            authorName = "Sant Tukaram Maharaj Sansthan",
            authorRole = "Official Sansthan",
            isVerified = true,
            eventTag = "#AshadhiWari2026",
            textEn = "The holy Palkhi of Jagadguru Sant Tukaram Maharaj has safely arrived at Saswad camp. Harinaam kirtan and community meals in full progress. Ram Krishna Hari!",
            textMr = "जगद्गुरु संत तुकाराम महाराज पालखी सोहळा सासवड मुक्कामी विसावला आहे. संपूर्ण वातावरण टाळ-मृदुंगाच्या गजराने दुमदुमून गेले आहे. 'ज्ञानोबा माउली तुकाराम'!",
            timestamp = "20 mins ago",
            likesCount = 1420,
            commentsCount = 186,
            mediaDescription = "Palkhi procession entering Saswad with traditional saffron Pataka flags and white bullock cart."
        ),
        Post(
            id = "p2",
            authorName = "Dagdusheth Halwai Ganpati Trust",
            authorRole = "Verified Mandal",
            isVerified = true,
            eventTag = "#GaneshotsavPune",
            textEn = "Grand lighting decoration rehearsal completed for this year's Somnath Temple replica pandal. Mukh Darshan gates will open on Ganesh Chaturthi morning.",
            textMr = "सोमनाथ मंदिराच्या प्रतिकृतीचे नयनरम्य विद्युत रोषणाईचे काम पूर्ण! भाविकांना २४ तास सुलभ मुखदर्शनाची सोय करण्यात आली आहे.",
            timestamp = "2 hours ago",
            likesCount = 2890,
            commentsCount = 240,
            mediaDescription = "Majestic Golden Pandal illuminated at night in Pune Budhwar Peth."
        ),
        Post(
            id = "p3",
            authorName = "Ramesh Shinde (Warkari Dindi Pramukh)",
            authorRole = "Dindi No. 42",
            isVerified = true,
            eventTag = "#WariMoments",
            textEn = "Our Dindi brothers and sisters walked 24 kilometers today with unyielding devotion. Free medical seva by doctor volunteers at Dive Ghat was heartening.",
            textMr = "आज दिवे घाटातून जात असताना डॉक्टरांच्या टीमने वारकऱ्यांच्या पायाला मोफत मलम व औषधोपचार केले. या सेवेला आमचा लाख लाख नमस्कार!",
            timestamp = "4 hours ago",
            likesCount = 890,
            commentsCount = 74,
            mediaDescription = "Devotees walking uphill on Dive Ghat smiling with saffron flags."
        ),
        Post(
            id = "p4",
            authorName = "Vitthal Seva Sangh Pandharpur",
            authorRole = "Seva Volunteer",
            isVerified = true,
            eventTag = "#Chandrabhaga",
            textEn = "Cleanliness drive conducted on Chandrabhaga river banks. 200 dustbins and water filtration points placed for upcoming Ekadashi snan.",
            textMr = "चंद्रभागा वाळवंट स्वच्छता मोहीम यशस्वी! भाविकांनी कृपया नदी पात्रात कपडे व कचरा टाकू नये ही नम्र विनंती.",
            timestamp = "6 hours ago",
            likesCount = 1120,
            commentsCount = 92,
            mediaDescription = "Volunteers cleaning the sacred Chandrabhaga ghats with bamboo brooms."
        )
    )

    private val sampleLocations = listOf(
        LocationPoint("loc1", "Shri Vitthal Rukmini Mandir", "श्री विठ्ठल रुक्मिणी मंदिर", LocationType.TEMPLE_MANDAL, "Pandharpur, Solapur", "Pandharpur", 17.6775, 75.3278, 0.4, false, "02186-224466", "24 Hours (during Wari)", "Very High", true),
        LocationPoint("loc2", "Saswad Palkhi Mukam Pandal", "सासवड पालखी तळ", LocationType.WARI_ROUTE_POINT, "Saswad Palkhi Maidan, Pune", "Saswad", 18.3436, 74.0309, 3.2, true, "02115-222300", "04:00 AM - 11:00 PM", "High", true),
        LocationPoint("loc3", "Mauli Annakshetra (Free Bhandara)", "माऊली अन्नछत्र (मोफत महाप्रसाद)", LocationType.BHANDARA_MAHAPRASAD, "Chandrabhaga Ghat Road, Pandharpur", "Pandharpur", 17.6750, 75.3260, 0.6, true, "9822100000", "07:00 AM - 11:30 PM", "High", true),
        LocationPoint("loc4", "Tanpure Maharaj Math Dharamshala", "तनपुरे महाराज मठ धर्मशाळा", LocationType.ACCOMMODATION, "Pradakshina Road, Pandharpur", "Pandharpur", 17.6790, 75.3290, 0.8, false, "02186-223400", "24 Hours Check-in", "Moderate", true),
        LocationPoint("loc5", "Emergency 108 Medical Tent", "तातडीची वैद्यकीय मदत केंद्र", LocationType.MEDICAL_EMERGENCY, "Wakhari Phata, Pandharpur Highway", "Pandharpur", 17.6900, 75.3100, 4.5, true, "108", "24x7 Emergency", "Low", true),
        LocationPoint("loc6", "Municipal Mobile Drinking Water Point", "पिण्याच्या पाण्याचा टँकर", LocationType.DRINKING_WATER, "Near Namdev Payatha, Pandharpur", "Pandharpur", 17.6770, 75.3280, 0.2, true, "1800-233-1234", "Continuous Flow", "Moderate", true),
        LocationPoint("loc7", "Bio-Toilet Cluster (50 Units)", "स्वच्छतागृह संकुल", LocationType.PUBLIC_TOILET, "Bhakti Marg, Pandharpur", "Pandharpur", 17.6760, 75.3250, 0.5, true, "N/A", "24 Hours Open", "Moderate", true),
        LocationPoint("loc8", "Shrimant Dagdusheth Pandal", "दगडूशेठ गणपती मंडप", LocationType.TEMPLE_MANDAL, "Shivaji Road, Budhwar Peth", "Pune", 18.5167, 73.8562, 1.2, false, "020-24479222", "06:00 AM - 12:00 AM", "Very High", true),
        LocationPoint("loc9", "Panchavati Ramkund Kumbh Ghat", "पंचवटी रामकुंड", LocationType.TEMPLE_MANDAL, "Panchavati, Nashik", "Nashik", 19.9380, 73.5307, 2.0, false, "0253-2575631", "24 Hours", "High", true),
        LocationPoint("loc10", "Dindi Puja Samagri & Flag Stall", "वारकरी पताका व पूजा साहित्य स्टॉल", LocationType.TEMPORARY_STALL, "Dehu Gatha Mandir Chowk", "Dehu", 18.7180, 73.7667, 0.1, false, "9890123456", "06:00 AM - 10:00 PM", "Low", true)
    )

    private val sampleAccommodations = listOf(
        Accommodation(
            id = "acc_01",
            name = "Shri Vitthal Bhakt Niwas (Temple Trust)",
            type = AccommodationType.DHARAMSHALA,
            locationName = "Station Road, Pandharpur",
            city = "Pandharpur",
            pricePerNight = "₹150 / bed (Subsidized)",
            facilities = listOf("Clean Mattresses", "Purified RO Water", "Attached Bathrooms", "Luggage Locker", "CCTV Security"),
            contactPhone = "02186-224466",
            availableRooms = 42,
            isVerified = true,
            rulesNote = "Strictly Satvik premises. Alcohol, tobacco & non-veg strictly prohibited. Valid Aadhaar card required."
        ),
        Accommodation(
            id = "acc_02",
            name = "Tanpure Maharaj Math Dharamshala",
            type = AccommodationType.ASHRAM,
            locationName = "Pradakshina Road, Pandharpur",
            city = "Pandharpur",
            pricePerNight = "Voluntary Contribution / Seva",
            facilities = listOf("Dormitory Hall", "Community Kitchen", "Morning Bhajan", "Safe Water"),
            contactPhone = "02186-223400",
            availableRooms = 15,
            isVerified = true,
            rulesNote = "Priority for Dindi warkaris with Dindi registration slip."
        ),
        Accommodation(
            id = "acc_03",
            name = "Sant Dnyaneshwar Bhakt Bhavan",
            type = AccommodationType.GUEST_HOUSE,
            locationName = "Alandi Devachi, Pune",
            city = "Alandi",
            pricePerNight = "₹350 / room",
            facilities = listOf("Double Bed", "Hot Geyser Water", "Power Backup", "Car Parking"),
            contactPhone = "020-27185200",
            availableRooms = 8,
            isVerified = true,
            rulesNote = "Pre-booking advised during Kartiki & Ashadhi seasons."
        ),
        Accommodation(
            id = "acc_04",
            name = "Panchavati Pilgrims Lodge",
            type = AccommodationType.HOTEL,
            locationName = "Near Ramkund, Panchavati, Nashik",
            city = "Nashik",
            pricePerNight = "₹900 / night",
            facilities = listOf("AC / Non-AC Rooms", "Free Wi-Fi", "Room Service", "Elevator", "Tour Desk"),
            contactPhone = "0253-2512345",
            availableRooms = 12,
            isVerified = true,
            rulesNote = "Family-friendly accommodation close to Godavari ghat."
        )
    )

    private val sampleFoodServices = listOf(
        FoodOrEssentialService(
            id = "fs_01",
            name = "Shri Vitthal Sansthan Mahaprasad Dining Hall",
            category = "Free Mahaprasad (अन्नछत्र)",
            isFree = true,
            location = "East Gate, Vitthal Mandir, Pandharpur",
            city = "Pandharpur",
            operatingHours = "10:30 AM - 03:30 PM & 06:30 PM - 10:30 PM",
            description = "Sacred meal of steaming rice, varan, sabzi, and sheera served with reverence to all devotees regardless of background.",
            contactPhone = "02186-224466",
            emergencyLevel = "Normal"
        ),
        FoodOrEssentialService(
            id = "fs_02",
            name = "Dindi Amrut Tea & Poha Seva Stall",
            category = "Free Pilgrim Breakfast",
            isFree = true,
            location = "Dive Ghat Top & Belsar Phata",
            city = "Saswad Route",
            operatingHours = "05:00 AM - 10:30 AM",
            description = "Hot tea, jaggery water, and freshly cooked poha served by Pune Youth Mitra Mandal to all walking warkaris.",
            contactPhone = "9822456789",
            emergencyLevel = "Normal"
        ),
        FoodOrEssentialService(
            id = "fs_03",
            name = "Pandharpur Special Satvik Thali & Pithla Bhakri",
            category = "Local Maharashtrian Eatery",
            isFree = false,
            location = "Station Road, Pandharpur",
            city = "Pandharpur",
            operatingHours = "07:00 AM - 11:00 PM",
            description = "Authentic jowar bhakri, thecha, shenga chutney, pithla, and fresh buttermilk at nominal pricing.",
            contactPhone = "9422011223",
            emergencyLevel = "Normal"
        ),
        FoodOrEssentialService(
            id = "fs_04",
            name = "24x7 Emergency Pilgrim Health Center & Ambulance",
            category = "Medical Assistance (आरोग्य सेवा)",
            isFree = true,
            location = "Near Pradakshina Police Station, Pandharpur",
            city = "Pandharpur",
            operatingHours = "24 Hours (Round-the-clock)",
            description = "First-aid, foot-blister treatment, glucose IV, blood pressure checks, and emergency ICU ambulance on standby.",
            contactPhone = "108 / 02186-222100",
            emergencyLevel = "High"
        ),
        FoodOrEssentialService(
            id = "fs_05",
            name = "Municipal RO Purified Drinking Water Tanker Point",
            category = "Free Potable Water",
            isFree = true,
            location = "Gopalpur Road Junction, Pandharpur",
            city = "Pandharpur",
            operatingHours = "24 Hours",
            description = "Chilled RO drinking water taps with continuous testing by municipal health inspectors.",
            contactPhone = "1800-233-1234",
            emergencyLevel = "Normal"
        )
    )

    private val sampleVendors = listOf(
        VendorBusiness(
            id = "vb_01",
            name = "Shri Vitthal Traditional Tulsi Mala & Tal Store",
            category = "Puja Samagri & Devotional Items",
            eventAssociated = "Ashadhi Wari & Pandharpur Yatra",
            stallNumber = "Stall No. B-14",
            location = "Namdev Payatha Marg, Pandharpur",
            dates = "20 June - 15 July 2026",
            hours = "06:00 AM - 10:00 PM",
            products = listOf("Original Tulsi Beads Mala", "Brass Chiplya & Tal", "Chandan Ubtan", "Gopichandan Tika", "Warkari Pheta"),
            contactPhone = "9822114455",
            offerText = "10% Seva discount for Dindi participants with badge",
            isSponsored = false,
            isVerified = true
        ),
        VendorBusiness(
            id = "vb_02",
            name = "Mauli Famous Pandharpuri Pedha Sweets",
            category = "Sweets & Prasad Vendor",
            eventAssociated = "Pandharpur Mandir Parisar",
            stallNumber = "Stall No. A-02",
            location = "West Gate, Mandir Chowk",
            dates = "Year-Round & Special Festival Stalls",
            hours = "06:00 AM - 11:30 PM",
            products = listOf("Famous Khoa Malai Pedha", "Dink Laddu for Warkaris", "Rajgira Chikki", "Kakad Prasad"),
            contactPhone = "9423098765",
            offerText = "Special 1kg Prasad packaging ready for travel",
            isSponsored = true,
            isVerified = true
        ),
        VendorBusiness(
            id = "vb_03",
            name = "Bhagwat Traditional Dindi Flags & Dhol Accessories",
            category = "Pilgrimage Paraphernalia",
            eventAssociated = "Sant Tukaram Palkhi Dehu-Pune",
            stallNumber = "Stall No. D-08",
            location = "Near Dehu Gatha Mandir Parking",
            dates = "18 June - 05 July 2026",
            hours = "07:00 AM - 09:30 PM",
            products = listOf("Saffron Bhagwa Zenda", "Flag Bamboo Poles", "Mridanga Leather Belts", "White Cotton Kurta-Dhoti"),
            contactPhone = "9766554433",
            offerText = "Special bundle price for full Dindi groups",
            isSponsored = false,
            isVerified = true
        )
    )

    private val sampleServiceProviders = listOf(
        EventServiceProvider(
            id = "sp_01",
            name = "Shivshambho Pandal, Stage & Water-proof Mandap",
            serviceCategory = "Pandal & Temporary Tents",
            serviceArea = "Pune, Pandharpur, Solapur, Satara",
            startingPriceEstimate = "₹25,000 onwards (Customized by sq.ft)",
            description = "Specialists in giant German hangar waterproof pandals, Varkari community rest shelters, and Ganpati mandal thematic stages with safety clearance.",
            contactPhone = "9822334455",
            isAvailable = true,
            isVerified = true,
            pastEventsHandled = "Over 18 years handling Pune Ganpati and Palkhi halt pandals."
        ),
        EventServiceProvider(
            id = "sp_02",
            name = "Swaranand Audio Systems & Digital Sound Engineers",
            serviceCategory = "Sound & Public Address Systems",
            serviceArea = "Entire Maharashtra",
            startingPriceEstimate = "₹12,000 / day",
            description = "Noise-pollution compliant long-throw horn arrays, wireless microphones for Kirtan/Bhajan, and mobile battery backup sound vans for marching Dindis.",
            contactPhone = "9850123987",
            isAvailable = true,
            isVerified = true,
            pastEventsHandled = "Sound engineers for Akhand Harinam Saptaha and Alandi Palkhi."
        ),
        EventServiceProvider(
            id = "sp_03",
            name = "Divya Darshan HD Multi-Cam Live Streaming & Production",
            serviceCategory = "Live Broadcast & Multi-Camera Setup",
            serviceArea = "Mumbai, Pune, Nashik, Kolhapur, Solapur",
            startingPriceEstimate = "₹18,000 / session",
            description = "Full 4K/1080p multi-camera streaming setup with bonded cellular internet encoders for temple sansthans, Aarti broadcasts, and YouTube/App live feeds.",
            contactPhone = "9922001122",
            isAvailable = true,
            isVerified = true,
            pastEventsHandled = "Broadcast partner for 40+ Ganpati Mandals and Yatra committees."
        ),
        EventServiceProvider(
            id = "sp_04",
            name = "Annapurna Large-Scale Community Catering & Bhandara",
            serviceCategory = "Mass Catering & Annadaan",
            serviceArea = "Maharashtra & Goa",
            startingPriceEstimate = "₹45 per devotee plate",
            description = "Pure satvik mass food preparation handling up to 25,000 people per meal. Hygienic steam-cooking boilers, eco-friendly disposal, and certified staff.",
            contactPhone = "9422339900",
            isAvailable = true,
            isVerified = true,
            pastEventsHandled = "Served over 2 million meals in Wari and Kumbh Mela camps."
        )
    )

    private val _events = MutableStateFlow(sampleEvents)
    private val _liveStreams = MutableStateFlow(sampleLiveStreams)
    private val _posts = MutableStateFlow(samplePosts)
    private val _locations = MutableStateFlow(sampleLocations)
    private val _accommodations = MutableStateFlow(sampleAccommodations)
    private val _foodAndServices = MutableStateFlow(sampleFoodServices)
    private val _vendors = MutableStateFlow(sampleVendors)
    private val _serviceProviders = MutableStateFlow(sampleServiceProviders)
    private val _currentUser = MutableStateFlow(User())
    private val _enquiries = MutableStateFlow<List<BookingEnquiry>>(emptyList())

    override fun getEvents(): Flow<List<Event>> = _events.asStateFlow()
    override fun getLiveStreams(): Flow<List<LiveStream>> = _liveStreams.asStateFlow()
    override fun getPosts(): Flow<List<Post>> = _posts.asStateFlow()
    override fun getLocations(): Flow<List<LocationPoint>> = _locations.asStateFlow()
    override fun getAccommodations(): Flow<List<Accommodation>> = _accommodations.asStateFlow()
    override fun getFoodAndServices(): Flow<List<FoodOrEssentialService>> = _foodAndServices.asStateFlow()
    override fun getVendors(): Flow<List<VendorBusiness>> = _vendors.asStateFlow()
    override fun getServiceProviders(): Flow<List<EventServiceProvider>> = _serviceProviders.asStateFlow()
    override fun getCurrentUser(): Flow<User> = _currentUser.asStateFlow()

    override fun toggleSaveEvent(eventId: String) {
        _currentUser.update { user ->
            val newSaved = user.savedEventIds.toMutableSet()
            if (newSaved.contains(eventId)) {
                newSaved.remove(eventId)
            } else {
                newSaved.add(eventId)
            }
            user.copy(savedEventIds = newSaved)
        }
        _events.update { list ->
            list.map { ev ->
                if (ev.id == eventId) {
                    val isCurrentlySaved = _currentUser.value.savedEventIds.contains(eventId)
                    val newCount = if (isCurrentlySaved) ev.savesCount + 1 else maxOf(0, ev.savesCount - 1)
                    ev.copy(savesCount = newCount)
                } else ev
            }
        }
    }

    override fun toggleFollowOrg(orgId: String) {
        _currentUser.update { user ->
            val newFollowed = user.followedOrgIds.toMutableSet()
            if (newFollowed.contains(orgId)) {
                newFollowed.remove(orgId)
            } else {
                newFollowed.add(orgId)
            }
            user.copy(followedOrgIds = newFollowed)
        }
    }

    override fun toggleLikePost(postId: String) {
        _posts.update { list ->
            list.map { post ->
                if (post.id == postId) {
                    val wasLiked = post.isLiked
                    post.copy(
                        isLiked = !wasLiked,
                        likesCount = if (wasLiked) post.likesCount - 1 else post.likesCount + 1
                    )
                } else post
            }
        }
    }

    override fun switchUserRole(role: UserRole) {
        _currentUser.update { it.copy(role = role) }
    }

    override fun switchLanguage(language: Language) {
        _currentUser.update { it.copy(preferredLanguage = language) }
    }

    override fun addBookingEnquiry(enquiry: BookingEnquiry) {
        _enquiries.update { it + enquiry }
    }

    override fun addAnnouncement(eventId: String, title: String, message: String, isUrgent: Boolean) {
        val newAnnouncement = Announcement(
            id = "ann_${System.currentTimeMillis()}",
            title = title,
            message = message,
            timestamp = "Just now",
            isUrgent = isUrgent,
            authorName = "Authorized Event Desk"
        )
        _events.update { list ->
            list.map { ev ->
                if (ev.id == eventId) {
                    ev.copy(announcements = listOf(newAnnouncement) + ev.announcements)
                } else ev
            }
        }
    }

    override fun createEvent(event: Event) {
        _events.update { listOf(event) + it }
    }

    override fun addPost(textEn: String, textMr: String, eventTag: String) {
        val newPost = Post(
            id = "post_${System.currentTimeMillis()}",
            authorName = _currentUser.value.name,
            authorRole = _currentUser.value.role.label,
            isVerified = false,
            eventTag = if (eventTag.startsWith("#")) eventTag else "#$eventTag",
            textEn = textEn,
            textMr = if (textMr.isNotBlank()) textMr else textEn,
            timestamp = "Just now",
            likesCount = 1,
            commentsCount = 0,
            mediaDescription = "Devotee shared update & community photo",
            isLiked = true
        )
        _posts.update { listOf(newPost) + it }
    }
}
