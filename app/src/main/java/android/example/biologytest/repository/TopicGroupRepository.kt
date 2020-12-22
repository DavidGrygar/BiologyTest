package android.example.biologytest.repository

import android.example.biologytest.model.daos.TopicGroupDao
import javax.inject.Inject

class TopicGroupRepository @Inject constructor(
    private val topicGroupDao: TopicGroupDao
) {

}