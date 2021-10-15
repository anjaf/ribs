package uk.ac.ebi.biostudies.service.impl;


import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import uk.ac.ebi.biostudies.config.FireConfig;
import uk.ac.ebi.biostudies.config.SecurityConfig;

import java.io.FileNotFoundException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

@Service
public class FireService {
    private final Logger LOGGER = LogManager.getLogger(FireService.class);
    @Autowired
    FireConfig fireConfig;
    @Autowired
    AmazonS3 s3;
    @Autowired
    SecurityConfig securityConfig;

    /*public InputStream getFireObjectById(String fireId) throws Exception {
        HttpClientBuilder clientBuilder = HttpClients.custom();
        InputStreamResource inputStreamResource = new InputStreamResource();
        if(securityConfig.getHttpProxyHost()!=null && !securityConfig.getHttpProxyHost().isEmpty()) {
            clientBuilder.setProxy(new HttpHost(securityConfig.getHttpProxyHost(), securityConfig.getGetHttpProxyPort()));
        }
        CloseableHttpClient httpClient = clientBuilder
            .setSSLSocketFactory(new SSLConnectionSocketFactory(SSLContexts.custom()
                            .loadTrustMaterial(null, new TrustSelfSignedStrategy())
                            .build(), NoopHostnameVerifier.INSTANCE
                    )
            ).build();
        HttpPost httpPost = new HttpPost(securityConfig.getAsperaTokenServer());
        ObjectMapper mapper = new ObjectMapper();
        StringEntity entity = new StringEntity(String.format(JSONCONFIG, requestFiles.toString()));
        httpPost.setEntity(entity);
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json");
        UsernamePasswordCredentials creds
                = new UsernamePasswordCredentials(securityConfig.getAsperaUser(), securityConfig.getAsperaPass());
        httpPost.addHeader(new BasicScheme().authenticate(creds, httpPost, null));
        CloseableHttpResponse response = httpClient.execute(httpPost);
        String result = IOUtils.toString(response.getEntity().getContent(), StandardCharsets.UTF_8);
        jsonResult = mapper.readTree(result);
        response.close();

        return jsonResult;
    }*/


    public StringWriter getFireObjectStringContentByPath(String path, String bucketName) {
        if (bucketName == null)
            bucketName = fireConfig.getBucketName();
        GetObjectRequest getObjectRequest = new GetObjectRequest(bucketName, path);
        try (S3ObjectInputStream inputStream = s3.getObject(getObjectRequest).getObjectContent()) {
            StringWriter stringWriter = new StringWriter();
            IOUtils.copy(inputStream, stringWriter, StandardCharsets.UTF_8);
            return stringWriter;
        } catch (Exception exception) {
            LOGGER.error(exception);
            return null;
        }
    }

    public InputStreamResource getFireObjectInputStream(String path) throws FileNotFoundException {
        String bucketName = fireConfig.getBucketName();
        GetObjectRequest getObjectRequest = new GetObjectRequest(bucketName, path);
        try {
            S3ObjectInputStream inputStream = s3.getObject(getObjectRequest).getObjectContent();
            return  new InputStreamResource(inputStream.getDelegateStream());
        } catch (Exception exception) {
            LOGGER.error(exception);
            if (exception.getMessage() != null && exception.getMessage().contains("Not Found"))
                throw new FileNotFoundException(exception.getMessage());
            return null;
        }
    }

    public Stack<String> getAllDirectoryContent(List<String> pathNameList) throws Exception {
        Stack<String> allFileResult = new Stack<>();
        if (pathNameList == null || pathNameList.size() == 0) {
            return allFileResult;
        }
        allFileResult.addAll(pathNameList.stream().filter(path -> StringUtils.substringAfterLast(path, "/").contains(".")).collect(Collectors.toList()));

        Stack<String> subDirectoriesStack = new Stack<>();

        subDirectoriesStack.addAll(pathNameList.stream().filter(path -> !StringUtils.substringAfterLast(path, "/").contains(".")).collect(Collectors.toList()));
        try {
            while (subDirectoriesStack.size() > 0) {
                String currentPath = subDirectoriesStack.pop();
                ObjectListing objectListing = s3.listObjects(fireConfig.getBucketName(), currentPath);
                do {
                    allFileResult.addAll(objectListing.getObjectSummaries().stream().map(sum -> sum.getKey()).collect(Collectors.toList()));
                    List<String> embeddedDirectories = objectListing.getCommonPrefixes();
                    subDirectoriesStack.addAll(embeddedDirectories);
                } while (objectListing.isTruncated());
            }
        } catch (Exception exception) {
            LOGGER.error(exception);
        }
        return allFileResult;
    }
}
