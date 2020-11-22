package Service;

import java.io.*;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.html.HtmlParser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;

public class TikaService implements Service.Interface.ITikaService {
    @Override
    public String extract(String xml) throws IOException,SAXException, TikaException {
        //detecting the file type
        BodyContentHandler handler = new BodyContentHandler();

        Metadata metadata = new Metadata();
        ByteArrayInputStream inputstream = new ByteArrayInputStream(xml.getBytes());
        ParseContext pcontext = new ParseContext();

        //Html parser
        HtmlParser htmlparser = new HtmlParser();

        htmlparser.parse(inputstream, handler, metadata,pcontext);

        //debug:
        //System.out.println("Contents of the document:" + handler.toString());
        //System.out.println("Metadata of the document:");
        //String[] metadataNames = metadata.names();

        //for(String name : metadataNames) {
        //    System.out.println(name + ": " + metadata.get(name));
        //}

        return handler.toString();
    }
}
