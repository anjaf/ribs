package uk.ac.ebi.biostudies.api.util;

import org.apache.lucene.facet.DrillDownQuery;
import org.apache.lucene.facet.FacetsConfig;
import org.apache.lucene.search.Query;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DrillDownWrapper {
    private Query baseQuery;
    private Map<String,Set<String>> drillDownDims;
    private DrillDownQuery drillDownQuery;
    FacetsConfig facetsConfig;
    public DrillDownWrapper(FacetsConfig facetsConfig, Query baseQuery){
        this.baseQuery = baseQuery;
        drillDownDims = new HashMap<>();
        this.facetsConfig=facetsConfig;
    }
    public void addDim(String dimKey, String pathFacetValue){
        Set<String> dimDropDownFilters = drillDownDims.get(dimKey);
        if(dimDropDownFilters==null) {
            dimDropDownFilters = new HashSet<>();
            drillDownDims.put(dimKey,dimDropDownFilters);
        }
        dimDropDownFilters.add(pathFacetValue);
    }

    /**
     * to create DrillSideways Query, that leads creation of DrillDownQuery for each dimension without considering that dimension selected facets
     * @return Map from facet dimensions to related DrillDownQuery this map will be used by FacetCollectors
     */
    public Map<String, DrillDownQuery> generateDrillDownQueries(){
        Map<String, DrillDownQuery> result = new HashMap<>();
        DrillDownQuery dimDrillDownQuery;
        result.put(Constants.IndexEntryAttributes.DEFAULT_VALUE, drillDownQuery);
        Set<String> dims = drillDownDims.keySet();
        if(dims==null)
            return result;
        for(String dim:dims){
            dimDrillDownQuery = new DrillDownQuery(facetsConfig, baseQuery);
            result.put(dim, dimDrillDownQuery);
            for(String filterDim:dims){
                Set<String> dropDownFacetFilters = drillDownDims.get(filterDim);
                if(dropDownFacetFilters == null)
                    continue;
                for(String facetFilterValue:dropDownFacetFilters) {
                    if(filterDim.equalsIgnoreCase(dim))
                        continue;
                    dimDrillDownQuery.add(filterDim, facetFilterValue);
                }
            }
        }
        return result;
    }

    public Query getBaseQuery() {
        return baseQuery;
    }

    public void setBaseQuery(Query baseQuery) {
        this.baseQuery = baseQuery;
    }

    public Map<String, Set<String>> getDrillDownDims() {
        return drillDownDims;
    }


    public DrillDownQuery getDrillDownQuery() {
        return drillDownQuery;
    }

    public void setDrillDownQuery(DrillDownQuery drillDownQuery) {
        this.drillDownQuery = drillDownQuery;
    }
}
