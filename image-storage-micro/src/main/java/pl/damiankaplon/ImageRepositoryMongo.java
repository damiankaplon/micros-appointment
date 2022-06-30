package pl.damiankaplon;

import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ImageRepositoryMongo implements ReactivePanacheMongoRepository<ImageEntity> {
}
