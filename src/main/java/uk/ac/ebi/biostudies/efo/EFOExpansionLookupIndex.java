package uk.ac.ebi.biostudies.efo;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.index.*;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import uk.ac.ebi.arrayexpress.utils.efo.EFONode;
import uk.ac.ebi.arrayexpress.utils.efo.IEFO;
import uk.ac.ebi.biostudies.lucene.config.EFOConfig;
import uk.ac.ebi.biostudies.lucene.config.IndexManager;

import java.io.IOException;
import java.util.*;

/**
 * Created by ehsan on 02/03/2017.
 */

@Component
@Scope("singleton")
public class EFOExpansionLookupIndex {

    private Logger logger = LogManager.getLogger(EFOExpansionLookupIndex.class.getName());


    @Autowired
    IndexManager indexManager;
    @Autowired
    EFOConfig efoConfig;


    private IEFO efo;
    private Map<String, Set<String>> customSynonyms;

    // maximum number of index documents to be processed; in reality shouldn't be more than 2
    private static final int MAX_INDEX_HITS = 16;


    private IEFO getEfo() {
        return this.efo;
    }

    public void setEfo(IEFO efo) {
        this.efo = efo;
    }

    public void setCustomSynonyms(Map<String, Set<String>> synonyms) {
        this.customSynonyms = synonyms;
    }

    public void buildIndex() throws IOException, InterruptedException {
        try {
            this.logger.debug("Building expansion lookup index");
            IndexWriter indexWriter = indexManager.getEfoIndexWriter();
            addNodeAndChildren(this.efo.getMap().get(IEFO.ROOT_ID), indexWriter);
            addCustomSynonyms(indexWriter);
            indexWriter.commit();
            this.logger.debug("Building completed");
        }catch (Exception ex){
            logger.error("problem in creating efo index",ex);
        }
    }

    private void addCustomSynonyms(IndexWriter w) throws IOException, InterruptedException {
        // here we add all custom synonyms so those that weren't added during EFO processing
        //  get a chance to be included, too. don't worry about duplication, dupes will be removed during retrieval
        if (null != this.customSynonyms) {
            Set<String> addedTerms = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
            for (String term : this.customSynonyms.keySet()) {
                if (!addedTerms.contains(term)) {
                    Document d = new Document();

                    Set<String> syns = this.customSynonyms.get(term);
                    for (String syn : syns) {
                        addIndexField(d, "term", syn, true, true);

                    }
                    w.addDocument(d);
                    addedTerms.addAll(syns);
                }
            }
        }
    }

    private void addNodeAndChildren(EFONode node, IndexWriter w) throws IOException, InterruptedException {
        Thread.sleep(0);
        if (null != node) {
            addNodeToIndex(node, w);
            for (EFONode child : node.getChildren()) {
                addNodeAndChildren(child, w);
            }
        }
    }

    private void addNodeToIndex(EFONode node, IndexWriter w) throws IOException, InterruptedException {
        String term = node.getTerm();

        if (null != term && !isStopTerm(term)) {
            Set<String> synonyms = node.getAlternativeTerms();

            // if the node represents organizational class, just include its synonyms, but not children
            Set<String> childTerms =
                    node.isOrganizationalClass()
                            ? new HashSet<String>()
                            : getEfo().getTerms(node.getId(), IEFO.INCLUDE_CHILDREN);

            // here we add custom synonyms to EFO synonyms/child terms and their synonyms
            if (null != this.customSynonyms) {
                for (String syn : new HashSet<>(synonyms)) {
                    if (null != syn && this.customSynonyms.containsKey(syn)) {
                        synonyms.addAll(this.customSynonyms.get(syn));
                    }
                }

                if (this.customSynonyms.containsKey(term)) {
                    synonyms.addAll(this.customSynonyms.get(term));
                }

                for (String child : new HashSet<>(childTerms)) {
                    if (null != child && this.customSynonyms.containsKey(child)) {
                        childTerms.addAll(this.customSynonyms.get(child));
                    }
                }
            }
            if (synonyms.contains(term)) {
                synonyms.remove(term);
            }

            // just to remove ridiculously long terms/synonyms from the list


            if (synonyms.size() > 0 || childTerms.size() > 0) {

                Document d = new Document();

                int terms = 0, efoChildren = 0;

                for (String syn : synonyms) {
                    if (childTerms.contains(syn)) {
                        // this.logger.debug("Synonym [{}] for term [{}] is present as a child term itelf, skipping", syn, term);
                    } else if (isStopExpansionTerm(syn)) {
                        // this.logger.debug("Synonym [{}] for term [{}] is a stop-word, skipping", syn, term);
                    } else {
                        addIndexField(d, "term", syn, true, true);
                        addIndexField(d, "all", syn, true, true);
                        terms++;
                    }
                }

                for (String efoTerm : childTerms) {
                    if (isStopExpansionTerm(efoTerm)) {
                        // this.logger.debug("Child EFO term [{}] for term [{}] is a stop-word, skipping", efoTerm, term);
                    } else {
                        addIndexField(d, "efo", efoTerm, false, true);
                        addIndexField(d, "all", efoTerm, true, true);
                        efoChildren++;
                    }
                }

                if (!isStopExpansionTerm(term)) {
                    addIndexField(d, "term", term, true, true);
                    addIndexField(d, "all", term, true, true);
                    terms++;
                }

                if (terms > 1 || (1 == terms && efoChildren > 0)) {
                    w.addDocument(d);
                } else {
                    // this.logger.debug("EFO term [{}] was not added due to insufficient mappings", term);
                }

                Thread.sleep(0);
            }
        } else {
            // this.logger.debug("EFO Term [{}] is a stop-word, skipping", term);
        }
    }

    public EFOExpansionTerms getExpansionTerms(Query origQuery) throws IOException {
        EFOExpansionTerms expansion = new EFOExpansionTerms();

        if (DirectoryReader.indexExists(indexManager.getEfoIndexDirectory())) {

            try{
                IndexSearcher searcher = indexManager.getEfoIndexSearcher();
                Query q = overrideQueryField(origQuery, "term");

                TopDocs hits = searcher.search(q, MAX_INDEX_HITS);
                this.logger.debug("Expansion lookup for query [{}] returned [{}] hits", q.toString(), hits.totalHits);

                for (ScoreDoc d : hits.scoreDocs) {
                    Document doc = searcher.doc(d.doc);
                    String[] terms = doc.getValues("term");
                    String[] efo = doc.getValues("efo");
                    this.logger.debug("Synonyms [{}], EFO Terms [{}]", StringUtils.join(terms, ", "), StringUtils.join(efo, ", "));
                    if (0 != terms.length) {
                        expansion.synonyms.addAll(Arrays.asList(terms));
                    }

                    if (0 != efo.length) {
                        expansion.efo.addAll(Arrays.asList(efo));
                    }
                }
            }catch (Exception ex){
                logger.error("problem in expanding terms for query {}", origQuery.toString(), ex);
            }
        }

        return expansion;
    }

    public Set<String> getReverseExpansion(String text) throws IOException {
        Set<String> reverseExpansion = new HashSet<>();

        if (null != text && DirectoryReader.indexExists(indexManager.getEfoIndexDirectory())) {

            try {
                IndexSearcher searcher = indexManager.getEfoIndexSearcher();

                // step 1: split terms
                String[] terms = text.split("\\s+");

                for (int termIndex = 0; termIndex < terms.length; ++termIndex) {
                    BooleanQuery.Builder qb = new BooleanQuery.Builder();

                    Term t = new Term("all", terms[termIndex]);
                    qb.add(new TermQuery(t), BooleanClause.Occur.SHOULD);

                    for (int phraseLength = 4; phraseLength <= 2; --phraseLength) {
                        if (termIndex + phraseLength > terms.length) {
                            continue;
                        }
                        PhraseQuery.Builder pqb = new PhraseQuery.Builder();
                        for (int phraseTermIndex = 0; phraseTermIndex < phraseLength; ++phraseTermIndex) {
                            t = new Term("all", terms[termIndex + phraseTermIndex]);
                            pqb.add(t);
                        }
                        qb.add(pqb.build(), BooleanClause.Occur.SHOULD);
                    }
                    Query q = qb.build();
                    TopDocs hits = searcher.search(q, MAX_INDEX_HITS);
                    this.logger.debug("Expansion lookup for query [{}] returned [{}] hits", q.toString(), hits.totalHits);

                    for (ScoreDoc d : hits.scoreDocs) {
                        Document doc = searcher.doc(d.doc);
                        String[] reverseTerms = doc.getValues("term");
                        //this.logger.debug("Synonyms [{}], EFO Terms [{}]", StringUtils.join(terms, ", "), StringUtils.join(efo, ", "));
                        if (0 != reverseTerms.length) {
                            reverseExpansion.addAll(Arrays.asList(reverseTerms));
                        }
                    }
                }
            }catch (Exception ex){
                logger.error("", ex);
            }
        }

        return reverseExpansion;
    }

    private IndexWriter createIndex(Directory indexDirectory, Analyzer analyzer) throws IOException {
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        config.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
        return new IndexWriter(indexDirectory, config);
    }

    private void addIndexField(Document document, String name, String value, boolean shouldAnalyze, boolean shouldStore) {
        value = value.replaceAll("[^\\d\\w-]", " ").toLowerCase();
        FieldType fieldType = new FieldType();
        fieldType.setIndexOptions(IndexOptions.DOCS);
        fieldType.setTokenized(shouldAnalyze);
        fieldType.setStored(shouldStore);
        document.add(new Field(name, value, fieldType));
    }

    private Query overrideQueryField(Query origQuery, String fieldName) {
        Query query = new TermQuery(new Term(""));

        try {
            if (origQuery instanceof PrefixQuery) {
                Term term = ((PrefixQuery) origQuery).getPrefix();
                query = new PrefixQuery(new Term(fieldName, term.text()));
            } else if (origQuery instanceof WildcardQuery) {
                Term term = ((WildcardQuery) origQuery).getTerm();
                query = new WildcardQuery(new Term(fieldName, term.text()));
            } else if (origQuery instanceof TermRangeQuery) {
                TermRangeQuery trq = (TermRangeQuery) origQuery;
                query = new TermRangeQuery(fieldName, trq.getLowerTerm(), trq.getUpperTerm(), trq.includesLower(), trq.includesUpper());
            } else if (origQuery instanceof FuzzyQuery) {
                Term term = ((FuzzyQuery) origQuery).getTerm();
                query = new FuzzyQuery(new Term(fieldName, term.text()));
            } else if (origQuery instanceof TermQuery) {
                Term term = ((TermQuery) origQuery).getTerm();
                query = new TermQuery(new Term(fieldName, term.text()));
            } else if (origQuery instanceof PhraseQuery) {
                Term[] terms = ((PhraseQuery) origQuery).getTerms();
                StringBuilder text = new StringBuilder();
                for (Term t : terms) {
                    text.append(t.text()).append(' ');
                }
                query = new TermQuery(new Term(fieldName, text.toString().trim()));
            } else {
                this.logger.error("Unsupported query type [{}]", origQuery.getClass().getCanonicalName());
            }
        } catch (Exception x) {
            this.logger.error("Caught an exception:", x);
        }


        return query;
    }

    private boolean isStopTerm(String str) {
        return null == str || efoConfig.getStopWordsSet().contains(str.toLowerCase());
    }

    private boolean isStopExpansionTerm(String str) {
        return isStopTerm(str) || str.length() < 3 || str.matches(".*(\\s\\(.+\\)|\\s\\[.+\\]|,\\s|\\s-\\s|/|NOS).*");
    }

    @SuppressWarnings("unused")
    private boolean isLongTerm(String str) {
        // returns true if number of words is over 5;
        return null != str && str.replaceAll("\\s+", " ").replaceAll("[^ ]+", "").length() >= 4;
    }
}
