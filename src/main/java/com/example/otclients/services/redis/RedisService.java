package com.example.otclients.services.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisService {

//    private final RedisTemplate<String, DashboardOneResponse> dashboardOneResponseRedisTemplate;
//
//    //Keys
//    public static final String DASHBOARD_TAB_1_DATA = "dashboardOneResponse";
//
//    public void setDashboardOneCache(DashboardOneResponse dashboardOneResponse) {
//        ValueOperations<String, DashboardOneResponse> cache = dashboardOneResponseRedisTemplate.opsForValue();
//        cache.set(DASHBOARD_TAB_1_DATA, dashboardOneResponse, Duration.of(1, ChronoUnit.HOURS));
//    }
//
//    public Optional<DashboardOneResponse> getDashboardOneCache() {
//        ValueOperations<String, DashboardOneResponse> cache = dashboardOneResponseRedisTemplate.opsForValue();
//        try {
//            return Optional.ofNullable(cache.get(DASHBOARD_TAB_1_DATA));
//        } catch (Exception e) {
//            return Optional.empty();
//        }
//    }
}
