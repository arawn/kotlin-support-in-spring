package com.github.forum.data

import com.github.forum.domain.Topic
import com.github.forum.domain.TopicQueryRepository
import org.springframework.data.repository.Repository
import java.util.UUID

interface SpringDataTopicQueryRepository : TopicQueryRepository, Repository<Topic, UUID>
