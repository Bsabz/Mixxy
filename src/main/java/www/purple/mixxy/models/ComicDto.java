package www.purple.mixxy.models;

/**
 * Created by Brian_Sabz on 4/7/16.
 * 
 * @author Brian_Sabz
 */
import java.util.List;
import javax.validation.constraints.Size;
import com.google.appengine.api.images.Image;

public class ComicDto {

	@Size(min = 5)
	public String title;

	@Size(min = 5)
	public String description;

	public List<Image> images;

	public List<String> tags;

	public int likes;

}
