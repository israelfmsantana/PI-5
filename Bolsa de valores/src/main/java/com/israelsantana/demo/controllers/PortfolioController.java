package com.israelsantana.demo.controllers;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.israelsantana.demo.models.Portfolio;
import com.israelsantana.demo.models.dto.PortfolioCreateDTO;
import com.israelsantana.demo.models.dto.PortfolioUpdateDTO;
import com.israelsantana.demo.models.projection.PortfolioProjection;
import com.israelsantana.demo.services.PortfolioService;

@RestController
@RequestMapping("/portfolio")
@Validated
public class PortfolioController {
    
    @Autowired
    private PortfolioService portfolioService;


    // Authentication required
    @GetMapping("/{id}")
    public ResponseEntity<Portfolio> findById(@PathVariable Long id) {
        Portfolio obj = this.portfolioService.findById(id);
        return ResponseEntity.ok(obj);
    }

    // Authentication required
    @GetMapping()
    public ResponseEntity<List<Portfolio>> findAll() {
        List<Portfolio> portfolios = this.portfolioService.findAll();
        return ResponseEntity.ok().body(portfolios);
    }

    // Authentication required
    @GetMapping("/user/ids/{ids}")
    public ResponseEntity<List<PortfolioProjection>> findAllByUser_ids(@PathVariable List<Long> ids ) {
        List<PortfolioProjection> portfolioProjections = this.portfolioService.findAllByUser_ids(ids);
        return ResponseEntity.ok().body(portfolioProjections);
    }

    // Authentication required
    @GetMapping("/pal/ids/{ids}")
    public ResponseEntity<List<PortfolioProjection>> findAllByPal_ids(@PathVariable List<Long> ids ) {
        List<PortfolioProjection> portfolioProjections = this.portfolioService.findAllByPal_ids(ids);
        return ResponseEntity.ok().body(portfolioProjections);
    }

    // Authentication required ADMIN
    @GetMapping("/user_login_admin")
    public ResponseEntity<List<Portfolio>> findAllByUser_login_Admin() {
        List<Portfolio> portfolios = this.portfolioService.findAllByUser_login_Admin();
        return ResponseEntity.ok().body(portfolios);
    }



    // Authentication required ADMIN
    @PostMapping
    @Validated
    public ResponseEntity<Void> create(@Valid @RequestBody PortfolioCreateDTO obj) {
        Portfolio portfolio = this.portfolioService.fromDTO(obj);
        Portfolio newPortfolio = this.portfolioService.create(portfolio);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(newPortfolio).toUri();
        return ResponseEntity.created(uri).build();
    }

    // Authentication required ADMIN
    @PutMapping("/{id}")
    @Validated
    public ResponseEntity<Void> update(@Valid @RequestBody PortfolioUpdateDTO obj, @PathVariable Long id) {
        obj.setId(id);
        Portfolio portfolio = this.portfolioService.fromDTO(obj);
        this.portfolioService.update(portfolio);
        return ResponseEntity.noContent().build();
    }

    // Authentication required ADMIN
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        this.portfolioService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
