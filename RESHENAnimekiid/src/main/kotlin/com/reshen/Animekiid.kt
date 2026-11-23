package com.reshen

import com.lagradost.cloudstream3.*
import com.lagradost.cloudstream3.utils.*

class Animekiid : MainAPI() {
    override var mainUrl = "https://animekiid.com"
    override var name = "RESHEN Animekiid"
    override val supportedTypes = setOf(TvType.Anime)
    override var lang = "id"
    override val hasMainPage = true

    override suspend fun search(query: String): List<SearchResponse> {
        val doc = app.get(
            "$mainUrl/?s=${query.replace(" ", "+")}"
        ).document

        return doc.select("div.bsx").mapNotNull { item ->
            val link = item.selectFirst("a[itemprop=url]") ?: return@mapNotNull null
            val title = item.selectFirst("h2[itemprop=headline]")?.text()
                ?: link.attr("title")
            val poster = item.selectFirst("img")?.attr("data-src") ?: ""

            newAnimeSearchResponse(title, link.attr("href")) {
                this.posterUrl = poster
            }
        }
    }

    override suspend fun load(url: String): LoadResponse {
        throw Error("Detail anime belum dibuat")
    }
}
