import org.gradle.api.tasks.wrapper.Wrapper.DistributionType.ALL
import org.gradle.internal.impldep.org.junit.experimental.categories.Categories.CategoryFilter.exclude
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

/*
 * Copyright 2017-2019 Red Hat, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

plugins {
  `java-gradle-plugin`
  kotlin("jvm") version "1.3.72"
  id("com.github.ben-manes.versions") version "0.28.0"
  id("com.gradle.plugin-publish") version "0.12.0"
}

repositories {
  jcenter()
  mavenLocal()
}

version = "1.1.1-SNAPSHOT"
group = "io.vertx"

dependencies {
  implementation(kotlin("stdlib-jdk8"))
  implementation("com.github.jengelman.gradle.plugins:shadow:5.2.0")

  testImplementation("junit:junit:4.13")
  testImplementation("com.mashape.unirest:unirest-java:1.4.9")
  testImplementation("org.assertj:assertj-core:3.16.1")
}

gradlePlugin {
  plugins {
    create("vertxPlugin") {
      id = "io.vertx.vertx-plugin"
      implementationClass = "io.vertx.gradle.VertxPlugin"
    }
  }
}

pluginBundle {
  website = "https://github.com/jponge/vertx-gradle-plugin"
  vcsUrl = "https://github.com/jponge/vertx-gradle-plugin"
  plugins {
    getByName("vertxPlugin") {
      id = "io.vertx.vertx-plugin"
      displayName = "Vert.x Gradle Plugin"
      description = "An opinionated Gradle plugin for Vert.x projects"
      tags = listOf("vertx", "vert.x", "reactive", "microservice")
    }
  }
}

tasks {
  getByName<Wrapper>("wrapper") {
    gradleVersion = "6.5"
    distributionType = ALL
  }

  withType<KotlinCompile> {
    kotlinOptions { jvmTarget = "1.8" }
  }
}
