/**
 * This example works with Spring Boot 2.4.4
 */

package com.daangn

import org.springframework.fu.kofu.webApplication
import org.springframework.fu.kofu.webmvc.webMvc
import org.springframework.web.servlet.function.ServerResponse

val myApplication = webApplication {
    beans {
        bean<MyRepository>()
        bean {
            MyService(ref())
        }
        webMvc {
            port = if (profiles.contains("test")) 8181 else 8080
            router {
                val service = ref<MyService>()
                GET("/") {
                    ServerResponse.ok().body(service.say())
                }
            }
            converters {
                string()
            }
        }
    }
}

fun main(args: Array<String>) {
    myApplication.run(args)
}
