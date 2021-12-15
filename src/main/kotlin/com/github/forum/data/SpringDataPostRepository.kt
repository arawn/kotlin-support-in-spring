package com.github.forum.data

import com.github.forum.domain.Post
import com.github.forum.domain.PostRepository
import org.springframework.data.repository.CrudRepository

interface SpringDataPostRepository : PostRepository, CrudRepository<Post, Long>
