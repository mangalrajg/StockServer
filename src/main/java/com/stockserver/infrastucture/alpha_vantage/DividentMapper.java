package com.stockserver.infrastucture.alpha_vantage;

import com.stockserver.dividend.DividendEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DividentMapper {
    DividendEntity map(String ticker, DividendEntryDto x);
}
