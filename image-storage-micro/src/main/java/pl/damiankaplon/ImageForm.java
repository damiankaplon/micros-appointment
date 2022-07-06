package pl.damiankaplon;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.ws.rs.FormParam;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ImageForm {
    @FormParam("image1") private File image1;
    @FormParam("image2") private File image2;
    @FormParam("image3") private File image3;
    @FormParam("image4") private File image4;

    public List<File> getImages() {
        List<File> images = new ArrayList<>();
        if(image1 != null)
            images.add(image1);
        if(image2 != null)
            images.add(image2);
        if(image3 != null)
            images.add(image3);
        if(image4 != null)
            images.add(image4);
        return images;
    }
}
