package com.infor.scs.interviews.transformers;

import com.infor.scs.interviews.domain.GlossaryResponse;
import com.infor.scs.interviews.domain.Term;

public class GlossaryTransformer {

    public static void transform( GlossaryResponse glossaryResponse ) {

        if( glossaryResponse != null || glossaryResponse.getTerms() != null ) {

            /* The National Weather Service was producing an empty Term as the first entry in their glossary.
             * This became a useful tool for the technical interview because it gave an opportunity to see debugging skills
             * targeting a root cause originating outside our code base. While it would be best to replace the National
             * Weather Service API with our own service, for now we will recreate the scenario artificially.
             */
            Term missingNwaTerm = new Term();

            glossaryResponse.getTerms().add( 0, missingNwaTerm );
        }
    }

}
