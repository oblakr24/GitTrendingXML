package com.rokoblak.trendinggit.data.repo

import com.rokoblak.trendinggit.data.service.api.model.GithubSearchResponse
import com.rokoblak.trendinggit.data.service.db.model.GitRepoEntity
import com.rokoblak.trendinggit.data.service.model.GitRepository
import java.time.Instant

object RepoModelMapper {

    fun GithubSearchResponse.mapToEntity(page: Int, startIdx: Int): List<GitRepoEntity> {
        return items.withIndex().map { (idx, item) ->
            GitRepoEntity(
                id = item.id,
                name = item.name,
                desc = item.description,
                authorImgUrl = item.owner.avatar_url,
                authorName = item.owner.login,
                lang = item.language,
                stars = item.stargazers_count,
                pageIdx = page,
                timestampMs = Instant.now().toEpochMilli(),
                orderIdx = startIdx + idx,
            )
        }
    }

    fun GitRepoEntity.mapToDomain() = GitRepository(
        id = id,
        name = name,
        desc = desc,
        authorName = authorName,
        authorImgUrl = authorImgUrl,
        lang = lang,
        stars = stars,
        pageIdx = pageIdx,
    )
}
