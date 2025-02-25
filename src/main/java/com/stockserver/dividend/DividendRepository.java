package com.stockserver.dividend;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DividendRepository extends JpaRepository<DividendEntity, String> {
    List<DividendEntity> findAllByTicker(String ticker);
}
