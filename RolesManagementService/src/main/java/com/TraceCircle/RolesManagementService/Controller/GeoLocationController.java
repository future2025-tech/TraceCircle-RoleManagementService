package com.TraceCircle.RolesManagementService.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.TraceCircle.RolesManagementService.ServiceIMPL.GeoLocationServiceIMPL;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/auth/api/v1/location")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "http://localhost:5173")
public class GeoLocationController {

    private final GeoLocationServiceIMPL geoService;

    @GetMapping("/countries")
    public ResponseEntity<List<String>> getAllCountries() {
        
    	log.info("API: Fetching all countries");
        
    	return ResponseEntity.ok(geoService.getAllCountries());
    }

    @GetMapping("/states/{country}")
    public ResponseEntity<List<String>> getStatesByCountry(
    		@PathVariable String country) {
        
    	log.info("API: Fetching states for country={}", country);
        
    	return ResponseEntity.ok(geoService.getStatesByCountry(country));
    }

    @GetMapping("/cities/{country}/{state}")
    public ResponseEntity<List<String>> getCitiesByState(
            @PathVariable String country,
            @PathVariable String state) {
      
    	log.info("API: Fetching cities for country={} and state={}", country, state);
        
    	return ResponseEntity.ok(geoService.getCitiesByState(country, state));
    }
}
