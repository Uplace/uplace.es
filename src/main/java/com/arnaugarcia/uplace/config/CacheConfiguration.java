package com.arnaugarcia.uplace.config;

import io.github.jhipster.config.JHipsterProperties;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;
import org.ehcache.jsr107.Eh107Configuration;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
@AutoConfigureAfter(value = { MetricsConfiguration.class })
@AutoConfigureBefore(value = { WebConfigurer.class, DatabaseConfiguration.class })
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(Expirations.timeToLiveExpiration(Duration.of(ehcache.getTimeToLiveSeconds(), TimeUnit.SECONDS)))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(com.arnaugarcia.uplace.repository.UserRepository.USERS_BY_LOGIN_CACHE, jcacheConfiguration);
            cm.createCache(com.arnaugarcia.uplace.repository.UserRepository.USERS_BY_EMAIL_CACHE, jcacheConfiguration);
            cm.createCache(com.arnaugarcia.uplace.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(com.arnaugarcia.uplace.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(com.arnaugarcia.uplace.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(com.arnaugarcia.uplace.domain.Property.class.getName(), jcacheConfiguration);
            cm.createCache(com.arnaugarcia.uplace.domain.Property.class.getName() + ".manageds", jcacheConfiguration);
            cm.createCache(com.arnaugarcia.uplace.domain.Apartment.class.getName(), jcacheConfiguration);
            cm.createCache(com.arnaugarcia.uplace.domain.Office.class.getName(), jcacheConfiguration);
            cm.createCache(com.arnaugarcia.uplace.domain.Agent.class.getName(), jcacheConfiguration);
            cm.createCache(com.arnaugarcia.uplace.domain.Parking.class.getName(), jcacheConfiguration);
            cm.createCache(com.arnaugarcia.uplace.domain.Terrain.class.getName(), jcacheConfiguration);
            cm.createCache(com.arnaugarcia.uplace.domain.Location.class.getName(), jcacheConfiguration);
            cm.createCache(com.arnaugarcia.uplace.domain.Bussiness.class.getName(), jcacheConfiguration);
            cm.createCache(com.arnaugarcia.uplace.domain.Gallery.class.getName(), jcacheConfiguration);
            cm.createCache(com.arnaugarcia.uplace.domain.Gallery.class.getName() + ".photos", jcacheConfiguration);
            cm.createCache(com.arnaugarcia.uplace.domain.Photo.class.getName(), jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
