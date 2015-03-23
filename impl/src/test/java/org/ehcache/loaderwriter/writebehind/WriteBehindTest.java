/*
 * Copyright Terracotta, Inc.
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
package org.ehcache.loaderwriter.writebehind;

import static org.mockito.Matchers.anyObject;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.CacheManagerBuilder;
import org.ehcache.config.CacheConfiguration;
import org.ehcache.config.CacheConfigurationBuilder;
import org.ehcache.config.writebehind.WriteBehindConfiguration;
import org.ehcache.config.writebehind.WriteBehindConfigurationBuilder;
import org.ehcache.exceptions.BulkCacheWritingException;
import org.ehcache.expiry.Expirations;
import org.ehcache.spi.loaderwriter.CacheLoaderWriter;
import org.ehcache.spi.loaderwriter.CacheLoaderWriterFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author Abhilash
 *
 */
public class WriteBehindTest extends AbstractWriteBehindTestBase {
    

  @Before
  public void setUp(){
    CacheManagerBuilder<CacheManager> builder = CacheManagerBuilder.newCacheManagerBuilder();
    CacheLoaderWriterFactory cacheLoaderWriterFactory = mock(CacheLoaderWriterFactory.class);
    
    when(cacheLoaderWriterFactory.createCacheLoaderWriter(anyString(), (CacheConfiguration<String, String>)anyObject())).thenReturn((CacheLoaderWriter)loaderWriter);
    
    WriteBehindConfigurationBuilder writeBehindConfigurationBuilder = WriteBehindConfigurationBuilder.newWriteBehindConfigurationBuilder();
    WriteBehindConfiguration writeBehindConfiguration = writeBehindConfigurationBuilder.with(false, 3, 10).withBatching(5).build();
    builder.using(cacheLoaderWriterFactory);
    
    cacheManager = builder.build(true);
    testCache = cacheManager.createCache("testCache", CacheConfigurationBuilder.newCacheConfigurationBuilder()
        .withExpiry(Expirations.noExpiration())
        .addServiceConfig(writeBehindConfiguration)
        .buildConfig(String.class, String.class));

  }
  

}
