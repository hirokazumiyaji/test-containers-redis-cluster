package com.github.hirokazumiyaji.testcontainers

import org.testcontainers.containers.GenericContainer
import org.testcontainers.containers.wait.strategy.Wait

class RedisContainer : GenericContainer<RedisContainer> {
    constructor() : super("redis:5.0-alpine") {
        withExposedPorts(6379)
        waitingFor(Wait.forLogMessage(".*Ready to accept connections.*", 1))
        withCommand("redis-server",
                    "--cluster-enabled", "yes",
                    "--cluster-config-file", "node.conf",
                    "--cluster-node-timeout", "2000")
        start()
    }
}