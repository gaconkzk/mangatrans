package org.theflies.registry.config

/**
 *
 */
object TheFliesConstants {
  // Spring profiles for development, test and production, see http://jhipster.github.io/profiles/
  val SPRING_PROFILE_DEVELOPMENT = "dev"
  val SPRING_PROFILE_TEST = "test"
  val SPRING_PROFILE_PRODUCTION = "prod"
  // Spring profile used when deploying with Spring Cloud (used when deploying to CloudFoundry)
  val SPRING_PROFILE_CLOUD = "cloud"
  // Spring profile used when deploying to Heroku
  val SPRING_PROFILE_HEROKU = "heroku"
  // Spring profile used to disable swagger
  val SPRING_PROFILE_SWAGGER = "swagger"
  // Spring profile used to disable running liquibase
  val SPRING_PROFILE_NO_LIQUIBASE = "no-liquibase"
}
