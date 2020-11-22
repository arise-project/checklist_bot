package Service.Interface;

import org.xml.sax.SAXException;
import org.apache.tika.exception.TikaException;
import java.io.IOException;

public interface ITikaService {
    String extract(String xml) throws IOException, SAXException, TikaException;
}
