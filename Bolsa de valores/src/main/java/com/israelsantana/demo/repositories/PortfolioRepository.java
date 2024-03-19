package com.israelsantana.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.israelsantana.demo.models.Portfolio;
import com.israelsantana.demo.models.projection.PortfolioProjection;

@Repository
public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {

    List<PortfolioProjection> findAllByUserIdIn(List<Long> ids);

    List<PortfolioProjection> findAllByActionIdIn(List<Long> ids);
}
