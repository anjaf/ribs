package uk.ac.ebi.biostudies.api.util;

import org.junit.Assert;
import org.junit.Test;

public class StudyUtilsTest {

    @Test
    public void getPartitionedPath() {
        Assert.assertEquals  ("S-BSMS/S-BSMS0-99/S-BSMS1", StudyUtils.getPartitionedPath("S-BSMS1"));
    }


}
