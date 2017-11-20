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

        this.idfScores = this.computeIdfScores(webpages);
        this.documentTfIdfVectors = this.computeAllDocumentTfIdfVectors(webpages);
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
     * This method should return a dictionary mapping every single unique word found
     * in any documents to their IDF score.
     */
    
    // need to get num docs and number of documents that have that word
    private IDictionary<String, Double> computeIdfCounts(ISet<Webpage> pages) {
    		IDictionary<String, Double> idfCounts = new ChainedHashDictionary<String, Double>();
		URI lastSeen = null;
		for (Webpage pg : pages) {
			IList<String> wordsList = pg.getWords();
			for (String word: wordsList) {
				if (!idfCounts.containsKey(word)) {
					idfCounts.put(word, 1.0);
				} else if (idfCounts.containsKey(word) && !pg.getUri().equals(lastSeen)) {
					idfCounts.put(word, idfCounts.get(word) + 1.0);
					lastSeen = pg.getUri();
				}
			}
		}
		return idfCounts;
    }
    
    public IDictionary<String, Double> computeIdfScores(ISet<Webpage> pages) {
     	//	ln (total num docs/num docs containing term)    	
		IDictionary<String, Double> idfScores = computeIdfCounts(pages); // Compute the counts first
		for (KVPair<String, Double> idfPair : idfScores) {
			idfScores.put(idfPair.getKey(), Math.log(pages.size() / idfPair.getValue()));
		}
		return idfScores;
    }

    /**
     * Returns a dictionary mapping every unique word found in the given list
     * to their term frequency (TF) counts.
     *
     * We are treating the list of words as if it were a document.
     */
    private IDictionary<String, Double> computeTfCounts(IList<String> words) {
    		IDictionary<String, Double> tfCounts = new ChainedHashDictionary<String, Double>();
    		for (String word : words) {
    			if (tfCounts.containsKey(word)) {
    				double count = tfCounts.get(word);
    				tfCounts.put(word, count + 1.0);
    			} else {
    				tfCounts.put(word, 1.0);
    			}
    		}
    		return tfCounts;
    }
    
    /**
     * Returns a dictionary mapping every unique word found in the given list
     * to their term frequency (TF) scores.
     *
     * We are treating the list of words as if it were a document.
     */
    private IDictionary<String, Double> computeTfScores(IList<String> words) {
    		IDictionary<String, Double> tfScores = computeTfCounts(words); // Compute the counts first
    		for (KVPair<String, Double> tfPair : tfScores) {
    			tfScores.put(tfPair.getKey(), (Double) tfPair.getValue() / words.size());
    		}
    		return tfScores;
    }
    

    /**
     * See spec for more details on what this method should do.
     */
    private IDictionary<URI, IDictionary<String, Double>> computeAllDocumentTfIdfVectors(ISet<Webpage> pages) {
        // Hint: this method should use the idfScores field and
        // call the computeTfScores(...) method.
    		IDictionary<URI, IDictionary<String, Double>> result = 
    				new ChainedHashDictionary<URI, IDictionary<String, Double>>();
    		for (Webpage pg : pages) {
    			URI uri = pg.getUri();
    			IDictionary<String, Double> pgTfIdfScores = new ChainedHashDictionary<String, Double>();
    			IDictionary<String, Double> tfScores = computeTfScores(pg.getWords());
    			for (KVPair<String, Double> tfScore: tfScores) {
    				double vector = this.idfScores.get((String) tfScore.getKey()) * (Double) tfScore.getValue();
    				pgTfIdfScores.put((String) tfScore.getKey(), vector);
    			}
    			result.put(uri, pgTfIdfScores);
    		}
    		return result;
    }

    /**
     * Returns the cosine similarity between the TF-IDF vector for the given query and the
     * URI's document.
     *
     * Precondition: the given uri must have been one of the uris within the list of
     *               webpages given to the constructor.
     */
    public Double computeRelevance(IList<String> query, URI pageUri) {
    		IDictionary<String, Double> docTfIdVectors = this.documentTfIdfVectors.get(pageUri);
    		IDictionary<String, Double> queryTfScores = computeTfScores(query);
    		IDictionary<String, Double> queryTfIdVectors = new ChainedHashDictionary<String, Double>();
    		for (KVPair<String, Double> tfScore: queryTfScores) {
    			double vector = this.idfScores.get((String) tfScore.getKey()) * (Double) tfScore.getValue();
    			queryTfIdVectors.put((String) tfScore.getKey(), vector);
    		}
    		double numerator = 0.0;
    		for (String word: query) {
    			double docWordScore = docTfIdVectors.containsKey(word) ? docTfIdVectors.get(word) : 0.0;
    			double queryWordScore = queryTfIdVectors.get(word);
    			numerator += docWordScore * queryWordScore;
    		}
    		double denominator = norm(docTfIdVectors) * norm(queryTfIdVectors);
    		double relevance = denominator == 0 ? 0.0 : numerator / denominator;
    		return relevance;
	    	// TODO: Replace this with actual, working code.        
	    	// TODO: The pseudocode we gave you is not very efficient. When implementing,
        // this smethod, you should:
        //
        // 1. Figure out what information can be precomputed in your constructor.
        //    Add a third field containing that information.
        //
        // 2. See if you can combine or merge one or more loops.
    }
    
    public Double norm(IDictionary<String, Double> tfIdVector) {
    		double output = 0.0;
    		for (KVPair<String, Double> pair: tfIdVector) {
    			double score = pair.getValue();
    			output += score * score;
    		}
    		return Math.sqrt(output);
    }
}
