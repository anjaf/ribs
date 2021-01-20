package uk.ac.ebi.biostudies.api.util;

import org.apache.lucene.facet.DrillDownQuery;
import org.apache.lucene.search.Query;

import java.util.HashSet;
import java.util.Set;

public class DrillDownWrapper {
    private Query baseQuery;
    private Set<String> drillDownDims;
    private DrillDownQuery drillDownQuery;
    public DrillDownWrapper(Query baseQuery){
        this.baseQuery = baseQuery;
        drillDownDims = new HashSet<>();
    }
    public void addDim(String dim){
        drillDownDims.add(dim);
    }

    public Query getBaseQuery() {
        return baseQuery;
    }

    public void setBaseQuery(Query baseQuery) {
        this.baseQuery = baseQuery;
    }

    public Set<String> getDrillDownDims() {
        return drillDownDims;
    }

    public void setDrillDownDims(Set<String> drillDownDims) {
        this.drillDownDims = drillDownDims;
    }

    public DrillDownQuery getDrillDownQuery() {
        return drillDownQuery;
    }

    public void setDrillDownQuery(DrillDownQuery drillDownQuery) {
        this.drillDownQuery = drillDownQuery;
    }
}
