package com.volthier.mongodbreactivestockquoteservice.repositories;

import com.volthier.mongodbreactivestockquoteservice.domain.Quote;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.Tailable;
import reactor.core.publisher.Flux;

public interface QuoteRepository extends ReactiveMongoRepository<Quote, String> {

    @Tailable // Use a tailable cursor
    Flux<Quote> findWithTailableCursorBy();
}
