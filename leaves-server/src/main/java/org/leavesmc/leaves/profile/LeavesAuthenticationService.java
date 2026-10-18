package org.leavesmc.leaves.profile;

import com.destroystokyo.paper.profile.PaperServicesDiscoveryService;
import com.mojang.authlib.Environment;
import com.mojang.authlib.minecraft.SessionService;
import com.mojang.authlib.services.response.discovery.DiscoveryResponse;

import java.net.Proxy;
import java.util.function.Supplier;

public class LeavesAuthenticationService extends PaperServicesDiscoveryService {

    public LeavesAuthenticationService(final Proxy proxy, final boolean servicesKeySetEnabled, final Supplier<DiscoveryResponse> discoverySupplier) {
        super(proxy, servicesKeySetEnabled, discoverySupplier);
    }

    public static LeavesAuthenticationService create(final Proxy proxy) {
        return create(proxy, true, determineEnvironment());
    }

    public static LeavesAuthenticationService create(final Proxy proxy, final boolean servicesKeySetEnabled) {
        return create(proxy, servicesKeySetEnabled, determineEnvironment());
    }

    public static LeavesAuthenticationService create(final Proxy proxy, final boolean servicesKeySetEnabled, final Environment environment) {
        return new LeavesAuthenticationService(proxy, servicesKeySetEnabled, createDiscoverySupplier(proxy, environment));
    }

    @Override
    public SessionService createMinecraftSessionService() {
        return new LeavesMinecraftSessionService(this.getServicesKeySet(), this.getProxy(), this);
    }
}
