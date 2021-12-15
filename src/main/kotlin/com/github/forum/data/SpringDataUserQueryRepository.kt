package com.github.forum.data

import com.github.forum.domain.User
import com.github.forum.domain.UserQueryRepository
import org.springframework.data.repository.Repository

interface SpringDataUserQueryRepository : UserQueryRepository, Repository<User, Long>
