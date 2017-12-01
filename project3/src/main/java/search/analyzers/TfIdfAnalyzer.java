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
	// This field must contain the IDF score for every single word in all
	// the documents.
	private IDictionary<String, Double> idfScores;

	// This field must contain the TF-IDF vector for each webpage you were given
	// in the constructor. We will use each webpage's page URI as a unique key.
	private IDictionary<URI, IDictionary<String, Double>> documentTfIdfVectors;

	// Field for storing TF scores for every single word in all the documents
	IDictionary<URI, Double> documentTfIdVectorNorm;

	// Initiates object with precomputed values
	public TfIdfAnalyzer(ISet<Webpage> webpages) {
		this.idfScores = this.computeIdfScores(webpages);
		this.documentTfIdVectorNorm = new ChainedHashDictionary<URI, Double>();
		this.documentTfIdfVectors = this.computeAllDocumentTfIdfVectors(webpages);
	}

	public IDictionary<URI, IDictionary<String, Double>> getDocumentTfIdfVectors() {
		return this.documentTfIdfVectors;
	}

	/**
	 * This method should return a dictionary mapping every single unique word found
	 * in any documents to their IDF score.
	 */

	// Counts the number of documents each word is found in
	private IDictionary<String, Double> computeIdfCounts(ISet<Webpage> pages) {
		IDictionary<String, Double> idfCounts = new ChainedHashDictionary<String, Double>();
		IDictionary<String, URI> lastSeen = new ChainedHashDictionary<String, URI>();
		for (Webpage pg : pages) {
			IList<String> wordsList = pg.getWords();
			for (String word: wordsList) {
				if (!idfCounts.containsKey(word)) {
					idfCounts.put(word, 1.0);
					lastSeen.put(word, pg.getUri());
				} else if (idfCounts.containsKey(word) 
						&& (lastSeen.containsKey(word) && !pg.getUri().equals(lastSeen.get(word)))) {
					idfCounts.put(word, idfCounts.get(word) + 1.0);
					lastSeen.put(word, pg.getUri());
				}
			}
		}
		return idfCounts;
	}

	//  Computes the Idf scores for each word
	private IDictionary<String, Double> computeIdfScores(ISet<Webpage> pages) {
		// Compute the counts first
		IDictionary<String, Double> idfScoresDict = computeIdfCounts(pages);
		for (KVPair<String, Double> idfPair : idfScoresDict) {
			if (idfPair.getValue() > 0) {
				idfScoresDict.put(idfPair.getKey(), 
						Math.log(pages.size() / idfPair.getValue()));
			} else {
				idfScoresDict.put(idfPair.getKey(), 0.0);
			}
		}
		return idfScoresDict;
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
		// Compute the counts first
		IDictionary<String, Double> tfScores = computeTfCounts(words);
		for (KVPair<String, Double> tfPair : tfScores) {
			tfScores.put(tfPair.getKey(), tfPair.getValue() / words.size());
		}
		return tfScores;
	}


	/**
	 * Computes all document Tf/Idf vectors
	 */
	private IDictionary<URI, IDictionary<String, Double>> 
	computeAllDocumentTfIdfVectors(ISet<Webpage> pages) {
		IDictionary<URI, IDictionary<String, Double>> result = 
				new ChainedHashDictionary<URI, IDictionary<String, Double>>();
		for (Webpage pg : pages) {
			URI uri = pg.getUri();
			IDictionary<String, Double> pgTfIdfScores = new ChainedHashDictionary<String, Double>();
			IDictionary<String, Double> tfScores = computeTfScores(pg.getWords());
			double output = 0.0;
			for (KVPair<String, Double> tfPair: tfScores) {
				double vector = this.idfScores.get((String) tfPair.getKey()) * 
						(Double) tfPair.getValue();
				pgTfIdfScores.put((String) tfPair.getKey(), vector);
				output += (vector * vector);
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
		IDictionary<String, Double> docTfIdVectors = this.documentTfIdfVectors.get(pageUri);
		IDictionary<String, Double> queryTfScores = computeTfScores(query);
		IDictionary<String, Double> queryTfIdVectors = new ChainedHashDictionary<String, Double>();
		double queryTfIdNorm = 0.0;
		for (String word: query) {
			double vector;
			if (this.idfScores.containsKey(word)) {
				vector = this.idfScores.get(word) * queryTfScores.get(word);
			} else {
				vector = 0.0;
			}
			queryTfIdVectors.put(word, vector);
			queryTfIdNorm += (vector * vector);
		}
		double numerator = 0.0;
		for (String word: query) {
			double docWordScore = docTfIdVectors.containsKey(word) ? docTfIdVectors.get(word) : 0.0;
			double queryWordScore = queryTfIdVectors.get(word);
			numerator += (docWordScore * queryWordScore);
		}
		double denominator = this.documentTfIdVectorNorm.get(pageUri) * Math.sqrt(queryTfIdNorm);
		double relevance = (denominator == 0) ? 0.0 : (numerator / denominator);
		return relevance;
	}
}

