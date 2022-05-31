package pl.damiankaplon.entity;

import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;

@MongoEntity(collection = "accounts")
@Getter
@Setter
public class Account {
    private ObjectId id;
    private String email;
    private String password;
    private String name;
    private String surname;
}
