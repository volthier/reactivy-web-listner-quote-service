package com.volthier.mongodbreactivestockquoteservice.service;

import com.volthier.mongodbreactivestockquoteservice.client.StockQuoteClient;
import com.volthier.mongodbreactivestockquoteservice.domain.Quote;
import com.volthier.mongodbreactivestockquoteservice.repositories.QuoteRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class QuoteMonitorService implements ApplicationListener<ContextRefreshedEvent> {

    private final StockQuoteClient stockQuoteClient;
    private final QuoteRepository quoteRepository;

    public QuoteMonitorService(StockQuoteClient stockQuoteClient, QuoteRepository quoteRepository) {
        this.stockQuoteClient = stockQuoteClient;
        this.quoteRepository = quoteRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        stockQuoteClient.getQuoteStream()
                .log("quote-monitor-service")
                .subscribe( quote -> {
                    Mono<Quote> savedQuote = quoteRepository.save(quote);

                    savedQuote.subscribe(q -> System.out.println("I saved a quote! Id: " + q.getId()));
        });

    }

}
