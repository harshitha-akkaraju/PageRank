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
import search.models.Webpage;

import java.net.URI;

/**
 * This class is responsible for computing how "relevant" any given document is
 * to a given search query.
 *
 * See the spec for more details.
 */
public class TfIdfAnalyzer {	
    // This field contains the IDF score for every single word in all the documents.
    private IDictionary<String, Double> idfScores;	//  ln (total num docs/num docs containing term)
    
    // This field contains the TF-IDF vector for each webpage you were given
    // in the constructor. We use each webpage's page URI as a unique key.
    private IDictionary<URI, IDictionary<String, Double>> documentTfIdfVectors;

<<<<<<< HEAD
=======
    // Feel free to add extra fields and helper methods.
    
    // Field for storing TF scores for every single word in all the documents
    private IDictionary<String,Double> tfScores;
    IDictionary<URI, Double> documentTfIdVectorNorm;
>>>>>>> 620a91ed9bdcd8c630a3d23581b27f1acce92bcb
    
    //  Creates new object with precomputed Idf scores and the Tf/Idf vectors.
    public TfIdfAnalyzer(ISet<Webpage> webpages) {
        this.idfScores = this.computeIdfScores(webpages);
<<<<<<< HEAD
        this.documentTfIdfVectors = this.computeAllDocumentTfIdfVectors(webpages); 
=======
        this.documentTfIdVectorNorm = new ChainedHashDictionary<URI, Double>();
        this.documentTfIdfVectors = this.computeAllDocumentTfIdfVectors(webpages);
>>>>>>> 620a91ed9bdcd8c630a3d23581b27f1acce92bcb
    }

    public IDictionary<URI, IDictionary<String, Double>> getDocumentTfIdfVectors() {
        return this.documentTfIdfVectors;
    }
    
    /**
     * This method returns a dictionary mapping every single unique word found
     * in any documents to their IDF score.
     */
    
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
    
    //  This method computes the the IDF scores for each of the words in the documents
    public IDictionary<String, Double> computeIdfScores(ISet<Webpage> pages) {
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
    		IDictionary<URI, IDictionary<String, Double>> result = 
    				new ChainedHashDictionary<URI, IDictionary<String, Double>>();
    		for (Webpage pg : pages) {
    			URI uri = pg.getUri();
    			IDictionary<String, Double> pgTfIdfScores = new ChainedHashDictionary<String, Double>();
    			IDictionary<String, Double> tfScores = computeTfScores(pg.getWords());
    			double output = 0.0;
    			for (KVPair<String, Double> tfScore: tfScores) {
    				double vector = this.idfScores.get((String) tfScore.getKey()) * (Double) tfScore.getValue();
    				pgTfIdfScores.put((String) tfScore.getKey(), vector);
    				output += vector * vector;
    			}
    			result.put(uri, pgTfIdfScores);
    			this.documentTfIdVectorNorm.put(uri, Math.sqrt(output));
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
	    	// TODO: Replace this with actual, working code.        
	    	// TODO: The pseudocode we gave you is not very efficient. When implementing,
	    // this smethod, you should:
	    //
	    // 1. Figure out what information can be precomputed in your constructor.
	    //    Add a third field containing that information.
	    //
	    // 2. See if you can combine or merge one or more loops.
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
    		double denominator = this.documentTfIdVectorNorm.get(pageUri) * norm(queryTfIdVectors);
    		double relevance = denominator == 0 ? 0.0 : numerator / denominator;
    		return relevance;
    }
    
    //  This method computes the returns  to be used in computeRelevance
    public Double norm(IDictionary<String, Double> tfIdVector) {
    		double output = 0.0;
    		for (KVPair<String, Double> pair: tfIdVector) {
    			double score = pair.getValue();
    			output += score * score;
    		}
    		return Math.sqrt(output);
    }
}
