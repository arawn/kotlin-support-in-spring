package com.github.forum.web

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver
import org.springframework.web.servlet.view.json.MappingJackson2JsonView

@Configuration
class WebConfiguration : WebMvcConfigurer

@Configuration
class ContentNegotiationCustomizer {

    @Autowired
    fun configurer(viewResolver: ContentNegotiatingViewResolver) {
        val defaultViews = ArrayList(viewResolver.defaultViews)
        defaultViews.add(MappingJackson2JsonView())

        viewResolver.defaultViews = defaultViews
    }
}
