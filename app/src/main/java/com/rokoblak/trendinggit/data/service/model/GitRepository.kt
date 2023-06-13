package com.rokoblak.trendinggit.data.service.model


data class GitRepository(
    val id: Long,
    val name: String,
    val desc: String?,
    val authorImgUrl: String?,
    val authorName: String,
    val lang: String?,
    val stars: Long,
    val pageIdx: Int,
)