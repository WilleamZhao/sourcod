import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by willeam on 16-8-22.
 */
public class httpClientTest {
    public static void main(String[] args) {
        // String url = "http://www.infoq.com/cn/bigdata/?utm_source=infoq&utm_medium=header_graybar&utm_campaign=topic_clk";
        String alimamaUrl = "http://pub.alimama.com/promo/item/channel/index.htm?spm=a219t.7664554.1998457203.60.iV2dXF&channel=nzjh";
        HttpClient hc = new DefaultHttpClient();
        HttpGet hg = new HttpGet(alimamaUrl);
        HttpResponse response = null;
        try {
            response = hc.execute(hg);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                Header header = entity.getContentEncoding();
                HeaderElement[] he = header.getElements();
                he[0].getName();
                System.out.print(he[0].getName());
                InputStream instream = entity.getContent();
                int l;
                byte[] tmp = new byte[2048];
                System.out.print(instream.toString());
//                while ((l = instream.read(tmp)) != -1) {
//                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
