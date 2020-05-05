package uk.ac.ebi.biostudies.service.impl;


import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import uk.ac.ebi.biostudies.api.util.Constants;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BatchDownloadScriptBuilder {
    private static final Logger LOGGER = LogManager.getLogger(BatchDownloadScriptBuilder.class.getName());
    static Map<String, String> DownloadTemplates = new HashMap<>();
    static{
        loadDlTemplates();
    }
    public String fillTemplate(String downloadType, List<String> fileNames , String baseDirectory, String os){
        String content="";
        try{
            String fileTemplate = DownloadTemplates.get(getTemplate(downloadType, os));
            content = fillFileTemplate(fileTemplate, fileNames, baseDirectory, downloadType);
            if(!os.equalsIgnoreCase(Constants.OS.WINDOWS))
                content = "#!/bin/bash\r\n"+content;
        }catch (Exception ex){
            LOGGER.error("Cant open download template file {}", getTemplate(downloadType, os), ex);
        }
        return content;
    }
    String getTemplate(String type, String os){
        String myOs = os;
        if(!myOs.equalsIgnoreCase(Constants.OS.WINDOWS))
            myOs=Constants.OS.UNIX;
        StringBuilder result = new StringBuilder(type);
        result.append("-").append(myOs);
        return result.toString();
    }

    String fillFileTemplate(String fileTemplate,List<String> fileNames, String baseDirectory, String downloadType){
        String content = "";
        if(downloadType.equalsIgnoreCase("ftp")){
            String allFiles = fileNames.stream().map(name -> "mget \""+name+"\"").collect(Collectors.joining("\r\n"));
            content = String.format(fileTemplate, baseDirectory, allFiles);
        }
        else if(downloadType.equalsIgnoreCase("aspera")){
            content = fileNames.stream().map(name -> String.format(fileTemplate, "\""+baseDirectory+"/"+name+"\"")).collect(Collectors.joining("\r\n"));
        }
        return content;
    }

    private static void loadDlTemplates(){
        String fileNames[]= {"aspera-unix", "aspera-windows", "ftp-unix", "ftp-windows"};
        for(String fName:fileNames){
            try {
                InputStream templateStream = new ClassPathResource("batchdl/" + fName).getInputStream();
                String fileTemplate = IOUtils.toString(templateStream, "UTF-8");
                DownloadTemplates.put(fName, fileTemplate);
                templateStream.close();
            }catch (Exception ex){
                LOGGER.error("Cant open download template file {}", fName, ex);
            }
        }
    }
}