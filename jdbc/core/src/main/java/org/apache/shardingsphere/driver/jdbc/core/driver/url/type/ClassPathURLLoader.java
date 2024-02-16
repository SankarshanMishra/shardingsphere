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

package org.apache.shardingsphere.driver.jdbc.core.driver.url.type;

import lombok.SneakyThrows;
import org.apache.shardingsphere.driver.jdbc.core.driver.url.ShardingSphereURLLoader;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Class path URL loader.
 */
public final class ClassPathURLLoader implements ShardingSphereURLLoader {
    
    @Override
    @SneakyThrows(IOException.class)
    public String load(final String configurationSubject, final Map<String, String> parameters) {
        return Files.readAllLines(getResourceFile(configurationSubject).toPath()).stream().collect(Collectors.joining(System.lineSeparator()));
    }
    
    @SneakyThrows(URISyntaxException.class)
    private File getResourceFile(final String configurationSubject) {
        return new File(Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource(configurationSubject)).toURI().getPath());
    }
    
    @Override
    public String getType() {
        return "classpath:";
    }
}