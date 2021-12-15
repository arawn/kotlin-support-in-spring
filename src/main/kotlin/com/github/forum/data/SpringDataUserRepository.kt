package com.github.forum.data

import com.github.forum.domain.User
import com.github.forum.domain.UserRepository
import org.springframework.data.repository.CrudRepository

interface SpringDataUserRepository : UserRepository, CrudRepository<User, Long>
