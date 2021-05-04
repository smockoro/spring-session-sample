/**
 * @formatter:off
 *      MIT License
 *
 *      Copyright (c) 2021 mockoro
 *
 *      Permission is hereby granted, free of charge, to any person obtaining a copy
 *      of this software and associated documentation files (the "Software"), to deal
 *      in the Software without restriction, including without limitation the rights
 *      to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *      copies of the Software, and to permit persons to whom the Software is
 *      furnished to do so, subject to the following conditions:
 *
 *      The above copyright notice and this permission notice shall be included in all
 *      copies or substantial portions of the Software.
 *
 *      THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *      IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *      FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *      AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *      LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *      OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *      SOFTWARE.
 * @formatter:on
 */
package com.example.demo.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.BindMode;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;

public class MysqlContainerInitializer implements
    ApplicationContextInitializer<ConfigurableApplicationContext> {

  private static final String MYSQL_IMAGE = "mysql:8.0";

  private static final String DATABASE_NAME = "userservice";
  private static final String USERNAME = "root";
  private static final String PASSWORD = "password";
  private static final int PORT = 3306;

  private static final String INIT_SQL = "/initdb.d/1_user.sql";
  private static final String INIT_SQL_IN_CONTAINER = "/docker-entrypoint-initdb.d/1_user.sql";

  private static final Logger LOGGER = LoggerFactory.getLogger(MysqlContainerInitializer.class);

  private static final MySQLContainer MYSQL = (MySQLContainer) new MySQLContainer(MYSQL_IMAGE)
      .withDatabaseName(DATABASE_NAME)
      .withUsername(USERNAME)
      .withPassword(PASSWORD)
      .withExposedPorts(PORT)
      .withLogConsumer(new Slf4jLogConsumer(LOGGER))
      .withClasspathResourceMapping(INIT_SQL, INIT_SQL_IN_CONTAINER, BindMode.READ_ONLY);

  static {
    MYSQL.start();
  }

  @Override
  public void initialize(ConfigurableApplicationContext applicationContext) {
    String jdbcUrl = MYSQL.getJdbcUrl();
    TestPropertyValues.of(
        "spring.datasource.url=" + jdbcUrl
    ).applyTo(applicationContext.getEnvironment());
  }
}
