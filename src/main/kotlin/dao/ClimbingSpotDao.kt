package dao

import webScraper.OutdorSpotsRoutes.ClimbingSpot

interface ClimbingSpotDao : DaoCrud<ClimbingSpot> {
    fun getAll() : List<ClimbingSpot>?
}