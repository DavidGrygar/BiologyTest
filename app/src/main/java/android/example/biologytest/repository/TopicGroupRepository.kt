package android.example.biologytest.repository

import android.example.biologytest.model.daos.TopicGroupDao
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

class TopicGroupRepository @Inject constructor(
    private val topicGroupDao: TopicGroupDao
) {
    @ExperimentalCoroutinesApi
    fun getTopicGroup(topicGroupId: Long) = topicGroupDao.getSingle(topicGroupId)

    @ExperimentalCoroutinesApi
    fun getAllTopicGroups() = topicGroupDao.getList()
}