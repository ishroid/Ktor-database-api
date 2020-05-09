package com.ishroid.example.app.model

import java.util.*

data class Issue(val userUuid: UUID, val issueId:Int, val title: String, val description: String, val refImages: List<String>, val refVideos: List<String>)