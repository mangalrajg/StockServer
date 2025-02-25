package com.stockserver.infrastucture.tiingo;

import com.stockserver.quote.QuoteEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface QuoteMapper {

    @Mapping(target = "quoteDate", source = "quoteDto.date")
    QuoteEntity map(String ticker, QuoteDto quoteDto);
}
