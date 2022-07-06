package pl.damiankaplon;

import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.Getter;
import lombok.Setter;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

@MongoEntity(collection = "images")
@Setter
@Getter
public class ImageEntity {
    @BsonProperty("_id") private ObjectId id;
    @BsonProperty("candyId") private ObjectId candyId;
    @BsonProperty("no") private int no;
    @BsonProperty("path") private String path;
}
