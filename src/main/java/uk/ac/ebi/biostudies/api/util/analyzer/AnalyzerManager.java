package uk.ac.ebi.biostudies.api.util.analyzer;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.miscellaneous.PerFieldAnalyzerWrapper;
import org.springframework.stereotype.Service;
import uk.ac.ebi.biostudies.api.BioStudiesField;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ehsan on 27/03/2017.
 */
@Service
public class AnalyzerManager {

    private Map<String, Analyzer> perFieldAnalyzerMap= new HashMap<>();
    private PerFieldAnalyzerWrapper perFieldAnalyzerWrapper;
    private Map<String, String> expandableFields = new HashMap<>();

    @PostConstruct
    void init(){
        for(BioStudiesField bsField: BioStudiesField.values()){
            if(bsField.getAnalyzer()!=null)
                perFieldAnalyzerMap.put(bsField.toString(), bsField.getAnalyzer());
            if(bsField.isExpand())
                expandableFields.put(bsField.toString(), bsField.toString());
        }
        perFieldAnalyzerWrapper = new PerFieldAnalyzerWrapper(new AttributeFieldAnalyzer(), perFieldAnalyzerMap);
    }

    public PerFieldAnalyzerWrapper getPerFieldAnalyzerWrapper(){
        return perFieldAnalyzerWrapper;
    }

    public Map<String, String> getExpandableFields(){
        return expandableFields;
    }
}
