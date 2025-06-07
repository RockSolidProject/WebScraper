package dao

import webScraper.InnerClimbingCenter.InnerClimbingCenter

interface InnerClimbingCenterDao : DaoCrud<InnerClimbingCenter> {
    fun getAll() : List<InnerClimbingCenter>?
}