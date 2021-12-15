package com.github.forum.data

import com.github.forum.domain.Topic
import com.github.forum.domain.TopicRepository
import org.springframework.data.repository.CrudRepository
import java.util.UUID

interface SpringDataTopicRepository : TopicRepository, CrudRepository<Topic, UUID>
