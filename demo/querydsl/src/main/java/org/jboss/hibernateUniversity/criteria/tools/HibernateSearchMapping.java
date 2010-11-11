package org.jboss.hibernateUniversity.criteria.tools;

import org.apache.solr.analysis.LowerCaseFilterFactory;
import org.apache.solr.analysis.NGramFilterFactory;
import org.apache.solr.analysis.StandardTokenizerFactory;

import org.hibernate.search.annotations.Factory;
import org.hibernate.search.cfg.SearchMapping;

/**
 * @author Emmanuel Bernard
 */
public class HibernateSearchMapping {

	/**
	 * Configure global settings
	 * cleaner than annotations
	 * @return
	 */
	@Factory
	SearchMapping getSearchMapping() {
		SearchMapping mapping = new SearchMapping();
		mapping
				.analyzerDef( "ngram", StandardTokenizerFactory.class )
					.filter( LowerCaseFilterFactory.class )
					.filter( NGramFilterFactory.class )
						.param( "minGramSize", "3" )
						.param( "maxGramSize", "3" );
		return mapping;
	}
}
