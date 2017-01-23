import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.PutObjectResult;

public class Test_aliyunoss {

	public static void main(String[] args) throws Exception {
		
	    // endpoint以杭州为例，其它region请按实际情况填写
	    String endpoint = "http://oss-cn-beijing.aliyuncs.com";
	    // accessKey请登录https://ak-console.aliyun.com/#/查看
	    String accessKeyId = "aJz5xtsPpE9YeL8N";
	    String accessKeySecret = "qcoAmZWWNpraprwt9S6KbAGXiYploW";
	    ClientConfiguration conf = new ClientConfiguration();
	    // 设置OSSClient使用的最大连接数，默认1024
	    conf.setMaxConnections(200);
	    // 设置请求超时时间，默认50秒
	    conf.setSocketTimeout(10000);
	    // 设置失败请求重试次数，默认3次
	    conf.setMaxErrorRetry(5);
	    
	    // 创建OSSClient实例
	    OSSClient client = new OSSClient(endpoint, accessKeyId, accessKeySecret, conf);
	    client.deleteObject("sourcod", "aa/cccccc.jpg");
	    // 使用访问OSS
//	    String content = "Hello OSS";
//	    PutObjectResult result =  client.putObject("sourcod", "aa/cccccc.jpg", new URL("http://img2.hitumedia.com/validateImage/20161115/201611151602400098434.jpg").openStream());
//	    InputStream a = result.getCallbackResponseBody();
//	    System.out.println(result.getETag());
	    // 关闭client
	    client.shutdown();
	}

}
