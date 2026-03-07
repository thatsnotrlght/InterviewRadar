package com.thatsnotrlght.interview_radar.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thatsnotrlght.interview_radar.model.Company;
import com.thatsnotrlght.interview_radar.repository.CompanyRepository;
import com.thatsnotrlght.interview_radar.service.GhostService;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {
	@Autowired
    private GhostService ghostService;
	
	@Autowired
    private CompanyRepository companyRepository;

    // This is the "URL" you will visit in your browser
//    @GetMapping("/{id}")
//    public Map<String, Object> getGhostScore(@PathVariable Long id) {
//        double probability = ghostService.calculateGhostProbability(id);
//        
//        // We return a Map so Spring converts it into a JSON object automatically
//        return Map.of(
//            "companyId", id,
//            "ghostProbability", (probability * 100) + "%",
//            "message", probability > 0.5 ? "High Ghosting Risk" : "Likely Safe"
//        );
//    }
    
    @GetMapping
    public List<Company> getAllCompanies() {
    	return companyRepository.findAll();
    }
    
    @PostMapping
    public Company createCompany(@RequestBody Company company) {
    	return companyRepository.save(company);
    }
    
    @DeleteMapping("/{id}")
    public String deleteCompany(@PathVariable Long id) {
    	companyRepository.deleteById(id);
    	return "Company with ID " + id + " has been deleted.";    
    }
    
 // 4. PUT: Update an existing company
//    @PutMapping("/{id}")
//    public Company updateCompany(@PathVariable Long id, @RequestBody Company companyDetails) {
//        // 1. Find the existing company by ID
//        Company company = companyRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Company not found with id: " + id));
//
//        // 2. Update the fields with the new data from the "Body"
//        company.setName(companyDetails.getName());
//        company.setWebsiteUrl(companyDetails.getWebsiteUrl());
//
//        // 3. Save the updated object back to the database
//        return companyRepository.save(company);
//    }
    
}
