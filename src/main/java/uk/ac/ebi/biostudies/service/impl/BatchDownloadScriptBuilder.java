package uk.ac.ebi.biostudies.service.impl;


import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import uk.ac.ebi.biostudies.api.util.Constants;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BatchDownloadScriptBuilder {
    public String fillTemplate(String downloadType, List<String> fileNames , String baseDirectory, String os){
        String content="";
        try{
            File templateFile = new ClassPathResource("batchdl/"+getTemplate(downloadType, os)).getFile();
            String fileTemplate = new String ( Files.readAllBytes(templateFile.toPath()), "UTF-8" );
            content = fillFileTemplate(fileTemplate, fileNames, baseDirectory, downloadType);
        }catch (Exception ex){}
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
            String allFiles = fileNames.stream().map(name -> "\""+name+"\"").collect(Collectors.joining(" "));
            content = String.format(fileTemplate, baseDirectory, allFiles);
        }
        else if(downloadType.equalsIgnoreCase("aspera")){
            String allFiles = fileNames.stream().map(name -> "\""+baseDirectory+"/"+name+"\"").collect(Collectors.joining(" "));
            content = String.format(fileTemplate, allFiles);
        }
        return content;
    }
}
