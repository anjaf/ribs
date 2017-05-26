/*
 * Copyright 2009-2016 European Molecular Biology Laboratory
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package uk.ac.ebi.biostudies.efo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.lucene.index.*;
import org.apache.lucene.util.BytesRef;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import uk.ac.ebi.arrayexpress.utils.efo.EFONode;
import uk.ac.ebi.arrayexpress.utils.efo.IEFO;
import uk.ac.ebi.biostudies.api.BioStudiesField;
import uk.ac.ebi.biostudies.api.BioStudiesFieldType;
import uk.ac.ebi.biostudies.efo.autocompletion.AutocompleteData;
import uk.ac.ebi.biostudies.efo.autocompletion.AutocompleteStore;
import uk.ac.ebi.biostudies.config.IndexManager;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
@Scope("singleton")
public class Autocompletion {

    private Logger logger = LogManager.getLogger(Autocompletion.class.getName());

    @Autowired
    IndexManager indexManager;


    private AutocompleteStore autocompleteStore;

    private IEFO efo;



    @PostConstruct
    public void initialize() throws Exception {
        this.autocompleteStore = new AutocompleteStore();
    }


    private IEFO getEfo() {
        return this.efo;
    }

    public void setEfo(IEFO efo) {
        this.efo = efo;
    }

    public String getKeywords(String prefix, String field, Integer limit) {
        StringBuilder sb = new StringBuilder("");
        List<AutocompleteData> matches = getStore().findCompletions(prefix, field, limit);
        for (AutocompleteData match : matches) {
            sb.append(match.getText()).append('|').append(match.getDataType()).append('|').append(match.getData()).append('\n');
        }
        return sb.toString();
    }

    public String getEfoWords(String prefix, Integer limit) {
        StringBuilder sb = new StringBuilder("");
        List<AutocompleteData> matches = getStore().findCompletions(prefix, "", 1000);
        int counter = 0;
        for (AutocompleteData match : matches) {
            if (AutocompleteData.DATA_EFO_NODE == match.getDataType()) {
                if (limit == null || counter < limit) {
                    sb.append(match.getText()).append('|').append(match.getDataType()).append('|').append(match.getData()).append('\n');
                    counter++;
                } else {
                    break;
                }
            }
        }
        return sb.toString();
    }

    public String getEfoChildren(String efoId) {
        StringBuilder sb = new StringBuilder();

        if (null != getEfo()) {
            EFONode node = getEfo().getMap().get(efoId);
            if (null != node) {
                Set<EFONode> children = node.getChildren();
                if (null != children) {
                    for (EFONode child : children) {
                        sb.append(child.getTerm()).append("|o|");
                        if (child.hasChildren()) {
                            sb.append(child.getId());
                        }
                        sb.append("\n");
                    }
                }
            }
        }
        return sb.toString();
    }

    public void rebuild() throws IOException {
        getStore().clear();
        List<BioStudiesField> numericalFieldNameTitle = new ArrayList();
        //Add the fields that you want autoComplete be Applied
        for(BioStudiesField bsField:BioStudiesField.values()) {
            if(bsField.isExpand())
                numericalFieldNameTitle.add(bsField);
        }

        // adding field terms (for all non-numerical fields) and names (if there is a description)
        for (BioStudiesField field : numericalFieldNameTitle) {
            String fieldTitle = field.toString();
            String fieldName = field.toString();
            if (null != fieldTitle && fieldTitle.length() > 0) {
                getStore().addData(
                        new AutocompleteData(
                                fieldTitle
                                , AutocompleteData.DATA_FIELD
                                , fieldTitle
                        )
                );
            }
            BioStudiesFieldType fieldType = field.getType();
            if (fieldType!=BioStudiesFieldType.LONG) {
                List<String> terms = getTerms(fieldName, "content".equals(fieldName) ? 10 : 1);
                for (String term : terms ) {
                    getStore().addData(
                            new AutocompleteData(
                                    term
                                    , AutocompleteData.DATA_TEXT
                                    , fieldName
                            )
                    );
                }
            }
        }

        if (null != getEfo()) {
            addEfoNodeWithDescendants(IEFO.ROOT_ID);
        }
    }

    public List<String> getTerms(String fieldName, int minFreq) throws IOException {
        List<String> termsList = new ArrayList<>();

        try {
            IndexReader reader = indexManager.getIndexReader();
            Terms terms = MultiFields.getTerms(reader, fieldName);
            if (null != terms) {
                TermsEnum iterator = terms.iterator();
                BytesRef byteRef;
                while((byteRef = iterator.next()) != null) {
                    if (iterator.docFreq() >= minFreq) {
                        termsList.add(byteRef.utf8ToString());
                    }
                }
            }
        }
        catch (Exception ex){
            logger.error("getTerms problem",ex);
        }
        return termsList;
    }


    private void addEfoNodeWithDescendants(String nodeId) {
        EFONode node = getEfo().getMap().get(nodeId);
        // make node expandable only if it has children and not organizational class
        if (null != node) {
            getStore().addData(
                    new AutocompleteData(
                            node.getTerm()
                            , AutocompleteData.DATA_EFO_NODE
                            , node.hasChildren() && !node.isOrganizationalClass() ? node.getId() : ""
                    )
            );

            if (node.hasChildren()) {
                for (EFONode child : node.getChildren()) {
                    addEfoNodeWithDescendants(child.getId());
                }
            }
        }
    }

    private AutocompleteStore getStore() {
        return this.autocompleteStore;
    }
}
