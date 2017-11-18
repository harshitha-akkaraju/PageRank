/*
 * Team Members:
 * Harshitha Akkaraju
 * Shaarika Kaul
 */
package search.analyzers;

import datastructures.concrete.KVPair;
import datastructures.concrete.dictionaries.ChainedHashDictionary;
import datastructures.interfaces.IDictionary;
import datastructures.interfaces.IList;
import datastructures.interfaces.ISet;
import misc.exceptions.NotYetImplementedException;
import search.models.Webpage;

import java.net.URI;
import java.util.Iterator; // TODO: Check if we are allowed to import iterator

/**
 * This class is responsible for computing how "relevant" any given document is
 * to a given search query.
 *
 * See the spec for more details.
 */
public class TfIdfAnalyzer {
    // This field must contain the IDF score for every single word in all
    // the documents.
    private IDictionary<String, Double> idfScores;	//  ln (total num docs/num docs containing term)
    // This field must contain the TF-IDF vector for each webpage you were given
    // in the constructor.
    //
    // We will use each webpage's page URI as a unique key.
    private IDictionary<URI, IDictionary<String, Double>> documentTfIdfVectors;

    // Feel free to add extra fields and helper methods.
    
    // Field for storing TF scores for every single word in all the documents
    private IDictionary<String,Double> tfScores;
    
    public TfIdfAnalyzer(ISet<Webpage> webpages) {
        // Implementation note: We have commented these method calls out so your
        // search engine doesn't immediately crash when you try running it for the
        // first time.
        //
        // You should uncomment these lines when you're ready to begin working
        // on this class.

//        this.idfScores = this.computeIdfScores(webpages);
//    		this.tfScores = this.computeTfScores(webpages);
//        this.documentTfIdfVectors = this.computeAllDocumentTfIdfVectors(webpages);
    }

    // Note: this method, strictly speaking, doesn't need to exist. However,
    // we've included it so we can add some unit tests to help verify that your
    // constructor correctly initializes your fields.
    public IDictionary<URI, IDictionary<String, Double>> getDocumentTfIdfVectors() {
        return this.documentTfIdfVectors;
    }

    // Note: these private methods are suggestions or hints on how to structure your
    // code. However, since they're private, you're not obligated to implement exactly
    // these methods: Feel free to change or modify these methods if you want. The
    // important thing is that your 'computeRelevance' method ultimately returns the
    // correct answer in an efficient manner.
    
    /**
     * New helper method
     * This method should return a dictionary mapping every single unique word found
     * in any documents to their IDF score.
     */
    public IDictionary<String, Double> computeTfCounts(ISet<Webpage> pages) {
    		IDictionary<String, Double> tfCounts = new ChainedHashDictionary<String, Double>();
    		Iterator<Webpage> pgItr = pages.iterator();
    		while (pgItr.hasNext()) {
    			// gets one page
    			Webpage page = pgItr.next();
    			// list of all the words in 'page'
    			IList<String> wordsList = page.getWords();
    			Iterator<String> wordsListItr = wordsList.iterator();
    			while (wordsListItr.hasNext()) {
    				// gets one word from 'wordsList'
    				String word = wordsListItr.next();
    				if (tfCounts.containsKey(word)) {
    					double count = tfCounts.get(word);
    					tfCounts.put(word, count + 1);
    				} else {
    					tfCounts.put(word, 1.0);
    				}
    			}
    		}
    		return tfCounts;
    }
    
    public IDictionary<String, Double> computeTfScores(ISet<Webpage> pages) {
    		IDictionary<String, Double> tfScores = computeTfCounts(pages); // Compute the counts first
    		Iterator<KVPair<String, Double>> tfCountsItr = tfScores.iterator();
    		while (tfCountsItr.hasNext()) {
    			KVPair<String, Double> tfPair = tfCountsItr.next();
    			tfScores.put(tfPair.getKey(), tfPair.getValue() / tfScores.size());
    		}
    		return tfScores;
    }
    
    /**
     * This method should return a dictionary mapping every single unique word found
     * in any documents to their IDF score.
     */
    private IDictionary<String, Double> computeIdfScores(ISet<Webpage> pages) {
	    /* new idict idf scores?  already have private var	
	     * for (uniqueTerm : pages) {
	    		idfScore = ln(total num docs/num docs containing uniqueTerm)
	    		idfScores.put(uniqueTerm, idfScore);
	    	}  	
	     	*/
    	
    	throw new NotYetImplementedException();
    }

    /**
     * Returns a dictionary mapping every unique word found in the given list
     * to their term frequency (TF) score.
     *
     * We are treating the list of words as if it were a document.
     */
    private IDictionary<String, Double> computeTfScores(IList<String> words) {
        /* new iDict IFscores? already have a private var
         * words.itr = new iterator();
         * while (itr.hasNext) {
         * 		score = number of times term appears in doc/total num words in a doc
         * 		ifScores.put(uniqueTerm, ifScore)
         * }
         * return dictionary
         */
    	throw new NotYetImplementedException();
    }

    /**
     * See spec for more details on what this method should do.
     */
    private IDictionary<URI, IDictionary<String, Double>> computeAllDocumentTfIdfVectors(ISet<Webpage> pages) {
        // Hint: this method should use the idfScores field and
        // call the computeTfScores(...) method.
        throw new NotYetImplementedException();
    }

    /**
     * Returns the cosine similarity between the TF-IDF vector for the given query and the
     * URI's document.
     *
     * Precondition: the given uri must have been one of the uris within the list of
     *               webpages given to the constructor.
     */
    public Double computeRelevance(IList<String> query, URI pageUri) {
    	//  Relevance(term, document) = TF(term, document) * IDF(term)

    	// TODO: Replace this with actual, working code.        
    	// TODO: The pseudocode we gave you is not very efficient. When implementing,
        // this smethod, you should:
        //
        // 1. Figure out what information can be precomputed in your constructor.
        //    Add a third field containing that information.
        //
        // 2. See if you can combine or merge one or more loops.
        return 1.0;
    }
}
