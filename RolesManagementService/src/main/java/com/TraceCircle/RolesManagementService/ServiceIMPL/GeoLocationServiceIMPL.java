package com.TraceCircle.RolesManagementService.ServiceIMPL;

import java.util.*;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class GeoLocationServiceIMPL {

    private final RestTemplate restTemplate;
    private static final String BASE_URL = "https://countriesnow.space/api/v0.1/countries";

    /**
     * ✅ Get all countries
     */
    public List<String> getAllCountries() {
        log.info("Fetching all countries...");
        try {
            ResponseEntity<Map> response = restTemplate.getForEntity(BASE_URL, Map.class);
            List<Map<String, Object>> data = (List<Map<String, Object>>) response.getBody().get("data");

            List<String> countries = new ArrayList<>();
            for (Map<String, Object> c : data) {
                countries.add((String) c.get("country"));
            }

            log.info("✅ {} countries fetched successfully", countries.size());
            return countries;
        } catch (Exception e) {
            log.error("Error fetching countries: {}", e.getMessage());
            return List.of();
        }
    }

    /**
     * ✅ Get all states for a given country
     */
    public List<String> getStatesByCountry(String country) {
        log.info("Fetching states for country={}", country);
        String url = BASE_URL + "/states";

        Map<String, String> req = Map.of("country", country);
        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(url, req, Map.class);
            Map<String, Object> data = (Map<String, Object>) response.getBody().get("data");

            List<Map<String, Object>> states = (List<Map<String, Object>>) data.get("states");
            List<String> stateList = new ArrayList<>();
            for (Map<String, Object> s : states) {
                stateList.add((String) s.get("name"));
            }

            log.info("✅ {} states found for country={}", stateList.size(), country);
            return stateList;
        } catch (Exception e) {
            log.error("Error fetching states for {}: {}", country, e.getMessage());
            return List.of();
        }
    }

    /**
     * ✅ Get all cities for a given country + state
     */
    public List<String> getCitiesByState(String country, String state) {
        log.info("Fetching cities for country={} and state={}", country, state);
        String url = BASE_URL + "/state/cities";

        Map<String, String> req = Map.of("country", country, "state", state);
        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(url, req, Map.class);
            List<String> cities = (List<String>) response.getBody().get("data");

            log.info("✅ {} cities found in state={} ({})", cities.size(), state, country);
            return cities;
        } catch (Exception e) {
            log.error("Error fetching cities for {}-{}: {}", country, state, e.getMessage());
            return List.of();
        }
    }
}
