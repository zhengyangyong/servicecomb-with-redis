/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.demo;

import org.apache.servicecomb.provider.rest.common.RestSchema;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.netflix.config.DynamicPropertyFactory;

import redis.clients.jedis.Jedis;

@RestSchema(schemaId = "hello")
@RequestMapping(path = "/")
public class HelloImpl {

  @GetMapping(path = "/set")
  public String set(String id, String value) {
    Jedis jedis = getJedisClient();
    jedis.set(id, value);
    String result = jedis.get(id);
    jedis.close();
    return result;
  }

  @GetMapping(path = "/get")
  public String get(String id) {
    Jedis jedis = getJedisClient();
    String result = jedis.get(id);
    jedis.close();
    return result;
  }

  private Jedis getJedisClient() {
    String server = DynamicPropertyFactory.getInstance().getStringProperty("redis.server", "localhost").get();
    int port = DynamicPropertyFactory.getInstance().getIntProperty("redis.port", 6379).get();
    return new Jedis(server, port);
  }
}