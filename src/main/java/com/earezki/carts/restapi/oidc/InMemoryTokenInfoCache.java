package com.earezki.carts.restapi.oidc;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import io.quarkus.oidc.*;
import io.smallrye.mutiny.Uni;

import javax.annotation.Priority;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Alternative;

@ApplicationScoped
@Alternative
@Priority(0)
public class InMemoryTokenInfoCache implements TokenIntrospectionCache, UserInfoCache {

    private static class CacheEntry {

        TokenIntrospection tokenIntrospection;
        UserInfo userInfo;

        CacheEntry(TokenIntrospection tokenIntrospection, UserInfo userInfo) {
            this.tokenIntrospection = tokenIntrospection;
            this.userInfo = userInfo;
        }

    }

    private final Cache<String, CacheEntry> cache;

    public InMemoryTokenInfoCache() {
        this.cache = Caffeine.from("initialCapacity=100,maximumSize=1000,expireAfterWrite=5m")
                .build();
    }

    @Override
    public Uni<Void> addIntrospection(String token,
                                      TokenIntrospection introspection,
                                      OidcTenantConfig oidcConfig,
                                      OidcRequestContext<Void> requestContext) {
        CacheEntry cacheEntry = cache.getIfPresent(token);
        if (cacheEntry == null) {
            return Uni.createFrom().nullItem();
        }

        cacheEntry.tokenIntrospection = introspection;

        return Uni.createFrom().voidItem();
    }

    @Override
    public Uni<TokenIntrospection> getIntrospection(String token,
                                                    OidcTenantConfig oidcConfig,
                                                    OidcRequestContext<TokenIntrospection> requestContext) {
        CacheEntry cacheEntry = cache.getIfPresent(token);

        if (cacheEntry == null || cacheEntry.tokenIntrospection == null) {
            return Uni.createFrom().nullItem();
        }

        return Uni.createFrom().item(cacheEntry.tokenIntrospection);
    }

    @Override
    public Uni<Void> addUserInfo(String token, UserInfo userInfo, OidcTenantConfig oidcConfig,
                                 OidcRequestContext<Void> requestContext) {
        CacheEntry cacheEntry = cache.getIfPresent(token);
        if (cacheEntry == null) {
            return Uni.createFrom().nullItem();
        }

        cacheEntry.userInfo = userInfo;

        return Uni.createFrom().voidItem();
    }

    @Override
    public Uni<UserInfo> getUserInfo(String token, OidcTenantConfig oidcConfig,
                                     OidcRequestContext<UserInfo> requestContext) {
        CacheEntry cacheEntry = cache.getIfPresent(token);

        if (cacheEntry == null || cacheEntry.userInfo == null) {
            return Uni.createFrom().nullItem();
        }

        return Uni.createFrom().item(cacheEntry.userInfo);
    }
}
